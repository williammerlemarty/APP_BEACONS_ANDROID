package com.example.williammerle.workshop2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.altbeacon.beacon.BeaconConsumer;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class DashboardActivity extends AppCompatActivity implements BeaconConsumer {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BeaconManager beaconManager;
    public static int SECONDES = 120000; // nb secondes + 000
    public static final int NUMBER_PLAYERS = 3;
    public static final Class JEU1 = MjLeftRightActivity.class;
    public static final Class JEU2 = MjMathsActivity.class;
    public static final Class JEU3 = MjPushActivity.class;
    public static final Class JEU4 = MjTopBottomActivity.class;
    public static final Class JEU5 = MjWhipActivity.class;

    public static final Identifier B1 = Identifier.parse("381");
    public static final Identifier B2 = Identifier.parse("384");
    public static final Identifier B3 = Identifier.parse("385");

    public static int A1 = 0;
    public static int A2 = 0;
    public static int A3 = 0;

    public static final Date CREATE_DATE = new Date();
    public static final long GET_TIME = CREATE_DATE.getTime();
    public static final String TO_STRING_TIMESTAMP = ""+GET_TIME;
    public static final int HASH_CODE = TO_STRING_TIMESTAMP.hashCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /////////////BEACON //////////////////
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

        //////////////////////////////////////
        ////////////////TIMER////////////////
        //////////////////////////////////////

        ////////////////VERIFICATION EN COURS OU NON ////////////////

        final TextView timer = (TextView) findViewById(R.id.time);

        File internal = getFilesDir();
        File f = new File(internal, "" + HASH_CODE);
        if(f.exists() && f.isFile()) {
            try {
                String readTime = this.readFile(f);
                long readTimeInt = Long.parseLong(readTime);

                Date d = new Date();
                long getTimeNow = d.getTime();

                long dif = (getTimeNow - readTimeInt);

                int finalDif = (int) dif;
                SECONDES = SECONDES - finalDif;
                Log.e("LOL",""+SECONDES);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //////////////// AFFICHAGE DU TIMER ////////////////

        new CountDownTimer(SECONDES, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Temps restant: " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                new AlertDialog.Builder(DashboardActivity.this)
                        .setTitle("Perdu ! Le temps est écoulé! ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getApplicationContext(), ActionPartyActivity.class);
                                startActivity(i);
                            }
                        }).setCancelable(false)
                        .show();
            }
        }.start();

         //////////////////////////////////////////////////////
        ////////////////TABLEAU DES MISSIONS ////////////////
        ////////////////////////////////////////////////////

        TableLayout tl = (TableLayout) findViewById(R.id.tablelayout);

        for (int i = 1; i<=NUMBER_PLAYERS; i++) {
        /* Create a new row  */
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        /* Create cols */
            TextView column1 = new TextView(this);
            column1.setText("Nom du jeu");
            column1.setGravity(Gravity.LEFT | Gravity.CENTER);
            tr.addView(column1);

            TextView column2 = new TextView(this);
            column2.setText("Temps");
            column2.setGravity(Gravity.RIGHT | Gravity.CENTER);
            tr.addView(column2);

            TextView column3 = new TextView(this);
            column3.setText(">");
            column3.setGravity(Gravity.RIGHT | Gravity.CENTER);
            tr.addView(column3);

            //////////////// AFFICHAGE EN ATTENDANT ////////////////

            Class jeu = null;
            Identifier bc = null;
            if(i == 1 ){
                jeu = JEU1;
                bc = B1;
            }
            else if(i == 2 ){
                jeu = JEU2;
                bc = B2;
            }
            else if(i == 3 ){
                jeu = JEU3;
                bc = B1;
            }
            else if(i == 4 ){
                jeu = JEU4;
                bc = B2;
            }
            else if(i == 5 ){
                jeu = JEU5;
                bc = B1;
            }
            final Class finalJeu = jeu;
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            doClickOnButton(finalJeu);
                        }
                    });
                }
            });

        /* Add row to TableLayout. */

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
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
        beaconManager.bind(this);
    }

    public void onBeaconServiceConnect() {
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                for (Beacon beacon : beacons) {
                    Log.e("Distance",""+beacon.getDistance());
                    Log.e("ID",""+beacon.getId3());
                    if(beacon.getDistance() <= 1){
                        if(beacon.getId3() == B1){
                            A1 = 1;
                        }
                        if(beacon.getId3() == B2){
                            A2 = 1;
                        }
                        if(beacon.getId3() == B3){
                            A3 = 1;
                        }
                    }else{
                        if(beacon.getId3() == B1){
                            A1 = 0;
                        }
                        if(beacon.getId3() == B2){
                            A2 = 0;
                        }
                        if(beacon.getId3() == B3){
                            A3= 0;
                        }
                    }
                    Log.e("B1",""+B1);
                    Log.e("B2",""+B2);
                    Log.e("B3",""+B3);
                    Log.e("A1",""+A1);
                    Log.e("A2",""+A2);
                    Log.e("A3",""+A3);
                }
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {

        }
    }

    private void doClickOnButton(Class actvity) { //.class

        // TODO :: AUTHORISER LE CLIC SI LA DISTANCE EST < 1
        ////////////////////////////////////////////////////////////
        ////////////////FICHIER CACHE POUR LE TIMER ////////////////
        ///////////////////////////////////////////////////////////

        File internal = getFilesDir();
        File f = new File(internal, "" + HASH_CODE);

        // On définit la différence de temps entre la recherche et la date de création du fichier
        Date d = new Date();
        long gt = d.getTime();
        String time = Long.toString(gt);

        if(f.exists() && f.isFile()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                this.writeFile(f, time);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // On va ecrire le HTML dans son fichier
            try {
                this.writeFile(f, time);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent i = new Intent(getApplicationContext(), actvity);
        startActivity(i);
    }

    private void writeFile(File f, String s) throws IOException {
        try {
            FileOutputStream isw = null; // je place ma tete de lecture, je vais lire les octets
            isw = new FileOutputStream(f,false);
            OutputStreamWriter isrw = new OutputStreamWriter(isw); // va permettre au Buffer de lire (il traduit)
            BufferedWriter brw = new BufferedWriter(isrw); // Je vais le lire

            try {
                brw.write(s);// je vais lire telle ligne traduit des octets en chaine de caractères
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                brw.flush();
                brw.close();// je fermer le fichier
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private String readFile(File f) throws IOException {

        FileInputStream is = new FileInputStream(f);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
    public void onBackPressed() {
    }
}
