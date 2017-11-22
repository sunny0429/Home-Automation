package com.example.sunnysingh.home;

import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sunny Singh on 1/3/2017.
 */

public class CommunicationView extends AppCompatActivity {

    private OutputStream outStream = null;

    /* input stream used to read data from Bluetooth module */
   // private InputStream inStream;
    BluetoothSocket btsocket;

    RelativeLayout Tvon;
    RelativeLayout Tvoff;
    RelativeLayout Settopboxon;
    RelativeLayout Settopboxoff;
    RelativeLayout Switchon;
    RelativeLayout Switchoff;
    SharedPreferences sharedpreferences;
    public static final String Mypreferencestv="Mypreftv";
    public static final String Mypreferencesset="Myprefset";
    public static final String Mypreferencessw="Myprefsw";
    public static final String tv="";
    public static final String settopbox="";
    public static final String powerswitch="";

    private ImageView btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication_view);
        btsocket = MainActivity.BTSocket.getSocket();
        try {
            outStream = btsocket.getOutputStream();
            // inStream = btsocket.getInputStream();
        } catch (IOException e) {
            Log.d("","outsream exception raised");
        }
        btnSpeak = (ImageView) findViewById(R.id.btnSpeak);

        Log.d("tv",tv);
        Log.d("settopbox",settopbox);
        Log.d("switch",powerswitch);
        SharedPreferences prefstv = getSharedPreferences(Mypreferencestv, MODE_PRIVATE);
        SharedPreferences prefsset = getSharedPreferences(Mypreferencesset, MODE_PRIVATE);
        SharedPreferences prefssw = getSharedPreferences(Mypreferencessw, MODE_PRIVATE);

        String tvstatus = prefstv.getString(tv, null);


        String settopboxstatus = prefsset.getString(settopbox, null);
        String powerswitchstatus = prefssw.getString(powerswitch, null);
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);


    /*    if(prefs.getString(first,null)==null)
        {
            Log.i("cool:","inloop");

            Tvoff.setVisibility(View.VISIBLE);
            Settopboxoff.setVisibility(View.VISIBLE);
            Switchoff.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(first,"cool");
            editor.commit();
            Log.i("cool:",prefs.getString(first,null));

        }*/
        if(tvstatus!=null && settopboxstatus!=null && powerswitchstatus!=null) {
            /*if(tv.equals("") && settopbox.equals("") && powerswitch.equals(""))
            {
                Tvoff.setVisibility(View.VISIBLE);
                Settopboxoff.setVisibility(View.VISIBLE);
                Switchoff.setVisibility(View.VISIBLE);
            }*/
            if (tvstatus.equals("on")) {
                Tvon.setVisibility(View.VISIBLE);
                Tvoff.setVisibility(View.INVISIBLE);
            }
            if (tvstatus.equals("off")) {
                Tvon.setVisibility(View.INVISIBLE);
                Tvoff.setVisibility(View.VISIBLE);
            }
            if (settopboxstatus.equals("on")) {
                Settopboxon.setVisibility(View.VISIBLE);
                Settopboxoff.setVisibility(View.INVISIBLE);
            }
            if (settopboxstatus.equals("off")) {
                Settopboxon.setVisibility(View.INVISIBLE);
                Settopboxoff.setVisibility(View.VISIBLE);
            }
            if (powerswitchstatus.equals("on")) {
                Switchon.setVisibility(View.VISIBLE);
                Switchoff.setVisibility(View.INVISIBLE);
            }
            if (powerswitchstatus.equals("off")) {
                Switchon.setVisibility(View.INVISIBLE);
                Switchoff.setVisibility(View.VISIBLE);
            }
        }

else{
            Switchoff.setVisibility(View.VISIBLE);
            Settopboxoff.setVisibility(View.VISIBLE);
            Tvoff.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor1 = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();
            SharedPreferences.Editor editor2 = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();
            SharedPreferences.Editor editor3= getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();
            editor1.putString(tv,"off");
            editor1.commit();
            editor2.putString(settopbox,"off");
            editor2.commit();
            editor3.putString(powerswitch,"off");
            editor3.commit();

        }


        // Keep listening to the InputStream until an exception occurs
