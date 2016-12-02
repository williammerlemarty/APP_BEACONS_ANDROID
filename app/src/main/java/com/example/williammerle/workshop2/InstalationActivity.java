package com.example.williammerle.workshop2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class InstalationActivity extends AppCompatActivity {
    private static int NUMBER_PLAYERS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalation);

        if(NUMBER_PLAYERS == 2){
            ImageView img = (ImageView) findViewById(R.id.schema);
            img.setImageResource(R.drawable.twoplayers);
        }
        new AlertDialog.Builder(InstalationActivity.this)
                .setTitle("Installation des Beacons")
                .setMessage("Mettez en place une limite imaginaire. Placez les Beacons à 10 mètres de la limite. Les beacons doivent être séparés de plus d'1m50.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}})
                .show();
        Button next = (Button) findViewById(R.id.next);
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

    }


    private void doClickOnButtonCreate2() {
        Intent i = new Intent(getApplicationContext(), WaitingActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
    public void onBackPressed() {
    }
}
