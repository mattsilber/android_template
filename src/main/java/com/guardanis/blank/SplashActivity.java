package com.guardanis.blank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.guardanis.blank.tools.views.ToolbarLayoutBuilder;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 2000);
    }

    @Override
    protected void setup(ToolbarLayoutBuilder builder) {
        // Nothin there
    }

}
