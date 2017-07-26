package com.awlsoft.asset.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.awlsoft.asset.AssetApplication;
import com.awlsoft.asset.R;
import com.awlsoft.asset.basic.AppSettings;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.contract.LoginContract;
import com.awlsoft.asset.model.entry.ComImgTextInfo;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.ui.adapter.CommCallBack;
import com.awlsoft.asset.ui.presenter.LoginPresenter;
import com.awlsoft.asset.ui.widget.ListChooseWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yejingxian on 2017/5/20.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    private EditText mUsername, mPassword, mServer;
    private ImageView mUiChangeUserName;
    private Button mLogin;

    private ListChooseWindow mListWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mServer = (EditText) findViewById(R.id.server_address);
        //读取保存的服务器地址
        mServer.setText(AppSettings.getServerAddress(this));

        mUiChangeUserName = (ImageView) findViewById(R.id.ui_change_user_name);

        mLogin = (Button) findViewById(R.id.sign_in_button);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                System.out.println("yjx onEditorAction" + actionId);
                if (actionId == R.id.loginEt || actionId == EditorInfo.IME_NULL) {
                    login();
                    return true;
                }
                return false;
            }
        });
        setupHistory();

        if(AppSettings.getNeedLogin(this)){

        }else{
            mPresenter.loginWithOutValidate();
        }
    }

    /**
     * '设置登录历史
     */
    private void setupHistory() {
        List<String> usernames = AppSettings.getUserNames(this);
        final ArrayList<ComImgTextInfo> infoList = new ArrayList<>();

        if (usernames != null && usernames.size() != 0) {
            for (String username : usernames) {
                ComImgTextInfo comImgTextInfo = new ComImgTextInfo();
                comImgTextInfo.setContentText(username);
                infoList.add(comImgTextInfo);
            }

            mUiChangeUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideKeybord(mUsername, LoginActivity.this);

                    mListWindow.setWindowsWidth(mUsername.getMeasuredWidth());
                    mListWindow.showDown(mUsername);
                }
            });

        }

        //infoList绑定到mListWindow
        mListWindow = new ListChooseWindow(this, infoList).createWindow();
        mListWindow.setBackgroundRes(R.drawable.light_grey_stroke_slide_bg);
        mListWindow.setCallBack(new CommCallBack() {
            @Override
            public void callBack(Object... object) {
                Integer position = (Integer) object[0];

                String text = infoList.get(position).getText();
                mUsername.setText(text);
                mUsername.setSelection(text.length());
            }

            @Override
            public void progress(String str1, String str2) {

            }
        });
    }

    private void hideKeybord(EditText editText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void login() {
        if (mPresenter != null) {
            if(isNetAvailable(this)){
                mPresenter.login(mServer.getText().toString().trim(), mUsername.getText().toString().trim(), mPassword.getText().toString().trim());
            }else{
                mPresenter.loginByLocal(mServer.getText().toString().trim(), mUsername.getText().toString().trim(), mPassword.getText().toString().trim());
            }
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            showProgressDialog("登录中！");
        } else {
            dismissProgressDialog();
        }
    }

    @Override
    public void loginSuccess(UserResponse user) {
        AssetApplication.get(this).setUser(user);
        //下次无需登录
        AppSettings.setNeedLogin(this,false);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure(String reason) {
        showMessage(reason);
    }

    @Override
    public void showValidationError(String reason) {
        showMessage(reason);
    }

    @Override
    public void showNoPermission() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }
}
