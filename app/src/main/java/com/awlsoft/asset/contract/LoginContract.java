package com.awlsoft.asset.contract;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.awlsoft.asset.basic.BasePresenter;
import com.awlsoft.asset.basic.BaseView;
import com.awlsoft.asset.model.entry.response.UserResponse;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        Activity getActivity();

        void showMessage(String message);

        void setLoadingIndicator(boolean active);

        void loginSuccess(UserResponse user);

        void loginFailure(String reason);

        void showValidationError(String reason);

        void showNoPermission();

    }

    /**
     * 定义了登录、登出抽象方法
     */
    interface Presenter extends BasePresenter {

        void login(@NonNull final String server, @NonNull String username, @NonNull String password);

        void loginByLocal(@NonNull final String server, @NonNull String username, @NonNull String password);

        void loginWithOutValidate();

        void checkSelfPermission();

    }
}
