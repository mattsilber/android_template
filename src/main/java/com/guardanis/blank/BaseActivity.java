package com.guardanis.blank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.guardanis.blank.tools.views.ToolbarLayoutBuilder;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    protected void setupToolbar(){
        setupToolbar(R.id.base__toolbar, R.layout.base__toolbar);
    }

    protected void setupToolbar(int toolbarId){
        setupToolbar(toolbarId, R.layout.base__toolbar);
    }

    protected void setupToolbar(int toolbarId, int inflatedResId){
        setup(new ToolbarLayoutBuilder((Toolbar) findViewById(toolbarId), inflatedResId));
    }

    protected abstract void setup(ToolbarLayoutBuilder builder);

}
