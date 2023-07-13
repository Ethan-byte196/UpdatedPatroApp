package com.example.updatedpatrolapp;

import android.util.Log;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Observable;
import android.os.Handler;
import android.provider.Settings;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

//Layout stuff
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //Logcat debug tag
    private static final String TAG = "MainActivity";

    //Declare layout aspects
    TextView mStatusBlueTv, mDeviceInfoTv;
    Button mOnBtn, mScanBtn, mOffBtn, mConnectBtn, mFineBtn;

    //Codes used for requests
    private static final int REQUEST_ENABLED_BT = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 2;

    private static final int REQUEST_SCAN_PERMISSION = 3;

    private static final int REQUEST_CONNECT_PERMISSION = 4;



    //Scanner function
    private static final long SCAN_PERIOD = 10000;
    Handler handler = new Handler();



    //Declare bluetooth variables
    BluetoothAdapter bluetoothAdapter;
    BluetoothLeScanner bluetoothLeScanner;
    BluetoothGatt bluetoothGatt;
    //List of scanned devices



    //boolean for SCAN function, defaulted to false
    private boolean scanning;
    //boolean for sending RSSI signals




    private void changeText(final TextView text,final int value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(String.valueOf(value));
            }
        });
    }

    //Scan function called in the discover button
    @SuppressLint("MissingPermission")
    private void scanLeDevices() {
        if (!scanning) {
            // Stops scanning after a predefined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    bluetoothLeScanner.stopScan(leScanCallback);
                    mScanBtn.setText("Scan");
                }
            }, SCAN_PERIOD);

            scanning = true;
            mScanBtn.setText("Scanning...");
            bluetoothLeScanner.startScan(leScanCallback);
        }
    }


    // Device scan callback.
    private ScanCallback leScanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    if((result != null) && (result.getDevice() != null) && (result.getDevice().getName() != null)  ) {

                        if(result.getDevice().getName().equals("TESTING")){
                            BluetoothDevice device = result.getDevice();
                            try {
                                // connect to the GATT server on the device
                                bleConnect(device);

                            } catch (IllegalArgumentException exception) {
                                showToast("Not Connected");
                            }
                        }
                    }


                }
            };



    private BluetoothGattCallback bluetoothGattCallback;




    public void bleConnect(BluetoothDevice device){
        bluetoothGatt = device.connectGatt(this, false, bluetoothGattCallback);
    }


    private void close() {
        if (bluetoothGatt == null) {
            return;
        }
        bluetoothGatt.close();
        bluetoothGatt = null;
    }




    //MISC: Used to show alert messages
    private void showToast(String msg){
        if(msg != null){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        }

    }

    private Handler handler2 = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Perform readRemoteRssi
            // Obtain BluetoothGatt instance

            boolean result = bluetoothGatt.readRemoteRssi();
            // The result will be received in the onReadRemoteRssi callback method

            // Schedule the next execution after a delay (adjust as needed)
            handler.postDelayed(this, 1000);
        }
    };

// Start the periodic task



    //START OF EVERYTHING
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothGattCallback = new BluetoothGattCallback() {

            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                Log.v(TAG, "Connection change occured");
                Log.v(TAG, "status = " + status);
                Log.v(TAG, "newState = " + newState);


                Log.v(TAG, "BluetoothProfile.STATE_CONNECTED = " + BluetoothProfile.STATE_CONNECTED);

                if (newState == BluetoothProfile.STATE_CONNECTED) {




                    Log.v(TAG, "We are connected");
                    Log.v(TAG, "Trying to read rssi now...");



                    handler.postDelayed(runnable, 1000);






                    // successfully connected to the GATT Server
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    // disconnected from the GATT Server
                    Log.v(TAG, "Disconnected");

                }
            }
            @Override
            public void onReadRemoteRssi (BluetoothGatt gatt,int rssi, int status){
                Log.v(TAG, "OnReadRemoteRSSI");

                super.onReadRemoteRssi(gatt, rssi, status);
                Log.v(TAG, "Entered readremoterssi callback");
                if (status == gatt.GATT_SUCCESS) {

                    //mStatusBlueTv.setText("RSSI: " + rssi);
                    //SUCCESS!!!!
                    Log.v(TAG, "" + rssi);
                    //Change RSSI display
                    changeText(mStatusBlueTv, rssi);
                } else {
                    Log.v(TAG, "RSSI read not successful");
                }


            }
        };





        //Initialize some variables
        mOnBtn = findViewById(R.id.onBtn);
        mScanBtn = findViewById(R.id.scanBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mConnectBtn = findViewById(R.id.connectBtn);
        mFineBtn = findViewById(R.id.fineBtn);
        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mDeviceInfoTv = findViewById(R.id.deviceInfoTv);




        //Adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Case where device does not support bluetooth
        if(bluetoothAdapter == null){
            showToast("Bluetooth Adapter Not Detected");
        } else {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            if(bluetoothLeScanner == null){
                showToast("Bluetooth Low Energy Scanner not detected");
            }
        }





        //Ble connect permission
        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_CONNECT_PERMISSION);

                }
            }
        });

        mFineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Request the permission
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                }
            }
        });

        //Turn on bluetooth button
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent intentEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intentEnable, REQUEST_ENABLED_BT);
                    showToast("Bluetooth On");

                    //Get the ble scanner
                    bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

                }
                else{
                    showToast("Bluetooth already on");
                }
            }
        });

        //Scan button
        //TODO
        //Display list of devices
        //CHOOSE what device to connect to
        mScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_SCAN_PERMISSION);

                }



                // Location permission is already granted, start scanning
                //IDK if this will pass in the ArrayAdapter by value or by reference
                scanLeDevices();


            }
        });

        mOffBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                close();
                //Disconnect from device
            }
        });





    }




}






