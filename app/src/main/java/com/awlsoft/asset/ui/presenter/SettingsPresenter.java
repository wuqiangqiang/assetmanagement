package com.awlsoft.asset.ui.presenter;

import com.awlsoft.asset.contract.SettingsContract;

/**
 * Created by yejingxian on 2017/5/31.
 */

public class SettingsPresenter implements SettingsContract.Presenter {
    private SettingsContract.View mView;

    public SettingsPresenter(SettingsContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void logOut() {
        mView.showLogout();
    }

    @Override
    public void setOutputPower() {
        mView.showSetOutputPower();
    }

    @Override
    public void start() {

    }
}
