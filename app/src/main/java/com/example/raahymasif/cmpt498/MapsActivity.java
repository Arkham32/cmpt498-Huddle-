package com.example.raahymasif.cmpt498;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.sax.StartElementListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.SphericalUtil;

import java.util.Map;
import android.content.Context;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.View;

import static java.lang.String.format;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    int a;
    public GoogleMap mMap;
    public LatLng newlocate;
    LocationManager locationManager;
    LocationListener mlocListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        loadActivity();


    }

    @Override
    protected void onResume(){
        loadActivity();
        super.onResume();

    }

    @Override
    protected void onPause(){
        mMap = null;
        super.onPause();

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
        mMap.setMyLocationEnabled(true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 3 seconds



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Posts");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        get_Location((Map <String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

            }
        }, 500);




        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.2f));
    }

    private void get_Location(Map<String,Object> users) {

        ArrayList<String> location = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            location.add((String) singleUser.get("location"));
        }

        ArrayList<String> locationID = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            locationID.add((String) singleUser.get("id"));
        }
        //System.out.println("=====================================================================");

        //System.out.println (location.toString());
        //System.out.println(g.toString());
        for (String g: location) {
            if (g != null) {
                for(String i: locationID) {
                        if(i != null) {
                            getLocationFromAddress(this, g, i);
                        }
                }
            }
        }

    }

    public void getLocationFromAddress(Context context, String inputtedAddress, final String id){
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng resLatLng;
        Double distance;
        int test;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5);
            if (address == null) {
                return;
            }

            if (address.size() == 0) {
                return;
            }

            Address location = address.get(0);
            //location.getLatitude();
            //location.getLongitude();
            //test = location.getLatitude();
            if(newlocate == null) {
                System.out.println(a);
            }
            if(newlocate != null) {
                resLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                distance = SphericalUtil.computeDistanceBetween(newlocate, resLatLng);
                distance = distance / 1000;
                distance = Math.round(distance * 10.0) / 10.0;
                String distancefromlocation = (String.valueOf(distance));
                Bundle extras = getIntent().getExtras();
                final String username = extras.getString("username");
                final String email = extras.getString("email");
                //int a = Integer.parseInt(distancefromlocation);

                Marker marker = mMap.addMarker(new MarkerOptions().position(resLatLng).title(distancefromlocation + " km"));
                marker.setTag(id);
                //mMap.addMarker(new MarkerOptions().position(resLatLng).title(distancefromlocation + " km"));

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //Intent newintent = new Intent(MapsActivity.this, PopupwindowActivity.class);
                        //newintent.putExtra("post_key", marker1.getTag().toString());
                        //newintent.putExtra("user_name", username);
                        //newintent.putExtra("email", email);
                        //startActivity(newintent);
                        System.out.println(marker.getTag());



                        return false;
                    }
                });


            }}catch (IOException ex) {

            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        //return resLatLng;
    }

    private void loadActivity() {


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



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

        Location oldlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (oldlocation != null) {
            double oldlat = oldlocation.getLatitude();
            double oldlong = oldlocation.getLongitude();
            LatLng oldloc = new LatLng(oldlat, oldlong);
            newlocate = oldloc;
        }


        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mlocListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();
                        if(mMap != null) {
                            mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        }
                        a = 5;
                        newlocate = latLng;
                        String s = "no";
                        s = getIntent().getStringExtra("buttonclicked");
                        if (s.equals("yes")) {
                            if(mMap != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                            }
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);

        } else {
            if (locationManager.isProviderEnabled((LocationManager.GPS_PROVIDER))) {
                mlocListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String str = addressList.get(0).getLocality() + ",";
                            str += addressList.get(0).getCountryName();
                            if(mMap != null) {
                                mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                            }
                            a = 5;
                            newlocate = latLng;
                            String s = "no";
                            s = getIntent().getStringExtra("buttonclicked");
                            if (s.equals("yes")) {
                                if(mMap != null) {
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                                }

                            }

                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

            }

        }
    }

}

