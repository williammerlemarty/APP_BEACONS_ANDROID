package com.example.williammerle.workshop2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MjPushActivity extends AppCompatActivity {
    public static final int PUSH_NUMBER = 100;
    public static final int SECONDES = 60000;//+000
    private CountDownTimer ctnd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mj_push);
        final TextView time = (TextView) findViewById(R.id.time);
        new AlertDialog.Builder(MjPushActivity.this)
                .setTitle("Jeu push")
                .setMessage("Le but du jeu est d'appuyer sur le bouton "+ PUSH_NUMBER +" le plus vite possible ! Vous avez 1 minute")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ctnd =  new CountDownTimer(SECONDES, 1000) {

                            public void onTick(long millisUntilFinished) {
                                time.setText("Temps restant: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {

                                new AlertDialog.Builder(MjPushActivity.this)
                                        .setTitle("Perdu ! Le temps est écoulé! ")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                                startActivity(i);
                                            }
                                        }).setCancelable(false)
                                        .show();
                            }
                        };
                        ctnd.start();
                    }
                }).setCancelable(false)
                .show();
        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            int score = 0;
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    if(score == PUSH_NUMBER){
                        ctnd.cancel();
                        new AlertDialog.Builder(MjPushActivity.this)
                                .setTitle("Bravo vous avez gagné !")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                        startActivity(i);
                                    }
                                }).setCancelable(false)
                                .show();
                    }
                    score = score+1;
                    TextView scores = (TextView) findViewById(R.id.score);
                    scores.setText(""+score);

                    }
                });
            }
        });
    }
    public void onBackPressed() {
    }
}
