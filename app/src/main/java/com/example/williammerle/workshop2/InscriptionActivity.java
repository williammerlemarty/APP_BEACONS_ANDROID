package com.example.williammerle.workshop2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button ins = (Button) findViewById(R.id.inscription);
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            doClickOnButtonInscription();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    private void doClickOnButtonInscription() throws MalformedURLException {
        EditText pseudo = (EditText) findViewById(R.id.pseudo);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        final String ps = pseudo.getText().toString();
        final String mail = email.getText().toString();
        final String pwd = password.getText().toString();

        if(!Objects.equals(ps, "") && !Objects.equals(mail, "") && !Objects.equals(pwd, "")){

            // TODO :: TOP
            RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
            String url = "http://li625-134.members.linode.com/auth";
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response",""+response);
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    //This code is executed if there is an error.
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("username", ps); //Add the data you'd like to send to the server.
                    MyData.put("email", mail); //Add the data you'd like to send to the server.
                    MyData.put("password", pwd); //Add the data you'd like to send to the server.
                    return MyData;
                }
            };
            MyRequestQueue.add(MyStringRequest);
            //TODO :: BOTTOM

            Intent i = new Intent(getApplicationContext(), ActionPartyActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
            startActivity(i);
        }else{
            if(Objects.equals(ps, "")){
                TextView errorpseudo = (TextView) findViewById(R.id.errorpseudo);
                errorpseudo.setText("Le pseudo est obligatoire");
            }
            if(Objects.equals(mail, "")){
                TextView erroremail = (TextView) findViewById(R.id.erroremail);
                erroremail.setText("L'email est obligatoire");
            }
            if(Objects.equals(pwd, "")){
                TextView errorpassword = (TextView) findViewById(R.id.errorpassword);
                errorpassword.setText("Le mot de passe est obligatoire");
            }
        }
    }
    private String readFile(File f) throws IOException {

        FileInputStream is = new FileInputStream(f);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();

        return s;
    }

}
