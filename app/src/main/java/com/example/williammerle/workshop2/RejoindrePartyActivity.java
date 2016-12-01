package com.example.williammerle.workshop2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class RejoindrePartyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejoindre_party);

        Button next = (Button) findViewById(R.id.join);
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
        EditText name = (EditText) findViewById(R.id.name);
        EditText password = (EditText) findViewById(R.id.password);

        String nm = name.getText().toString();
        String psw = password.getText().toString();

        if(!Objects.equals(nm, "") && !Objects.equals(psw, "")){

            //EVRIFY PARTY EN BDD

            Intent i = new Intent(getApplicationContext(), WaitingActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
            startActivity(i);

        }else {
            if(Objects.equals(nm, "")){
                TextView errorname = (TextView) findViewById(R.id.errorname);
                errorname.setText("Le nom est obligatoire");
            }

            if(Objects.equals(psw, "")){
                TextView errorpassword = (TextView) findViewById(R.id.errorpassword);
                errorpassword.setText("Le mot de passe est obligatoire");
            }

        }
    }
}
