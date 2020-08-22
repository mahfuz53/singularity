package com.Singularity.store;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.model.jsondata.store.Datum;
import com.model.jsondata.store.StoreData;
import com.presenter.adapter.GridSpacingItemDecoration;
import com.presenter.adapter.RecycleViewClickListener;
import com.presenter.adapter.StoreAdapter;
import com.presenter.utils.AlphaNemericGenerator;
import com.presenter.utils.DataParseVolly;
import com.presenter.utils.FileUtils;
import com.presenter.utils.PromptRunnable;
import com.presenter.utils.print;
import com.presenter.view.AlertMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class StoreActivity extends AppCompatActivity implements RecycleViewClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 5 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private LocationRequest mLocationRequest;
    double lat = 0;
    double lan = 0;

    private DataParseVolly dataParse;
    Context con;
    ArrayList<HashMap<String, String>> storeDataList;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.appColorgreen));
            }
             getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


            setContentView(R.layout.store_page);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(getResources().getString(R.string.storelist));

            con             = this;
            dataParse       = new DataParseVolly();
            storeDataList   = new ArrayList<HashMap<String, String>>();

            LoadRecycleView();
            statusCheck();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

                LoadUrl("http://128.199.215.102:4040/api/stores",null,Request.Method.GET);


    }
    private void LoadUrl(String url_, JSONObject jsonObj_,int requestType)
    {
        //final int post = Request.Method.POST;

        dataParse.DataVolleyJson(con,url_,1,jsonObj_,requestType,new PromptRunnable(){
            @SuppressLint("NewApi")
            public void run() {
                boolean value_ = this.getValue();
                StoreData obj_ =  this.getStore();
                print.message("val"+value_);
                if(value_)
                {
                    LoadStoreData(obj_);
                }
                else
                {
                    FileUtils.showToast(con,getString(R.string.sorry));
                }
            }
        });
    }

    public void LoadStoreData(StoreData obj) {


            if (obj != null) {
                storeDataList.clear();

                for (final Datum v : obj.getInspectionPoints()){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", "" + v.getid());
                    map.put("name", "" + v.getname());
                    map.put("address", "" + v.getaddress());
                    storeDataList.add(map);
                }
                LoadStoreAdapter(storeDataList);
            } else {
                AlertMessage.showMessage(con, con.getString(R.string.app_name), getString(R.string.sorry));
            }

    }

    protected RecyclerView recyclerView;
    @TargetApi(Build.VERSION_CODES.M)
    private void LoadRecycleView()
    {
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(con, LinearLayoutManager.VERTICAL, false);//horizontal or vertical
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(con, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void LoadStoreAdapter(ArrayList<HashMap<String,String>> data) {

        if(data.size()==0) return;;
        StoreAdapter adapter = new StoreAdapter(con, data);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(StoreActivity.this);
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public void onClick(int position) {

        String id        = storeDataList.get(position).get("id");
        String name      = storeDataList.get(position).get("name");

        Intent intent 				= new Intent(  StoreActivity.this,  LoginActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name",   name);
        intent.putExtra("lat", lat);
        intent.putExtra("lan", lan);
        startActivity(intent);
    }


////////////////////////////////////Location////////////////////////////////////////
        @Override
        public void onConnected (Bundle bundle){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {
            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(StoreActivity.this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

        @Override
        public void onConnectionSuspended ( int i){
        mGoogleApiClient.connect();
        Toast.makeText(this, "Location not onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

        @Override
        public void onConnectionFailed (ConnectionResult connectionResult){
        Toast.makeText(this, "Location not onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

        @Override
        protected void onStart () {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

        @Override
        protected void onStop () {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

        protected void startLocationUpdates () {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

        @Override
        public void onLocationChanged (Location location){

        String msg = "Updated Location: " + Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());

        lat = location.getLatitude();
        lan = location.getLongitude();

    }
        public void statusCheck () {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showAlert();
        }

    }


        private void showAlert () {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }
}
