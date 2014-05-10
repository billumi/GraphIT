package org.cardioart.graphit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    public BluetoothHelper bluetoothHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothHelper = new BluetoothHelper(this);
        bluetoothHelper.connectBluetooth();

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> device_names = new ArrayList<String>();
        device_names.add("AA (GraphView)");
        device_names.add("BB (GraphView)");
        device_names.add("CC (AndroidPlot)");
        device_names.add("EE (AndroidPlot)");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.device_list, R.id.textViewDeviceName, device_names);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String device_name = (String) adapterView.getItemAtPosition(i);
                //Toast.makeText(getApplicationContext(), device_name + " select", Toast.LENGTH_SHORT).show();
                Intent graphIntent;
                if (i > 1) {
                    graphIntent = new Intent(getApplicationContext(), PlotActivity.class);
                } else {
                    graphIntent = new Intent(getApplicationContext(), GraphActivity.class);
                }
                graphIntent.putExtra("device_name", device_name);
                startActivity(graphIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Setting", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_refresh) {
            Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothHelper.REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_CANCELED) {
                System.exit(2);
            }
        }
    }
}