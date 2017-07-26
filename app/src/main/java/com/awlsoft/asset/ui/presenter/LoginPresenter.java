package com.awlsoft.asset.ui.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.awlsoft.asset.AssetApplication;
import com.awlsoft.asset.basic.AppSettings;
import com.awlsoft.asset.contract.LoginContract;
import com.awlsoft.asset.model.api.BaseObserver;
import com.awlsoft.asset.model.api.RetrofitManager;
import com.awlsoft.asset.model.api.exception.ExceptionHandle;
import com.awlsoft.asset.model.entry.response.AdminResponse;
import com.awlsoft.asset.model.entry.response.PermissionResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.AdminResponseDao;
import com.awlsoft.asset.orm.greendao.UserResponseDao;
import com.awlsoft.asset.util.EncryptUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import okhttp3.HttpUrl;

/**
 * Created by yejingxian on 2017/5/20.
 * 登录主持人
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private UserResponseDao dao;

    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
        dao = DBManager.getInstance(AssetApplication.get(mView.getActivity())).getDaoSession().getUserResponseDao();
    }

    @Override
    public void login(@NonNull final String server, @NonNull final String username, @NonNull String password) {
        if(!validServer(server)){
            return;
        }
        if (!validInput(username, password)){
            return;
        }
        mView.setLoadingIndicator(true);
        RetrofitManager.getInstance().getOperateManager().login(username, password).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                mView.setLoadingIndicator(false);
            }
        }).subscribe(new BaseObserver<UserResponse>() {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                mView.loginFailure(e.getReason());
            }

            @Override
            public void onNext(@NonNull UserResponse userResponse) {
                mView.loginSuccess(userResponse);
                //保存服务器地址到SharedPreferences
                if (!AppSettings.getServerAddress(mView.getActivity()).equals(server)) {
                    AppSettings.setServerAddress(mView.getActivity(), server);
                }
                //保存用户名
                AppSettings.addUserName(mView.getActivity(), username);
                //清除之前的账号并保存UserResponse
                clearAndSaveUser(userResponse);
            }
        });
    }

    private void clearAndSaveUser(UserResponse userResponse) {
        dao.deleteAll();
        dao.insertOrReplace(userResponse);
    }


    @Override
    public void loginByLocal(@NonNull final String server, @NonNull String username, @NonNull String password) {
        if(!validServer(server)){
            return;
        }
        if (!validInput(username, password)){
            return;
        }
        mView.setLoadingIndicator(true);
        AdminResponseDao adminDao = DBManager.getInstance(AssetApplication.get(mView.getActivity())).getDaoSession().getAdminResponseDao();
        //数据库没有账号资料
        long count = adminDao.queryBuilder().count();
        if (count == 0) {
            mView.loginFailure("请先联网登录，并同步数据！");
        } else {
            AdminResponse admin = null;
            try {
                admin = adminDao.queryBuilder().where(AdminResponseDao.Properties.Login_name.eq(username)).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (admin != null) {
                boolean pass = false;
                try {
                    pass = EncryptUtils.validPassword(password, admin.getPassword());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (pass) {
                    UserResponse user = admin.generatorUserResponse();
                    mView.loginSuccess(user);
                    //保存服务器地址
                    if (!AppSettings.getServerAddress(mView.getActivity()).equals(server)) {
                        AppSettings.setServerAddress(mView.getActivity(), server);
                    }
                    //保存用户名
                    AppSettings.addUserName(mView.getActivity(), username);
                    //清除之前的账号并保存UserResponse
                    clearAndSaveUser(user);
                } else {
                    mView.loginFailure("密码错误！");
                }
            } else {
                mView.loginFailure("账号错误！");
            }
        }
        mView.setLoadingIndicator(false);
    }

    @Override
    public void loginWithOutValidate() {
        List<UserResponse> list = dao.queryBuilder().build().list();
        if (list == null || list.size() == 0) {
            mView.loginFailure("登录失效，请重新登录！");
        } else {
            mView.loginSuccess(list.get(0));
        }
    }

    @Override
    public void checkSelfPermission() {
    }

    private boolean validServer(String server) {
        if (TextUtils.isEmpty(server)) {
            mView.showValidationError("服务器地址不能为空");
            return false;
        } else {
            try {
                String[] urls = server.split(":");
                HttpUrl url = new HttpUrl.Builder()
                        .scheme("http")
                        .host(urls[0])
                        .port(urls.length == 1 ? HttpUrl.defaultPort("http") : Integer.valueOf(urls[1]))
                        .build();
                RetrofitManager.getInstance().setBaseUrl(url.toString());
            } catch (Exception e) {
                e.printStackTrace();
                mView.showValidationError("服务器地址错误！");
                return false;
            } finally {
            }
        }
        return true;
    }

    /**
     * 验证用户名、密码不能为空
     * @param username
     * @param password
     * @return
     */
    private boolean validInput(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            mView.showValidationError("账号不能为空");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            mView.showValidationError("密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void start() {

    }
}
