package com.example.williammerle.workshop2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.Objects;

public class MjTopBottomActivity extends AppCompatActivity {
    public static final int AR_NUMBER = 50;
    public static final int SECONDES = 60000;//+000
    private CountDownTimer ctnd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mj_top_bottom);
        final TextView time = (TextView) findViewById(R.id.time);

        new AlertDialog.Builder(MjTopBottomActivity.this)
                .setTitle("Jeu haut / bas")
                .setMessage("Le but du jeu est de faire bouger la boule de bas en haut "+ AR_NUMBER +"! Vous avez 1 minute")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ctnd = new CountDownTimer(SECONDES, 1000) {

                            public void onTick(long millisUntilFinished) {
                                time.setText("Temps restant: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {

                                new AlertDialog.Builder(MjTopBottomActivity.this)
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

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            int a = 0;
            int b = 0;
            int c = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                if(c == AR_NUMBER){
                    ctnd.cancel();
                    new AlertDialog.Builder(MjTopBottomActivity.this)
                            .setTitle("Bravo vous avez gagné !")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                    startActivity(i);
                                }
                            }).setCancelable(false)
                            .show();
                }

                String progres = String.valueOf(progress);
                int prgrs = Integer.parseInt(progres);

                if(prgrs >= 0 && prgrs <= 5) {
                    a = 1;
                }

                if(prgrs >= 95 && prgrs <= 100) {
                    b = 1;
                }

                if ( Objects.equals(a, 1) && Objects.equals(b, 1) ) {
                    c = c + 1;
                    a = 0;
                    b = 0;
                }

                TextView scores = (TextView) findViewById(R.id.score);
                scores.setText(""+c);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });
    }
    public void onBackPressed() {
    }
}
