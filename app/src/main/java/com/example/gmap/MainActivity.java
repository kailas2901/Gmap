package com.example.gmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button Showmap, GetLc, LocList;
    TextView Loc;
    LocationManager locationManager;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = longitude = "";

        Showmap = findViewById(R.id.b_showmap);
        GetLc = findViewById(R.id.b_GetCL);
        LocList = findViewById(R.id.b_LocationList);
        Loc = findViewById(R.id.LOCATION);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(new Criteria(), false);

        String provider = "";
        for (String pro : providers) {
            provider += pro;
        }
        Toast.makeText(this, provider, Toast.LENGTH_SHORT).show();

        GetLc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean providerenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (!providerenabled) {
                    EnableGPS();
                } else {
                    GetLocation();
                }
            }
        });

    }

    private void EnableGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog alert = builder.create();
        alert.show();

    }

    private void GetLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            String[] perm = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(this, perm,1);

        }else {
           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
               @Override
               public void onLocationChanged(@NonNull Location location) {
                   if(!location.equals(null))
                       Toast.makeText(MainActivity.this, "Latitude :" + location.getLatitude()+ "\n Longitude:"+location.getLongitude(), Toast.LENGTH_SHORT).show();
               }
           });

            }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            String[] perm = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(this, perm,1);

        }else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null){
               latitude = location.getLatitude()+"";
                longitude = location.getLongitude()+"";
                Loc.setText("Latitude :" + latitude+ "\n Longitude:"+longitude );

            }
        }

    }

}