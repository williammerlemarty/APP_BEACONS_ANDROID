package com.example.williammerle.workshop2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class ConnexionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

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
    }

    private void doClickOnButtonConnexion() {
        EditText pseudo = (EditText) findViewById(R.id.pseudo);
        EditText password = (EditText) findViewById(R.id.password);

        final String ps = pseudo.getText().toString();
        final String pwd = password.getText().toString();
        if(Objects.equals(ps, "") ||  Objects.equals(pwd, "")) {
            if(Objects.equals(ps, "")){
                TextView errorpseudo = (TextView) findViewById(R.id.errorpseudo);
                errorpseudo.setText("Le pseudo est obligatoire");
            }
            if(Objects.equals(pwd, "")){
                TextView errorpassword = (TextView) findViewById(R.id.errorpassword);
                errorpassword.setText("Le mot de passe est obligatoire");
            }
        }else {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    URL u = null;
                    String parameters = "username=" + ps + "&password=" + pwd;
                    try {
                        u = new URL("http://li625-134.members.linode.com/auth?" + parameters);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    URLConnection c = null;
                    try {
                        c = u.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    InputStream cis = null;

                    try {
                        cis = c.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (cis != null) {
                        JSONObject jsonresponse = null;
                        boolean ok = false;
                        JSONObject user = null;
                        String username = null;
                        String token = null;
                        try {
                            try {
                                jsonresponse = this.readUrl(u);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            assert jsonresponse != null;
                            ok = (boolean) jsonresponse.get("ok");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ok) {
                            try {
                                user = (JSONObject) jsonresponse.get("user");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                assert user != null;
                                username = (String) user.get("username");

                                File internal = getFilesDir();
                                File fu = new File(internal, "username");
                                if (fu.exists() && fu.isFile()) {
                                    fu.delete();
                                }
                                try {
                                    fu.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    this.writeFile(fu, username);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String fe = readFile(fu);
                                    Log.e("FE", "" + fe);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                token = (String) user.get("token");

                                File internal = getFilesDir();
                                File ft = new File(internal, "token");
                                if (ft.exists() && ft.isFile()) {
                                    ft.delete();
                                }
                                try {
                                    ft.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    this.writeFile(ft, token);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    String ff = readFile(ft);
                                    Log.e("FT", "" + ff);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Intent i = new Intent(getApplicationContext(), ActionPartyActivity.class); // param 1 = contexte (null,getApplicationContext,this) - param 2 = quelle activity ?
                            startActivity(i);
                            Log.e("CONNECTÉ", "CONNECTÉ");

                        } else {
                            Log.e("REPONSE", "DONNES INCORECTES");
                        }

                    } else {
                        Log.e("REPONSE", "DONNES INCORECTES");

                    }
                }

                private void writeFile(File f, String s) throws IOException {
                    try {
                        FileOutputStream isw = null; // je place ma tete de lecture, je vais lire les octets
                        isw = new FileOutputStream(f);
                        OutputStreamWriter isrw = new OutputStreamWriter(isw); // va permettre au Buffer de lire (il traduit)
                        BufferedWriter brw = new BufferedWriter(isrw); // Je vais le lire

                        try {
                            brw.write(s);// je vais lire telle ligne traduit des octets en chaine de caractères
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            brw.close();// je fermer le fichier
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }

                private JSONObject readUrl(URL u) throws IOException, JSONException {
                    URLConnection c = u.openConnection();
                    InputStream cis = c.getInputStream();
                    InputStreamReader cisr = new InputStreamReader(cis);
                    BufferedReader cbr = new BufferedReader(cisr);
                    String sc = cbr.readLine();
                    JSONObject reader = new JSONObject(sc);
                    return reader;
                }

                private String readFile(File f) throws IOException {

                    FileInputStream is = new FileInputStream(f);
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String s = br.readLine();

                    return s;
                }
            });
            t.start();
        }
    }
}
