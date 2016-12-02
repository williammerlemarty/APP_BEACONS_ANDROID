package com.example.williammerle.workshop2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.appaholics.circularseekbar.CircularSeekBar;// src = https://github.com/RaghavSood/AndroidCircularSeekBar/blob/master/USAGE.md
import java.util.Objects;

public class MjWhipActivity extends AppCompatActivity {
    public static final int CIRCLE_NUMBER = 30;
    public static final int SECONDES = 60000;//+000
    private CountDownTimer ctnd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mj_whip);
        final TextView time = (TextView) findViewById(R.id.time);

        new AlertDialog.Builder(MjWhipActivity.this)
                .setTitle("Jeu Whip")
                .setMessage("Le but du jeu est de faire "+ CIRCLE_NUMBER +" cercles en partant du haut! Vous avez 1 minute")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ctnd = new CountDownTimer(SECONDES, 1000) {

                            public void onTick(long millisUntilFinished) {
                                time.setText("Temps restant: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {

                                new AlertDialog.Builder(MjWhipActivity.this)
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


        CircularSeekBar seekbar = (CircularSeekBar) findViewById(R.id.circularSeekBar);
        seekbar.setBackGroundColor(0xFFFFFFFF);
        seekbar.setProgressColor(0xFFFFFFFF);
        seekbar.hideSeekBar();

        seekbar.setSeekBarChangeListener(new CircularSeekBar.OnSeekChangeListener() {

            int a = 0;
            int b = 0;
            int c = 0;
            int d = 0;
            int e = 0;

            @Override
            public void onProgressChange(CircularSeekBar view, int newProgress) {

                if(e == CIRCLE_NUMBER){
                    ctnd.cancel();
                    new AlertDialog.Builder(MjWhipActivity.this)
                            .setTitle("Bravo vous avez gagné !")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                    startActivity(i);
                                }
                            }).setCancelable(false)
                            .show();
                }

                int prgrs = view.getProgress();;
                Log.e("prgrs",""+prgrs);

                if(prgrs > 0  &&  prgrs <= 25) {
                    a = 1;
                }

                if(prgrs > 25  &&  prgrs <= 50) {
                    b = 1;
                }

                if(prgrs > 50  &&  prgrs <= 75) {
                    c = 1;
                }

                if(prgrs > 75  &&  prgrs <= 100) {
                    d = 1;
                }

                if ( Objects.equals(a, 1) && Objects.equals(b, 1) && Objects.equals(c, 1) && Objects.equals(d, 1) ) {
                    e = e + 1;
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                }

                TextView scores = (TextView) findViewById(R.id.score);
                scores.setText(""+e);

            }
        });
    }
    public void onBackPressed() {
    }
}
