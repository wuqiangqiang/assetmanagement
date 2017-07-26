package com.awlsoft.asset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.awlsoft.asset.supernfc.reader.CMD;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.supernfc.reader.server.ReaderHelper;

import java.io.IOException;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private final BroadcastReceiver mRecv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //showToast("收到扫描返回---------");
            System.out.println("yjx rfid found 0------------------------"+intent.getAction());
            if (intent.getAction().equals(
                    ReaderHelper.BROADCAST_REFRESH_INVENTORY_REAL)) {
                byte btCmd = intent.getByteExtra("cmd", (byte) 0x00);
                System.out.println("yjx rfid found------------------------"+btCmd);
                switch (btCmd) {
                    case CMD.REAL_TIME_INVENTORY:
                    case CMD.CUSTOMIZED_SESSION_TARGET_INVENTORY:
                        break;
                    case ReaderHelper.INVENTORY_ERR:
                    case ReaderHelper.INVENTORY_ERR_END:
                    case ReaderHelper.INVENTORY_END:
                        //System.out.println("yjx rfid found------------------------"+btCmd);
                        /*List<InventoryBuffer.InventoryTagMap> lsTagList = rfidManager.getInventory();
                        if(lsTagList != null && lsTagList.size() != 0){

                            InventoryBuffer.InventoryTagMap inventoryTagMap = lsTagList.get(0);
                            String rfid = inventoryTagMap.strEPC.replace(" ", "");
                            System.out.println("yjx rfid-->"+rfid+lsTagList.size());
                        }*/
                        break;
                }
            } else if (intent.getAction().equals(
                    ReaderHelper.BROADCAST_WRITE_LOG)) {

            }
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lbm= LocalBroadcastManager.getInstance(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lbm.unregisterReceiver(mRecv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rfidManager= new RfidManager(this);
        try {
            rfidManager.openDriver();
        } catch (IOException e) {
            e.printStackTrace();
            showToast("程序版本有误，请联系技术支持人员！");
        }
        rfidManager.startScan();
    }

    public  void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    private LocalBroadcastManager lbm;
    private RfidManager rfidManager;
}
