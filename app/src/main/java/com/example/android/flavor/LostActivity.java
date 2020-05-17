package com.example.android.flavor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        TextView lostTextView = findViewById(R.id.lostTextView);
        lostTextView.setText("Lost a device;" + PanelActivity.activeDevice.getlastDeviceName());
    }
}
