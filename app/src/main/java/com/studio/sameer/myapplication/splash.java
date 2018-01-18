package com.studio.sameer.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splash extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
////        setContentView(R.layout.splash);
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(splash.this,MainActivity.class);
//                splash.this.startActivity(mainIntent);
//                splash.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);

    }
}
