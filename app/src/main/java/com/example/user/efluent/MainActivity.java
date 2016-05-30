package com.example.user.efluent;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LoginManager login;
    ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        login = new LoginManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginDialog = new ProgressDialog(this);
        loginDialog.setTitle("Loging in");
        loginDialog.setMessage("Wait while loading...");


        ((EditText) findViewById(R.id.LoginMain)).setText("ortho1");
        ((EditText) findViewById(R.id.PasswordMain)).setText("123456");

        ((Button) findViewById(R.id.connexionMain))
                .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //loginDialog.show(v.getContext(), "Logging in", "Wait while loading...");
                loginDialog.show();
                Log.i("test", "Login");
                login.login(
                        ((EditText) findViewById(R.id.LoginMain)).getText().toString(),
                        ((EditText)findViewById(R.id.PasswordMain)).getText().toString()
                );


            }
        });

        final Button buttonInscription = (Button) findViewById(R.id.inscriptionMain);
        buttonInscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> Pro tab");
                Intent intent = new Intent(v.getContext(), InscriptionProActivity.class);
                ProActivity.login = login;
                startActivity(intent);


            }
        });

        final Button button = (Button) findViewById(R.id.GoProActivity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> Pro tab");
                Intent intent = new Intent(v.getContext(), ProActivity.class);
                ProActivity.login = login;
                startActivity(intent);


            }
        });

        final Button button2 = (Button) findViewById(R.id.GoPatientActivity);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> Patient tab");
                Intent intent = new Intent(v.getContext(), PatientActivity.class);
                //PatientActivity.login = login;
                startActivity(intent);


            }
        });



        /*EditText text = new EditText(this);
        //Intent intent1 = new Intent(this, )
        text.setText("Bonjour, c'est bon rentrez chez vous ");
        setContentView(text);*/
    }

    public void showErrorLogin() {
        loginDialog.dismiss();
        new AlertDialog.Builder(this)
                .setTitle("Bad Login")
                .setMessage("Bad username or Password")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                }).show();
    }

    public void loginSucess() {
        loginDialog.dismiss();
        Toast toast = Toast.makeText(getApplicationContext(), "Login successful, Welcome!", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(getApplicationContext(), ProActivity.class);
        ProActivity.login = login;
        startActivity(intent);

    }
}


