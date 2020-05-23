package com.example.android.flavor;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private String bluetoothDeviceAddress;
    public Handler m_Handler = new Handler();
    private BluetoothGatt bluetoothGatt;
    private int connectionState = STATE_DISCONNECTED;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED =
            "com.unitraka.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.unitraka.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.unitraka.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.unitraka.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.unitraka.bluetooth.le.EXTRA_DATA";

    public final static UUID UNITRAKA_CHARACTERISTIC_READ =
            UUID.fromString("11111111-1111-1111-1111-111111111111");
    private boolean m_Reading = false;
    private BluetoothGattCharacteristic batteryCharacteristic;
    private BluetoothGattCharacteristic txPowerCharacteristic;
    public BluetoothLeService(Handler handler) {
        m_Handler = handler;
    }
    private final BluetoothGattCallback gattCallback =
            new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                                    int newState) {
                    String intentAction;
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        intentAction = ACTION_GATT_CONNECTED;
                        connectionState = STATE_CONNECTED;
                        broadcastUpdate(intentAction);
                        Log.i(TAG, "Connected to GATT server.");
                        Log.i(TAG, "Attempting to start service discovery:" +
                                bluetoothGatt.discoverServices());

                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        Message msg = new Message();
                        msg.what = 2; // device disconnected
                        m_Handler.sendMessage(msg);
                        intentAction = ACTION_GATT_DISCONNECTED;
                        connectionState = STATE_DISCONNECTED;
                        Log.i(TAG, "Disconnected from GATT server.");
                        broadcastUpdate(intentAction);
                    }
                }

                @Override
                // New services discovered
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    ArrayList<GattInfoItem> gattInfoList = new ArrayList<>();
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        List<BluetoothGattService> serviceList = gatt.getServices();
                        for (int i = 0; i < serviceList.size(); i++) {
                            GattInfoItem listItem = new GattInfoItem();
                            listItem.setGattService(serviceList.get(i));
                            listItem.setCharacteristicList(serviceList.get(i).getCharacteristics());
                            gattInfoList.add(listItem);
                            // read battery
                            if(serviceList.get(i).getUuid().toString().contains("180f")) {
                                batteryCharacteristic = serviceList.get(i).getCharacteristics().get(0);
                            }
                            // tx power
                            if(serviceList.get(i).getUuid().toString().contains("1804")) {
                                System.out.println("tx power service;");
                                txPowerCharacteristic = serviceList.get(i).getCharacteristics().get(0);
                            }
//                        gattInfoList[i].service = serviceList.get(i);

//                        System.out.println("discovered services;" + serviceList.get(i).getUuid());
//                        List<BluetoothGattCharacteristic> characList = serviceList.get(i).getCharacteristics();
//                        System.out.println("discovered services detail1;" + characList.get(0).getUuid());
                        }

                        PanelActivity.activeDevice.setServices(gattInfoList);
                        Message msg = new Message();
                        msg.what = 1;
//                    PanelActivity.DeviceItem. = gattInfoList;
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);

                        bluetoothGatt.readCharacteristic(batteryCharacteristic);
                        System.out.println("battery reading;;;;;;;;;;;;;;;;;;;");
//                        try {
//                            Thread.sleep(2000);
//                            System.out.println("txPower reading;;;;;;;;;;;;;;");
//                            bluetoothGatt.readCharacteristic(txPowerCharacteristic);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }

                    } else {
                        Log.w(TAG, "onServicesDiscovered received: " + status);
                    }
                }

                @Override
                // Result of a characteristic read operation
                public void onCharacteristicRead(BluetoothGatt gatt,
                                                 BluetoothGattCharacteristic characteristic,
                                                 int status) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        byte[] b = characteristic.getValue();
                        // battery channel
                        if(characteristic.getUuid().toString().contains("2a19")) {
                            System.out.println("batter read complete");
                            int batteryLevel = b[0];
                            PanelActivity.activeDevice.setBatterLevel(batteryLevel);
                        }
                        // tx power
//                        if(characteristic.getUuid().toString().contains("2a07")) {
//                            System.out.println("tx read complete");
//
//                            System.out.println("tx level: " + b);
//                        }
                        broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
                    }
                }
            };

    public BluetoothGatt connect(Context contxt, BluetoothDevice device) {
        bluetoothGatt = device.connectGatt(contxt, false, gattCallback);
        return bluetoothGatt;
    }

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        System.out.println("broadcast update action:" + action);
        if(action.equals(ACTION_GATT_SERVICES_DISCOVERED)) {
            //active service item is now available
            Message msg = new Message();
            msg.what = 1;
            m_Handler.sendMessage(msg);
        }
    }


    //
    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        // This is special handling for the Heart Rate Measurement profile. Data
        // parsing is carried out as per profile specifications.
        if (UNITRAKA_CHARACTERISTIC_READ.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
                intent.putExtra(EXTRA_DATA, new String(data) + "\n" +
                        stringBuilder.toString());
            }
        }
//        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