/* uncomment this to recieve message from hc05
    final Handler handler  = new Handler();
     Runnable runnable = new Runnable() {

         @Override
         public void run() {
             while (true) {
                 try {
                     // Read from the InputStream
                     byte buffer[];
                     buffer = new byte[1024];
                     //Read is synchronous call which keeps on waiting until data is available
                     int bytes = inStream.read(buffer);
                     message = new String(buffer, 0, bytes);
                     Log.i("", message);
                     handler.post(new Runnable() {
                         @Override
                         public void run() {
                            // view.append(message);
                         }
                     });

                 } catch (IOException e) {
                     break;
                 }
             }

         }
     };
        new Thread(runnable).start();
*/
    }/*to send bytes of data to bluetooth
    public void send(View v) {
       // String message = text.getText().toString();
        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }*/

    public void mic(View v){
        promptSpeechInput();

    }
    public void tvon(View v) {
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();
        String message="1";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Tvon.setVisibility(View.INVISIBLE);
            Tvoff.setVisibility(View.VISIBLE);
            editor.putString(tv,"off");
            editor.commit();
            Toast.makeText(getBaseContext(),"Television switched OFF",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void tvoff(View v) {
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        String message="2";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Tvon.setVisibility(View.VISIBLE);
            Tvoff.setVisibility(View.INVISIBLE);
            editor.putString(tv,"on");
            editor.commit();
            Toast.makeText(getBaseContext(),"Television switched ON",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void settopboxon(View v) {
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();


        String message="3";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Settopboxon.setVisibility(View.INVISIBLE);
            Settopboxoff.setVisibility(View.VISIBLE);
            editor.putString(settopbox,"off");
            editor.commit();
            Toast.makeText(getBaseContext(),"Set Top Box switched OFF",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void settopboxoff(View v) {
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        String message="4";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Settopboxon.setVisibility(View.VISIBLE);
            Settopboxoff.setVisibility(View.INVISIBLE);
            editor.putString(settopbox,"on");
            editor.commit();
            Toast.makeText(getBaseContext(),"Set Top Box switched ON",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void switchon(View v) {
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();


        String message="5";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Switchon.setVisibility(View.INVISIBLE);
            Switchoff.setVisibility(View.VISIBLE);
            editor.putString(powerswitch,"off");
            editor.commit();
            Toast.makeText(getBaseContext(),"Power Switch switched OFF",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void switchoff(View v) {
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);
        String message="6";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Switchon.setVisibility(View.VISIBLE);
            Switchoff.setVisibility(View.INVISIBLE);
            editor.putString(powerswitch,"on");
            editor.commit();
            Toast.makeText(getBaseContext(),"Power Switch switched ON",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void tvon() {
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();
        String message="1";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Tvon.setVisibility(View.INVISIBLE);
            Tvoff.setVisibility(View.VISIBLE);
            editor.putString(tv,"off");
            editor.commit();
            Toast.makeText(getBaseContext(),"Television switched OFF",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void tvoff() {
        Tvon = (RelativeLayout) findViewById(R.id.tvon);
        Tvoff = (RelativeLayout) findViewById(R.id.tvoff);
        String message="2";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencestv, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Tvon.setVisibility(View.VISIBLE);
            Tvoff.setVisibility(View.INVISIBLE);
            editor.putString(tv,"on");
            editor.commit();
            Toast.makeText(getBaseContext(),"Television switched ON",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void settopboxon() {
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();


        String message="3";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Settopboxon.setVisibility(View.INVISIBLE);
            Settopboxoff.setVisibility(View.VISIBLE);
            editor.putString(settopbox,"off");
            editor.commit();
            Toast.makeText(getBaseContext(),"Set Top Box switched OFF",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void settopboxoff() {
        Settopboxon = (RelativeLayout) findViewById(R.id.settopboxon);
        Settopboxoff = (RelativeLayout) findViewById(R.id.settopboxoff);
        String message="4";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencesset, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Settopboxon.setVisibility(View.VISIBLE);
            Settopboxoff.setVisibility(View.INVISIBLE);
            editor.putString(settopbox,"on");
            editor.commit();
            Toast.makeText(getBaseContext(),"Set Top Box switched ON",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void switchon() {
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();


        String message="5";

        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Switchon.setVisibility(View.INVISIBLE);
            Switchoff.setVisibility(View.VISIBLE);
            editor.putString(powerswitch,"off");
            editor.commit();
            Toast.makeText(getBaseContext(),"Power Switch switched OFF",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    public void switchoff() {
        Switchon = (RelativeLayout) findViewById(R.id.switchon);
        Switchoff = (RelativeLayout) findViewById(R.id.switchoff);
        String message="6";
        SharedPreferences.Editor editor = getSharedPreferences(Mypreferencessw, MODE_PRIVATE).edit();


        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
            Switchon.setVisibility(View.VISIBLE);
            Switchoff.setVisibility(View.INVISIBLE);
            editor.putString(powerswitch,"on");
            editor.commit();
            Toast.makeText(getBaseContext(),"Power Switch switched ON",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.i("","In onResume() and an exception occurred during write: " + e.getMessage());
        }
    }
    @Override
    public void onBackPressed() {
        // Write your code here
        //super.onBackPressed();
try {
    btsocket.close();
    super.onBackPressed();
    this.finish();
}catch(IOException e){
    Log.e("notclosed","btsocket");
}
}





    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "speech not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String speech = result.get(0);

                    if(speech.equals("TV on"))
                    {
                        tvoff();
                    }
                    if(speech.equals("TV off"))
                    {
                        tvon();
                    }
                    if(speech.equals("Airtel on"))
                    {
                        settopboxoff();
                    }
                    if(speech.equals("Airtel of"))
                    {
                        settopboxon();
                    }
                    if(speech.equals("power switch on"))
                    {
                        switchoff();
                    }
                    if(speech.equals("all of"))
                    {
                        switchon();
                        settopboxon();
                        tvon();
                    }
                    if(speech.equals("all on"))
                    {
                        switchoff();
                        settopboxoff();
                        tvoff();
                    }
                    if(speech.equals("power switch off"))
                    {
                        switchon();
                    }
                    break;

                }
            }
        }
    }


    }
