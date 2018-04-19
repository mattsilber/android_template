package com.guardanis.blank

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.guardanis.blank.tools.views.ToolbarLayoutBuilder

abstract class BaseActivity: AppCompatActivity() {

    public override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
    }

    @JvmOverloads protected fun setupToolbar(toolbarId: Int = R.id.base__toolbar, inflatedResId: Int = R.layout.base__toolbar) {
        val toolbarView = findViewById<Toolbar>(toolbarId)
        val builder = ToolbarLayoutBuilder(toolbarView, inflatedResId)

        setup(builder)
    }

    protected abstract fun setup(builder: ToolbarLayoutBuilder)
}
