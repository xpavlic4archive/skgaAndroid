package com.laurinka.skga.app;

import android.content.Intent;
import android.os.Bundle;

import com.ubikod.capptain.android.sdk.activity.CapptainActivity;

/**
 * Activity shows logo of company and forward flow to intro
 */
public class LaurinkaActivity extends CapptainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laurinka);

        Thread logo = new Thread(){
            public void run(){
                try{

                    int time = 0;

                    while(time<3500){
                        sleep(100);
                        time = time + 100;
                    }
                    Intent i = new Intent("com.laurinka.skga.app.MAIN");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    finish();
                }
            }
        };

        logo.start();
    }
}
