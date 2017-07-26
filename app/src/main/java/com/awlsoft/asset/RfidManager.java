package com.awlsoft.asset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.AndroidVersions;
import com.awlsoft.asset.basic.AppSettings;
import com.awlsoft.asset.supernfc.reader.CMD;
import com.awlsoft.asset.supernfc.reader.model.ISO180006BOperateTagBuffer;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.supernfc.reader.model.OperateTagBuffer;
import com.awlsoft.asset.supernfc.reader.model.ReaderSetting;
import com.awlsoft.asset.supernfc.reader.server.ReaderBase;
import com.awlsoft.asset.supernfc.reader.server.ReaderHelper;
import com.awlsoft.asset.supernfc.utils.M10_GPIO;
import com.fntech.m10a.gpio.M10A_GPIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android_serialport_api.SerialPort;

/**
 * Created by yejingxian on 2017/5/18.
 */

public class RfidManager {

    private static final String TAG = RfidManager.class.getSimpleName();

    //SerialPort.close 会导致下次无法正常使用，暂时不关闭。
    private static final boolean POWER_DOWN = false;

    private LocalBroadcastManager lbm;
    private Context mContext;

    public SerialPort mSerialPort;
    private ReaderHelper mReaderHelper;
    private ReaderBase mReader;
    private ReaderSetting m_curReaderSetting;
    private OperateTagBuffer m_curOperateTagBuffer;
    private InventoryBuffer m_curInventoryBuffer;
    private ISO180006BOperateTagBuffer m_curOperateTagISO18000Buffer;

    private boolean opended;
    private boolean scanning;

    /**
     * 最大功率值
     */
    private int endDBM = 33;

    private Handler mLoopHandler = new Handler();
    private Runnable mLoopRunnable = new Runnable() {
        public void run() {
            byte btWorkAntenna = m_curInventoryBuffer.lAntenna
                    .get(m_curInventoryBuffer.nIndexAntenna);
            if (btWorkAntenna < 0)
                btWorkAntenna = 0;

            mReader.setWorkAntenna(m_curReaderSetting.btReadId, btWorkAntenna);
            mLoopHandler.postDelayed(this, 2000);
        }
    };

    public RfidManager(Context context) {
        this.lbm = LocalBroadcastManager.getInstance(context);
        this.mContext = context;
        this.endDBM = AppSettings.getOutputPower(context);
    }

