package com.guardanis.blank

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import com.guardanis.blank.tools.views.ToolbarLayoutBuilder

class SplashActivity: BaseActivity() {

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun setup(builder: ToolbarLayoutBuilder) {
        // Nothin there
    }
}
