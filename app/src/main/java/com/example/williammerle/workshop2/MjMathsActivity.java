package com.example.williammerle.workshop2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class MjMathsActivity extends AppCompatActivity {
    public int op3;
    public static final int SECONDES = 60000;//+000
    private CountDownTimer ctnd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mj_maths);
        final TextView time = (TextView) findViewById(R.id.time);

        new AlertDialog.Builder(MjMathsActivity.this)
            .setTitle("Jeu opération")
            .setMessage("Le but du jeu est de trouver la réponse à l'opération ! Si la réponse est négative, le signe est moins n'est pas obligatoire! Vous avez 1 minute")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ctnd = new CountDownTimer(SECONDES, 1000) {

                            public void onTick(long millisUntilFinished) {
                                time.setText("Temps restant: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {

                                new AlertDialog.Builder(MjMathsActivity.this)
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

                String[] operators = {"+","-","*","/"};

                Random r1 = new Random();
                int o1 = r1.nextInt(4 - 0);
                int o2 = r1.nextInt(4 - 0);
                int o3 = r1.nextInt(4 - 0);

                int n1 = r1.nextInt(100 - 1);
                int n2 = r1.nextInt(100 - 1);
                int n3 = r1.nextInt(100 - 1);
                int n4 = r1.nextInt(100 - 1);

                if(operators[o1] == "/"){
                    if(n1 % n2 != 0 ){
                        operators[o1] = "*";
                    }
                }

                if(operators[o2] == "/"){
                    operators[o2] = "*";
                }

                if(operators[o3] == "/"){
                    if(n2 % n3 != 0 ){
                        operators[o3] = "*";
                    }
                }


                TextView question = (TextView) findViewById(R.id.question);

                String affichage = "(" + n1 + operators[o1] + n2 + ")" + operators[o2] + "(" + n3 + operators[o3] + n4 + ")";
                int op1 = calculate(operators[o1], n1,n2);
                int op2 = calculate(operators[o3], n3,n4);
                op3 = calculate(operators[o2], op1,op2);

                if(op3 < 0){
                    op3 = op3+(-op3*2);
                }

                question.setText(affichage);
                }
            }).setCancelable(false)
                .show();

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                EditText answer = (EditText) findViewById(R.id.answer);
                String answerString = answer.getText().toString();
                    if(!Objects.equals(answerString, "")) {

                        int answerInt = Integer.parseInt(answerString);
                        if (answerInt == op3) {
                            ctnd.cancel();
                            new AlertDialog.Builder(MjMathsActivity.this)
                                    .setTitle("Bravo vous avez gagné !")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                            startActivity(i);
                                        }
                                    }).setCancelable(false)
                                    .show();
                        } else {
                            new AlertDialog.Builder(MjMathsActivity.this)
                                    .setTitle("Perdu ! Vous perdez une vie ")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                            startActivity(i);
                                        }
                                    }).setCancelable(false)
                                    .show();
                        }
                    }else{
                        TextView errormaths = (TextView) findViewById(R.id.errormaths);
                        errormaths.setText("Veuillez au moins essayer !");
                    }

                }
            });
            }
        });
    }

    public int calculate(String operators, int val1,int val2) {

        int operation = 0;

        if(operators == "+"){
            operation = val1 + val2;
        }
        else if (operators == "-"){
            operation = val1 - val2;
        }
        else if (operators == "*"){
            operation = val1 * val2;
        }
        else if (operators == "+"){
            operation = val1 / val2;
        }

        return operation;
    }
    public void onBackPressed() {
    }
}
