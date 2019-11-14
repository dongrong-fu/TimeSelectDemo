package com.example.timeselectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_select_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectDoubleNumberDialog dialog = new SelectDoubleNumberDialog(MainActivity.this);
                dialog.setTitle("每日提醒时间");
                String[] hourArr = NumberUtil.buildTimeNumberArr(1,23,1);
                String[] minuteArr = NumberUtil.buildTimeNumberArr(0,59,1);
                dialog.setSelectArr(hourArr,NumberUtil.getDefaultPosition(hourArr,"21"),minuteArr,NumberUtil.getDefaultPosition(minuteArr,"0"));
                dialog.setSelectNumberListenr((value1, value2) -> {
                    Toast.makeText(getApplicationContext(),"当前选择的时间为："+value1+":"+value2,Toast.LENGTH_SHORT).show();
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }
}
