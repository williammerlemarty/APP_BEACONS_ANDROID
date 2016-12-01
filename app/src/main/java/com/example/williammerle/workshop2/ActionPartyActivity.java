package com.example.williammerle.workshop2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActionPartyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_party);

        Button cre = (Button) findViewById(R.id.create);
        cre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButtonCreer();
                    }
                });
            }
        });

        Button re = (Button) findViewById(R.id.join);
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButtonRejoindre();
                    }
                });
            }
        });
    }

    private void doClickOnButtonCreer() {
        Intent i = new Intent(getApplicationContext(), CreerPartyActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }

    private void doClickOnButtonRejoindre() {
        Intent i = new Intent(getApplicationContext(), RejoindrePartyActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
        startActivity(i);
    }
}
