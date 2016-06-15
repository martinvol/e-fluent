package com.example.user.efluent;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LoginManager login;
    ProgressDialog loginDialog;
    private ArrayList<Patient> patient_list;

    public EditText idField;
    public EditText passwordField;

    final void login(String username, String password){
        loginDialog.show();
        Log.i("test", "Login");
        login.login(
                username.toLowerCase(),
                password
        );
    }

    final void login(){
        login(((EditText) findViewById(R.id.LoginMain)).getText().toString(),
                ((EditText)findViewById(R.id.PasswordMain)).getText().toString()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = new LoginManager(this);

        loginDialog = new ProgressDialog(this);
        loginDialog.setTitle("Logging in");
        loginDialog.setMessage("Wait while loading...");


        idField = ((EditText) findViewById(R.id.LoginMain));
        idField .setText("ortho1");
        passwordField = ((EditText) findViewById(R.id.PasswordMain));
        passwordField .setText("123456");

        ((Button) findViewById(R.id.connexionMain))
                .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
                //loginDialog.show(v.getContext(), "Logging in", "Wait while loading...");
/*                loginDialog.show();
                Log.i("test", "Login");
                login.login(
                        ((EditText) findViewById(R.id.LoginMain)).getText().toString(),
                        ((EditText)findViewById(R.id.PasswordMain)).getText().toString()
                );*/


            }
        });

        final MainActivity self = this;

        final Button buttonInscription = (Button) findViewById(R.id.inscriptionMain);
        buttonInscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> ProActivity tab");
                Intent intent = new Intent(v.getContext(), InscriptionProActivity.class);
                InscriptionProActivity.login = login;
                InscriptionProActivity.oldActivity = self;
                startActivity(intent);


            }
        });

        final Button button = (Button) findViewById(R.id.GoProActivity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> ProActivity tab");
                Intent intent = new Intent(v.getContext(), Sonometre.class);
                ProActivity.login = login;
                startActivity(intent);


            }
        });

        /*final Button button2 = (Button) findViewById(R.id.GoPatientActivity);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> Patient tab");
                Intent intent = new Intent(v.getContext(), PatientActivity.class);
                //PatientActivity.login = login;
                startActivity(intent);


            }
        });*/



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



    public void loginSucessOrtho() {
        loginSucess();
        Log.i("test", "Logged was an Ortho");
        Intent intent = new Intent(getApplicationContext(), ProActivity.class);
        ProActivity.login = login;
        startActivity(intent);

        // the following line is here only for testing
        //login.sendExercise(this, "");
    }

    private void loginSucess() {
        loginDialog.dismiss();
        Toast toast = Toast.makeText(getApplicationContext(), "Login successful, Welcome!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void loginSucessPatient() {
        loginSucess();
        Log.i("test", "Logged was a patient");
        Intent intent = new Intent(getApplicationContext(), PatientActivity.class);
        PatientActivity.login = login;
        startActivity(intent);
    }

    public void giveSuccess() {

    }
}


