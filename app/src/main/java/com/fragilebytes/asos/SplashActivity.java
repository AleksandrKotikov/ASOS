package com.fragilebytes.asos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Windows on 11/04/2016.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//CREATE SPLASH SCREEN
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
