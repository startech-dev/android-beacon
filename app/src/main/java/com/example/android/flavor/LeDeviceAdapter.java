/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.flavor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class LeDeviceAdapter extends ArrayAdapter<DeviceItem> {

    private static final String LOG_TAG = LeDeviceAdapter.class.getSimpleName();
    private Context context;


    public LeDeviceAdapter(Activity context, ArrayList<DeviceItem> bleDevices) {
        super(context, 0, bleDevices);
        this.context = context;
    }
    public void addDevice(DeviceItem device) {

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final DeviceItem currentDevice = getItem(position);

        final TextView nameTextView = (TextView) listItemView.findViewById(R.id.version_name);
        nameTextView.setText(currentDevice.getName());
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.version_number);
        numberTextView.setText(currentDevice.getAddress());
        TextView rssTextView = (TextView) listItemView.findViewById(R.id.rssTv);
        rssTextView.setText("RSSI: " + String.valueOf(currentDevice.getRSSI()) + "   " + currentDevice.getStatus() + "   Dist:" + String.format("%.1fM", currentDevice.getDistance()) );

        Button doorBtn = (Button)listItemView.findViewById(R.id.doorBtn);
        doorBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stubS
                currentDevice.setDeviceTag("door");
                nameTextView.setTextColor(Color.BLUE);
                System.out.println("Door button clicked on item;" + position);
            }
        });

        Button deviceBtn = (Button)listItemView.findViewById(R.id.deviceBtn);
        deviceBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                currentDevice.setDeviceTag(("device"));
                nameTextView.setTextColor(Color.RED);
                // TODO Auto-generated method stubS
                System.out.println("Device button clicked on item;" + position);
            }
        });

        listItemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent= new Intent(context, MapActivity.class);
//                context.startActivity(intent);
            }
        });

        return listItemView;
    }


}
