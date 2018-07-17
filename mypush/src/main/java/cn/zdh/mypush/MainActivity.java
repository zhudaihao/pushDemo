package cn.zdh.mypush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrqin.jpush.ExampleUtil;
import com.mrqin.jpush.LocalBroadcastManager;

import cn.zdh.mypush.jpush.JPushManager;

public class MainActivity extends AppCompatActivity {


    //依赖封装好的推送包
    //只需四步
    //1 项目依赖 JPush
    //2 修改JPush里面的manifest里面的包名替换为项目的包名； 替换manifest的key；替换build.gradle里面key和包名
    //3 把 JPUSHManager类复制到项目，在Application里面注册
    //4 在MainActivity把下面代码复制
    private JPushManager jManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerMessageReceiver();

        jManager = new JPushManager(getApplicationContext());
        jManager.initJPush();
    }

    public static boolean isForeground = false;

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
