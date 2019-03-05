package com.zong.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zong.pwdview.PwdViewLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PwdViewLayout pwdViewLayout1 = findViewById(R.id.pwdViewLayout1);
        final PwdViewLayout pwdViewLayout2 = findViewById(R.id.pwdViewLayout2);
        final PwdViewLayout pwdViewLayout3 = findViewById(R.id.pwdViewLayout3);
        pwdViewLayout1.setPwdViewCount(5);//设置输入框个数
        //输入完成监听,获取输入内容
        pwdViewLayout1.setInputFinshedListener(new PwdViewLayout.OnInputFinshedListener() {
            @Override
            public void inputFinshed(String content) {
                Log.i(TAG, "inputFinshed: " + content);
            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdViewLayout1.clearPwdContent();//清空输入内容
                pwdViewLayout2.clearPwdContent();//清空输入内容
                pwdViewLayout3.clearPwdContent();//清空输入内容
            }
        });
    }
}
