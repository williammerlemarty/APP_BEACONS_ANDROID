package com.example.williammerle.workshop2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import java.util.Objects;

public class CreerPartyActivity extends AppCompatActivity {
    String fe = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_party);

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

        EditText name = (EditText) findViewById(R.id.name);
        EditText number = (EditText) findViewById(R.id.number);
        EditText time = (EditText) findViewById(R.id.time);
        EditText motdepasse = (EditText) findViewById(R.id.mdp);

        final String nm = name.getText().toString();
        final String mdp = motdepasse.getText().toString();
        final String nb = number.getText().toString();
        final String tm = time.getText().toString();

        File internal = getFilesDir();
        File f = new File(internal, "token");
        try {
            fe = readFile(f);
            Log.e("FE", "" + fe);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int nbint = 0;
        if(!Objects.equals(nb, "")){
            nbint = Integer.parseInt(nb);
        }

        int tmint = 0;
        if(!Objects.equals(tm, "")){
            tmint = Integer.parseInt(tm);
        }

        if(!Objects.equals(nm, "") && !Objects.equals(nb, "") && !Objects.equals(tm, "")  && tmint > 0 && (nbint == 2 || nbint == 3)){

            // TODO :: DEBUT
            RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
            String url = "http://li625-134.members.linode.com/party";
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response",""+response);
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR",""+error);
                }
            }) {

                protected Map<String, String> getParams(){
                    Map<String,String> MyData = new HashMap<String, String>();
                    MyData.put("name", nm);
                    MyData.put("player_limit", nb);
                    MyData.put("password", mdp);
                    MyData.put("time", tm);

                    return MyData;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer "+ fe);
                    return params;
                }

            };
            MyRequestQueue.add(MyStringRequest);

            // TODO :: FIN

            Intent i = new Intent(getApplicationContext(), ConfigCreerActivity.class);
            startActivity(i);

        }else{

            if(Objects.equals(nm, "")){
                TextView errorname = (TextView) findViewById(R.id.errorname);
                errorname.setText("Le nom est obligatoire");
            }

            if(Objects.equals(nb, "")){
                TextView errornumber = (TextView) findViewById(R.id.errornumber);
                errornumber.setText("Le nombre de joueurs est obligatoire");
            }

            if(nbint < 2 || nbint > 3){
                TextView errornumber = (TextView) findViewById(R.id.errornumber);
                errornumber.setText("Le nombre de joueurs est de 2 ou de 3");
            }

            if(Objects.equals(tm, "")){
                TextView errortime = (TextView) findViewById(R.id.errortime);
                errortime.setText("Le temps de jeu est obligatoire");
            }
            if(tmint < 0){
                TextView errortime = (TextView) findViewById(R.id.errortime);
                errortime.setText("Le temps de jeu est positif");
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
