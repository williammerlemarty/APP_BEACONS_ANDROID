package com.example.williammerle.workshop2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

    }

    private void doClickOnButtonConnexion() {
        Intent i = new Intent(getApplicationContext(), ConnexionActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
    private void doClickOnButtonInscription() {
        Intent i = new Intent(getApplicationContext(), InscriptionActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
}
