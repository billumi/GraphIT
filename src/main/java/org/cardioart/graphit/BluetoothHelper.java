package org.cardioart.graphit;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by jirawat on 10/05/2014.
 */
public class BluetoothHelper {
    public static final int REQUEST_ENABLE_BT = 2718;

    private BluetoothAdapter mBluetoothAdapter;
    private Context context;

    public BluetoothHelper(Context mcontext) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.context = mcontext;
        if (mBluetoothAdapter == null) {
            Log.d("Bluetooth", "Device didn't support bluetooth");
            System.exit(2);
        }
    }
    public void connectBluetooth() {
        if (mBluetoothAdapter.isEnabled() == false) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            MainActivity mainActivity = (MainActivity) this.context;
            mainActivity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        }
    }
}
