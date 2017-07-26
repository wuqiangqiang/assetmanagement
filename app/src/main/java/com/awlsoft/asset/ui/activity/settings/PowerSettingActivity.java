package com.awlsoft.asset.ui.activity.settings;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.contract.PowerSettingContract;
import com.awlsoft.asset.ui.presenter.PowerSettingPresenter;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by yejingxian on 2017/5/31.
 */

public class PowerSettingActivity extends BaseActivity implements PowerSettingContract.View {
    private PowerSettingContract.Presenter mPresenter;
    private DiscreteSeekBar mSeekBar;
    private TextView mCurrent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPresenter = new PowerSettingPresenter(this);

        mSeekBar = (DiscreteSeekBar) findViewById(R.id.power_seekbar);
        mSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                if (fromUser) {
                    mPresenter.setOutputPower(value);
                } else {

                }
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        mCurrent = (TextView) findViewById(R.id.current);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showPowerValue(int dbm, boolean fromUser) {
        mCurrent.setText(getString(R.string.current_power, dbm));
        if(fromUser){

        }else{
            mSeekBar.setProgress(dbm);
        }
    }

    @Override
    public void setPresenter(PowerSettingContract.Presenter presenter) {

    }
}
