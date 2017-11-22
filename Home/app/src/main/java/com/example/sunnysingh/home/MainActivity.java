package com.example.sunnysingh.home;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT =1;
    List<String> dname = new ArrayList<String>();
    List<String> mac = new ArrayList<String>();
    BluetoothSocket btSocket;
    BluetoothDevice device;
    BluetoothAdapter mBluetoothAdapter;
    String address = "";
    TextView mydevice;
    int BLUETOOTH_REQUEST = 1;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydevice = (TextView) findViewById(R.id.device);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceAddress = device.getAddress();
                    if (deviceName.equals("HC-05") && deviceAddress.equals("98:D3:31:80:97:77"))
                    {
                       mydevice.setText("Master Bedroom");
                       address = "98:D3:31:80:97:77";

                    }

                }
            }
        }
        else {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceAddress = device.getAddress();
                    if (deviceName.equals("HC-05") && deviceAddress.equals("98:D3:31:80:97:77"))
                    {
                        mydevice.setText("Master Bedroom");
                        address = "98:D3:31:80:97:77";
                    }

                }
            }
        }
        mydevice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                address = "98:D3:31:80:97:77";
                Log.i("cool:",address);
               device = mBluetoothAdapter.getRemoteDevice(address);
                try {
                    btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

                }
                catch (IOException e)
                {
                    Toast.makeText(getBaseContext(),"Check & Reset your device ",Toast.LENGTH_LONG).show();
                }
                mBluetoothAdapter.cancelDiscovery();
                try {
                    btSocket.connect();
                    Toast.makeText(getBaseContext(),"Connected to Master Bedroom",Toast.LENGTH_LONG).show();
                    BTSocket.setSocket(btSocket);
                    Intent i = new Intent(MainActivity.this,CommunicationView.class);
                    startActivity(i);
                }
                catch (IOException e1)
                {
                    Toast.makeText(getBaseContext(),"Connect to correct device or Device Offline", Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    public static class BTSocket{

        private static BluetoothSocket socket;
        public  static synchronized BluetoothSocket getSocket(){
            return socket;
        }
        public static synchronized void setSocket(BluetoothSocket socket1){
            socket = socket1;
        }
    }
}
