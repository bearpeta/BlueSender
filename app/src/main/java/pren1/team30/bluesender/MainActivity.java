package pren1.team30.bluesender;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;
    private Button button_connect;
    private Button button_start;
    private BluetoothAdapter mBluetoothAdapter;
    private Chronometer chronometer;
    private ArrayList<BluetoothDevice> devices= new ArrayList<BluetoothDevice>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        button_connect = (Button) findViewById(R.id.buttonConnect);
        button_start = (Button) findViewById(R.id.buttonStart);

        chronometer = (Chronometer) findViewById(R.id.chronometer);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBluetooth();


        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_UUID);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);



        button_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_start.getText().equals("Start")) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    button_start.setText("Stop");
                }
                else{
                    chronometer.stop();
                    button_start.setText("Start");
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        unregisterReceiver(mReceiver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,"Bluetooth enabled",Toast.LENGTH_LONG).show();
                checkBluetooth();
            }
            else{
                Toast.makeText(this,"Bluetooth disabled",Toast.LENGTH_LONG).show();
            }

        }
    }

    private void checkBluetooth(){
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        else{
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else{
                getBondedDevices();
                mBluetoothAdapter.startDiscovery();
            }
        }
    }

    private void getBondedDevices(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                devices.add(device);
            }
        }

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                devices.add(device);
            }
        }
    };
}
