package com.amrayoub.cookpadtask;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  implements
        View.OnClickListener,
        OnMapReadyCallback{
    weatherInfo []myinfo= new weatherInfo[5];
    List<weatherInfo> weatherInfoArrayList=new ArrayList<>();
    String url,city_name,country;
    private LatLng latLng;
    private Dialog dialog;
    private GoogleMap mMap;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private WeatherAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton)findViewById(R.id.fab);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_today:
                // User chose the "Settings" item, show the app settings UI...
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                mAdapter = new WeatherAdapter((List<weatherInfo>) weatherInfoArrayList.get(0));
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                return true;

            case R.id.action_weekly:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                mAdapter = new WeatherAdapter(weatherInfoArrayList);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void onClick(View v) {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogmap);
        dialog.show();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng lat) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat,1));
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                mMap.addMarker(new MarkerOptions()
                        .position(lat)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                latLng = lat;
            }
        });
    }
    public void OK(View view) throws IOException {
        //lat to get weather
        url="https://api.openweathermap.org/data/2.5/forecast?lat="+(int)latLng.latitude+"&lon="
                +(int)latLng.longitude+"&appid=27629803342c85d48d290ab478e8264f";
        TempAsyncTask jsonAsync = new TempAsyncTask();
        jsonAsync.execute(url);
        dialog.dismiss();
    }
    private String JSONstrongConnection(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return data;
    }
    private class TempAsyncTask extends AsyncTask<String, Void, String> {
        //get JSON-string in background thread
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait... 22");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = JSONstrongConnection(url[0]);
                JSONObject jsonObject = new JSONObject(data);
                JSONObject cityname = jsonObject.getJSONObject("city");
                city_name = cityname.getString("name");
                country = cityname.getString("country");
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject theObject = jsonArray.getJSONObject(i);
                    JSONObject main = theObject.getJSONObject("main");
                    myinfo[i].setTemp((String) main.get("temp"));
                    myinfo[i].setTemp_min((String) main.get("temp_min"));
                    myinfo[i].setTemp_max((String) main.get("temp_max"));
                    myinfo[i].setPressure((String) main.get("pressure"));
                    myinfo[i].setHumidity((String) main.get("humidity"));
                    JSONArray weatherarray = theObject.getJSONArray("weather");
                    JSONObject weatherobj = weatherarray.getJSONObject(0);
                    myinfo[i].setWeather_clouds(weatherobj.getString("main"));
                    myinfo[i].setWeather_desc(weatherobj.getString("description"));
                    myinfo[i].setWeather_ico(weatherobj.getString("icon"));

                    /*JSONObject speed = theObject.getJSONObject("speed");
                    myinfo[i].setWind_speed(speed.toString());
                    JSONObject deg = theObject.getJSONObject("deg");
                    myinfo[i].setWind_deg(deg.toString());*/
                }

            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            weatherInfoArrayList = Arrays.asList(myinfo);
            progressDialog.dismiss();
        }
    }
}
//3759086f56e7015e480ab323c6a41315
//api.openweathermap.org/data/2.5/weather?lat=35&lon=139&APPID={APIKEY}
//api.openweathermap.org/data/2.5/weather?q={city name}&APPID={APIKEY}
//api.openweathermap.org/data/2.5/forecast/daily?lat={lat}&lon={lon}&cnt={7}&APPID={APIKEY}

