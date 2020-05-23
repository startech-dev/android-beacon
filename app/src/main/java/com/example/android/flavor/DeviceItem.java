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

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.location.Location;
import java.util.ArrayList;
import java.util.List;

class GattInfoItem {
    BluetoothGattService service;
    List<BluetoothGattCharacteristic> characteristics;
    public void setGattService(BluetoothGattService srv) { service = srv; }
    public BluetoothGattService getGattService() { return service; }
    public void setCharacteristicList(List<BluetoothGattCharacteristic> chrList) { characteristics = chrList; }
    public List<BluetoothGattCharacteristic> getCharacteristicList() {
        return characteristics;
    }

}

public class DeviceItem {
    private BluetoothDevice mDevice;
    private int m_RSSI;
    private int m_BatteryLevel; // 180F, 2A19
    private int m_TxPower;  // 1804, 2A07
    private String m_tag;
    private double m_Distance;
    private String m_lastDeviceName = "";
    private long m_lastDiscoveredSec = 0;
    private String m_Status = "";
    private Location m_lastDiscoveredLocation;
    private ArrayList<GattInfoItem> m_Services = new ArrayList<>();
    public  ArrayList<Integer> m_RSSIAry = new ArrayList<>();
    //    private ArrayList<double>
    public DeviceItem()
    {

    }

    public void setDevice(BluetoothDevice device) {
        mDevice = device;
    }

    public String getName() {
        return mDevice.getName();
    }

    public String getAddress() {
        return mDevice.getAddress();
    }
    public BluetoothDevice getDevice() {
        return mDevice;
    }

    public void setRSSI(int rssi) { m_RSSI = rssi; }
    public int getRSSI() { return m_RSSI; }
    public ArrayList<GattInfoItem> getServices() { return m_Services; }
    public void setServices(ArrayList<GattInfoItem> gatInfoList) { m_Services = gatInfoList; }
    public void setTxPower(int tx) { m_TxPower = tx; }
    public int getTXPower() { return m_TxPower; }
    public void setBatterLevel(int level) { m_BatteryLevel = level; }
    public int getBatteryLevel() { return m_BatteryLevel; }
    public void setDistance(double distance) { m_Distance = distance; }
    public double getDistance() { return m_Distance; }
    public void setlastDeviceName(String deviceName) {
        m_lastDeviceName = deviceName;
    }
    public String getlastDeviceName() {
        return m_lastDeviceName;
    }
    public long getLastDiscoveredSec() {
        return m_lastDiscoveredSec;
    }
    public void setLastDiscoveredSec(long sec) {
        m_lastDiscoveredSec = sec;
    }
    public void setStatus(String  status) {
        m_Status = status;
    }
    public String getStatus() { return m_Status; }
    public void setDeviceTag(String tag) { m_tag = tag; }
    public  String getDeviceTag() { return m_tag; }
    public Location getLastLoc() { return m_lastDiscoveredLocation; }
    public void setLastLoc(Location loc) { m_lastDiscoveredLocation = loc; }

}
