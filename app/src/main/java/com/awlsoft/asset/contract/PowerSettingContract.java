package com.awlsoft.asset.contract;

import android.app.Activity;

import com.awlsoft.asset.basic.BasePresenter;
import com.awlsoft.asset.basic.BaseView;

/**
 * Created by yejingxian on 2017/5/31.
 */

public interface PowerSettingContract {

    interface View extends BaseView<Presenter> {

        Activity getActivity();

        void showPowerValue(int dbm, boolean fromUser);

    }

    interface Presenter extends BasePresenter {

        void setOutputPower(int dbm);
    }
}