    /**
     * 模块上电，打开串口
     */
    public synchronized void openDriver() throws IOException {
        if (opended) {
            return;
        }
        if (android.os.Build.VERSION.RELEASE.equals(AndroidVersions.V_4_0_3)) {
            M10_GPIO.R1000_PowerOn(); // 模块上电
            try {
                mSerialPort = new SerialPort(new File("dev/ttySAC2"), 115200, 0);
                opended = true;
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (android.os.Build.VERSION.RELEASE
                .equals(AndroidVersions.V_5_1_1)) {
            M10A_GPIO.PowerOn(); // 模块上电
            try {
                M10A_GPIO._uhf_SwitchSerialPort();
                mSerialPort = new SerialPort(new File("/dev/ttyHSL0"), 115200, 0);
                opended = true;
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            throw new IOException("程序版本有误，请联系技术支持人员！");
        }

        try {
            mReaderHelper = ReaderHelper.getDefaultHelper();
            mReaderHelper.setReader(mSerialPort.getInputStream(),
                    mSerialPort.getOutputStream());
            mReader = mReaderHelper.getReader();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        m_curReaderSetting = mReaderHelper.getCurReaderSetting();
        m_curInventoryBuffer = mReaderHelper.getCurInventoryBuffer();
        m_curOperateTagBuffer = mReaderHelper.getCurOperateTagBuffer();
        m_curOperateTagISO18000Buffer = mReaderHelper
                .getCurOperateTagISO18000Buffer();

        mReader.getOutputPower(m_curReaderSetting.btReadId);

        byte btOutputPower = 0x00;

        btOutputPower = (byte) endDBM;

        int ret = mReader.setOutputPower(m_curReaderSetting.btReadId,
                btOutputPower);
        if (ret == 0) {
            m_curReaderSetting.btAryOutputPower = new byte[]{btOutputPower};
            Log.d(TAG, "yjx设置功率成功:" + btOutputPower);
        } else {
            Log.d(TAG, "yjx设置功率失败:" + btOutputPower);
        }
    }

    public boolean setOutputPower(int dbm){
        this.endDBM = dbm;
        byte btOutputPower = (byte) dbm;
        if(m_curReaderSetting != null){
            int ret = mReader.setOutputPower(m_curReaderSetting.btReadId,
                    btOutputPower);
            if (ret == 0) {
                m_curReaderSetting.btAryOutputPower = new byte[]{btOutputPower};
                Log.d(TAG, "yjx设置功率setOutputPower成功:" + btOutputPower);
            } else {
                Log.d(TAG, "yjx设置功率setOutputPower失败:" + btOutputPower);
            }
            return ret == 0;
        }
        return true;
    }

    private synchronized void registerReceiver() {
        IntentFilter itent = new IntentFilter();
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_FAST_SWITCH);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_INVENTORY);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_INVENTORY_REAL);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_ISO18000_6B);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_OPERATE_TAG);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_READER_SETTING);
        itent.addAction(ReaderHelper.BROADCAST_WRITE_LOG);
        itent.addAction(ReaderHelper.BROADCAST_WRITE_DATA);
        lbm.registerReceiver(mRecv, itent);
    }

    private final BroadcastReceiver mRecv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(
                    ReaderHelper.BROADCAST_REFRESH_INVENTORY_REAL)) {
                byte btCmd = intent.getByteExtra("cmd", (byte) 0x00);
                System.out.println("yjx BroadcastReceiver RfidManager:" + intent.getAction() + "->" + btCmd);
                switch (btCmd) {
                    case CMD.REAL_TIME_INVENTORY:
                    case CMD.CUSTOMIZED_SESSION_TARGET_INVENTORY:
                        break;
                    case ReaderHelper.INVENTORY_ERR:
                    case ReaderHelper.INVENTORY_ERR_END:
                    case ReaderHelper.INVENTORY_END:
                        if (mReaderHelper.getInventoryFlag()) {
                        } else {
                            mLoopHandler.removeCallbacks(mLoopRunnable);
                        }
                        System.out.println("yjx BroadcastReceiver RfidManager rfid count" + "->" + m_curInventoryBuffer.lsTagList.size());
                        break;
                }
            } else if (intent.getAction().equals(
                    ReaderHelper.BROADCAST_WRITE_LOG)) {

            }
        }
    };

    private synchronized void unregisterReceiver() {
        lbm.unregisterReceiver(mRecv);
    }

    /**
     * 模块下电，关闭串口
     */
    public synchronized void closeDriver() {
        if (!opended) {
            return;
        }
        if (POWER_DOWN) {
            if (mSerialPort != null) {
                mSerialPort.close();
            }
        }
        if (android.os.Build.VERSION.RELEASE.equals(AndroidVersions.V_4_0_3)) {
            M10_GPIO.R1000_PowerOFF();
        } else if (android.os.Build.VERSION.RELEASE
                .equals(AndroidVersions.V_5_1_1)) {
            M10A_GPIO.PowerOff();
        }

        opended = false;
    }

    public synchronized void startScan() {
        if (!opended || scanning) {
            return;
        }

        scanning = true;

        registerReceiver();
        // Loger.disk_log("调试", "开始盘询，设置天线", "M10_U8");
        m_curInventoryBuffer.clearInventoryPar();

        m_curInventoryBuffer.bLoopCustomizedSession = true;
        /*
         * m_curInventoryBuffer.btSession = 0x00;
		 * m_curInventoryBuffer.btTarget = 0x00;
		 */

        m_curInventoryBuffer.btSession = (byte) (getSessionState() & 0xFF);
        m_curInventoryBuffer.btTarget = (byte) (getFlagState() & 0xFF);

        m_curInventoryBuffer.lAntenna.add((byte) 0x01);

        m_curInventoryBuffer.bLoopInventoryReal = true;
        // m_curInventoryBuffer.btRepeat = 0;

        m_curInventoryBuffer.btRepeat = (byte) 1;

        // m_curInventoryBuffer.bLoopCustomizedSession = false;

        m_curInventoryBuffer.clearInventoryRealResult();
        mReaderHelper.setInventoryFlag(true);
        mReaderHelper.clearInventoryTotal();

        byte btWorkAntenna = m_curInventoryBuffer.lAntenna
                .get(m_curInventoryBuffer.nIndexAntenna);
        if (btWorkAntenna < 0)
            btWorkAntenna = 0;

        mReader.setWorkAntenna(m_curReaderSetting.btReadId, btWorkAntenna);
        // Loger.disk_log("调试", "开始盘询，天线设置完毕", "M10_U8");
        m_curReaderSetting.btWorkAntenna = btWorkAntenna;

        mLoopHandler.postDelayed(mLoopRunnable, 2000);

    }

    public synchronized void stopScan() {
        if (!opended || !scanning) {
            return;
        }
        // refreshText();
        mReaderHelper.setInventoryFlag(false);
        m_curInventoryBuffer.bLoopInventory = false;
        m_curInventoryBuffer.bLoopInventoryReal = false;
        mLoopHandler.removeCallbacks(mLoopRunnable);
        unregisterReceiver();
        scanning = false;
    }

    public boolean isScanning() {
        return scanning;
    }

    public boolean isOpen() {
        return opended;
    }

    public int getSessionState() {
        return 0;
    }

    public int getFlagState() {
        return 0;
    }

    public List<InventoryBuffer.InventoryTagMap> getInventory() {
        List<InventoryBuffer.InventoryTagMap> lsTagList;
        if (m_curInventoryBuffer == null) {
            lsTagList = new ArrayList<>();
        } else {
            lsTagList = m_curInventoryBuffer.lsTagList;
        }
        return lsTagList;
    }

    public void clearInventory(){
        m_curInventoryBuffer.clearTagMap();
    }
}
