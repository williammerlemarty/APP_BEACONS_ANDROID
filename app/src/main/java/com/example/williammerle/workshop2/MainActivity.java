package com.example.williammerle.workshop2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Button co = (Button) findViewById(R.id.connexion);
        co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButtonConnexion();
                    }
                });
            }
        });

        Button ins = (Button) findViewById(R.id.inscription);
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButtonInscription();
                    }
                });
            }
        });

        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButtonHome();
                    }
                });
            }
        });
    }

    private void doClickOnButtonConnexion() {
        Intent i = new Intent(getApplicationContext(), ConnexionActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
    private void doClickOnButtonInscription() {
        Intent i = new Intent(getApplicationContext(), InscriptionActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
    private void doClickOnButtonHome() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
}
