package io.zetaphase.hackerup;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Handler mHandler;
    private String serverAddress = "192.168.43.198:5000";
    int statusCode;
    boolean pingSuccess = true;
    String MYPREFERENCES = "HACKERUP";
    LocationManager mLocationManager;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private double nearbyRadius; // in kilometers
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nearbyRadius = 1.5;
        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();
        Log.d("DRAWERLISTCREATED", "hi");
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                if(position==0){
                    //adjust radius stuff
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Adjust the Radius");
                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nearbyRadius = Integer.valueOf(input.getText().toString());
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }else if(position==1){
                    //log out
                    SharedPreferences sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mActivityTitle = getTitle().toString();
        setupDrawer();
        mHandler = new Handler();
        startRepeatingTask();
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d("PING", "pinging");
                // TODO: Ping server here
                Location myLocation = getLastKnownLocation();
                final double latitude = myLocation.getLatitude();
                final double longitude = myLocation.getLongitude();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject position = new JSONObject();
                        try {
                            position.put("latitude", latitude);
                            position.put("longitude", longitude);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("OBJECT", position.toString());
                        SharedPreferences sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
                        String apikey = sharedpreferences.getString("ApiKey", "null");
                        int a = request("http://" + serverAddress + "/k/ping?apikey=" + apikey, position);

                        setStatusCode(a);
                        Log.d("STATUSCODE", "" + getStatusCode());
                    }
                });

                thread.start();
                thread.join();

                if (getStatusCode() == 200) {
                    pingSuccess = true;
                } else {
                    pingSuccess = false;
                    Toast.makeText(MainActivity.this, "Stuff is bad.", Toast.LENGTH_SHORT);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //repeat this runnable
                mHandler.postDelayed(mStatusChecker, 15000);
            }
        }
    };

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    public void setStatusCode(int statusCode){ this.statusCode = statusCode; }
    public int getStatusCode(){ return this.statusCode; }

    private int request(String urlString, JSONObject jsonObj) {
        // TODO Auto-generated method stub

        StringBuffer chaine = new StringBuffer("");
        int code = -1;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoInput(true);
            connection.connect();


            Log.d("REQUESTOUTPUT", "requesting");
            byte[] b = jsonObj.toString().getBytes();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(b);

            code = connection.getResponseCode();

            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        }

        Log.d("STATUSCODE", ""+code);

        return code;
    }

    private void addDrawerItems() {
        String[] osArray = {"Adjust Radius", "Log Out"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

}
