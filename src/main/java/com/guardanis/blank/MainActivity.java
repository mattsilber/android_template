package com.guardanis.blank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guardanis.blank.tools.views.ToolbarLayoutBuilder;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        setupToolbar();
    }

    @Override
    protected void setup(ToolbarLayoutBuilder builder) {
        builder.addTitle("So",
                v -> finish())
            .addOptionText("Lame", null);
    }

}
