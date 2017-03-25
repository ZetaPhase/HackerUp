package io.zetaphase.hackerup;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                // TODO: Ping server here
            } finally {
                //repeat this runnable
                mHandler.postDelayed(mStatusChecker, 15000);
            }
        }
    };
}
