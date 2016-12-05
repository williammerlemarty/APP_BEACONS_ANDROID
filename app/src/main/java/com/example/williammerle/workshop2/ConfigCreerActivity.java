package com.example.williammerle.workshop2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.altbeacon.beacon.BeaconConsumer;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class ConfigCreerActivity extends AppCompatActivity implements BeaconConsumer {

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static int NUMBER_PLAYERS = 3;
    private Button next;
    private BeaconManager beaconManager;
    private Identifier a1 = null;
    private Identifier b1 = null ;
    private Identifier c1 = null;
    private Identifier a2 = null;
    private Identifier b2 = null ;
    private Identifier c2 = null;
    private String a3 = null;
    private String b3 = null ;
    private String c3 = null;
    private int a4 = 0;
    private int b4 = 0 ;
    private int c4 = 0;
    private int i1 = 0;
    private int i2 = 0 ;
    private int i3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_creer);

        new AlertDialog.Builder(ConfigCreerActivity.this)
                .setTitle("Configuration des Beacons")
                .setMessage("Approchez vous à moins d'1 mètre de chacun des Beacon (1 par joueur) avec le mobile du créateur pour détecter les beacons")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}})
                .show();

        next = (Button) findViewById(R.id.next);
        //next.setEnabled(false);

        if(NUMBER_PLAYERS != 3) {
            ImageView imgView = (ImageView) findViewById(R.id.b3);
            imgView.setVisibility(View.INVISIBLE);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButtonCreate2();
                    }
                });
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);

                    }
                });
                builder.show();
            }
        }
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
                public void onBeaconServiceConnect() {
                    BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
                    beaconManager.setRangeNotifier(new RangeNotifier() {
                        @Override
                        public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                for (Beacon beacon : beacons) {
                    if(a1 == null && beacon.getDistance() <= 1){

                        a1 = beacon.getId3();
                        a2 = beacon.getId2();

                        a3 = String.valueOf(a1);
                        a4 = Integer.parseInt(a3);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageView img = (ImageView) findViewById(R.id.b1);
                                img.setImageResource(R.drawable.bg1);
                            }
                        });
                        if(i1 == 0){
                            // TODO: insert en tant que beacon 1

                        }
                    }

                    if(a1 != null && b1 == null && beacon.getDistance() <= 1 && beacon.getId3() != a1){
                        b1 = beacon.getId3();
                        b2 = beacon.getId2();

                        b3 = String.valueOf(b1);
                        b4 = Integer.parseInt(b3);

                        if(a4 == b4){
                            b1 = null;
                            b2 = null;
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ImageView img = (ImageView) findViewById(R.id.b2) ;
                                    img.setImageResource(R.drawable.bg2);
                                }
                            });


                            if(i2 == 0){
                                // TODO: insert en tant que beacon 2

                            }
                        }
                    }

                    if(NUMBER_PLAYERS == 3) {
                        if (a1 != null && b1 != null && c1 == null && beacon.getDistance() <= 1 && beacon.getId3() != a1 && beacon.getId3() != b1) {
                            c1 = beacon.getId3();
                            c2 = beacon.getId2();

                            c3 = String.valueOf(c1);
                            c4 = Integer.parseInt(c3);

                            if(a4 == c4 || b4 == c4){
                                c1 = null;
                                c2 = null;
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ImageView img = (ImageView) findViewById(R.id.b3);
                                        img.setImageResource(R.drawable.bg3);
                                    }
                                });

                                if(i3 == 0){
                                    // TODO: insert en tant que beacon 3

                                }
                            }
                            // insert en tant que beacon 3
                        }
                    }
                }
                if(NUMBER_PLAYERS == 3) {
                    if(a1 != null & b1 != null && c1 != null){
                        //INSERT EN BDD
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                next.setEnabled(true);
                            }
                        });
                    }
                }
                else if(NUMBER_PLAYERS == 2) {
                    if(a1 != null & b1 != null){
                        //INSERT EN BDD
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                next.setEnabled(true);
                            }
                        });
                    }
                }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }


    private void doClickOnButtonCreate2() {
        Intent i = new Intent(getApplicationContext(), InstalationActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
    public void onBackPressed() {
    }
}
