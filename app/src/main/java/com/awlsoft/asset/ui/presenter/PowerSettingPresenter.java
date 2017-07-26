package com.awlsoft.asset.ui.presenter;

import com.awlsoft.asset.basic.AppSettings;
import com.awlsoft.asset.contract.PowerSettingContract;

/**
 * Created by yejingxian on 2017/5/31.
 */

public class PowerSettingPresenter implements PowerSettingContract.Presenter{
    private PowerSettingContract.View mView;

    public PowerSettingPresenter(PowerSettingContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void setOutputPower(int dbm) {
        AppSettings.setOutputPower(mView.getActivity(), dbm);
        mView.showPowerValue(dbm, true);
    }

    @Override
    public void start() {
        int dbm = AppSettings.getOutputPower(mView.getActivity());
        mView.showPowerValue(dbm, false);
    }
}
