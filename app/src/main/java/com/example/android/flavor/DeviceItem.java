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

public class DeviceItem {

    private BluetoothDevice mDevice;

    public DeviceItem(BluetoothDevice device)
    {

        mDevice = device;
    }

    /**
     * Get the name of the Android version
     */
    public String getName() {
        return mDevice.getName();
    }

    /**
     * Get the Android version number
     */
    public String getAddress() {
        return mDevice.getAddress();
    }

    /**
     * Get the image resource ID
     */
    public BluetoothDevice getDevice() {
        return mDevice;
    }


}
