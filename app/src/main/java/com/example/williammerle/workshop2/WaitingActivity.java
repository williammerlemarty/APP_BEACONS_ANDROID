package com.example.williammerle.workshop2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WaitingActivity extends AppCompatActivity {
    private static int NUMBER_PLAYERS = 3;
    private static int PLAYERS_ACTIF = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        if(NUMBER_PLAYERS != 3) {
            TextView tv = (TextView) findViewById(R.id.player3);
            tv.setVisibility(View.INVISIBLE);
        }

        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButtonPlay();
                    }
                });
            }
        });

        play.setEnabled(false);

        //TODO :: FAIRE UN AJAX POUR CHERCHER LES ACTIF ET CHANGE LA CONSTANTE PLAYERS_ACTIF ET AFFICHER EN VERS ET ACTIVER LE BOUTON

        if(PLAYERS_ACTIF == NUMBER_PLAYERS){
            play.setEnabled(true);
        }

    }
    private void doClickOnButtonPlay() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
    public void onBackPressed() {
    }
}
