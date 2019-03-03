package com.zong.pwdview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PwdViewLayout pwdViewLayout= findViewById(R.id.pwdViewLayout1);
        pwdViewLayout.setPwdViewCount(5);//设置输入框个数
        pwdViewLayout.setInputFinshedListener(new PwdViewLayout.OnInputFinshedListener() {//输入完成监听
            @Override
            public void inputFinshed(String content) {
                Log.i(TAG, "inputFinshed: "+content);
            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            pwdViewLayout.clearPwdContent();//清空输入内容
            }
        });
    }
}
