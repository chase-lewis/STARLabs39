package com.starlabs.h2o.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.starlabs.h2o.R;
import com.starlabs.h2o.model.report.WaterReport;
import com.starlabs.h2o.model.user.User;

public class WaterReportMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private User user;
    public static final String TO_REPORT_USER = "TO_WATER_REPORT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        user = getIntent().getParcelableExtra(TO_REPORT_USER);


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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("waterReports").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numReports = 0;
                for (DataSnapshot report : dataSnapshot.getChildren()) {
                    WaterReport tempReport = report.getValue(WaterReport.class);
                    double latitude = tempReport.getLatitude();
                    double longitude = tempReport.getLongitude();
                    LatLng markLocation = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(markLocation).title(tempReport.toString()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Do nothing
            }
        });

         /*
        * Listens for a tap gesture on the map and then proceeds to the screen for report creation.
        * */
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(WaterReportMapActivity.this,CreateWaterReportActivity.class);
                intent.putExtra("latitude",latLng.latitude);
                intent.putExtra("longitude",latLng.longitude);
                intent.putExtra("fromMapClick","fromMapClick");
                intent.putExtra(CreateWaterReportActivity.TO_REPORT_USER, user);
                startActivity(intent);
            }
        });
    }
}