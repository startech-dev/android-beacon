package com.example.android.flavor;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class PanelActivity extends AppCompatActivity {
    public static DeviceItem activeDevice = new DeviceItem();
    RecyclerView recyclerView;
    ServiceAdapter serviceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        TextView batteryView = (TextView)findViewById(R.id.batteryTextView);
        batteryView.setText(String.valueOf(activeDevice.getBatteryLevel()));
        ArrayList<GattInfoItem> arrayList = activeDevice.getServices();
        serviceAdapter = new ServiceAdapter(arrayList);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(serviceAdapter);
//        System.out.print("gatt service item;" + activeDevice.getServices().size());
    }
}
