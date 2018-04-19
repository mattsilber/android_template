package com.guardanis.blank

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.guardanis.blank.tools.views.ToolbarLayoutBuilder

class MainActivity: BaseActivity() {

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        setContentView(R.layout.activity_main)
        setupToolbar()
    }

    override fun setup(builder: ToolbarLayoutBuilder) {
        builder.addTitle("So", { finish() })
        builder.addOptionText("Lame", null)
    }
}
