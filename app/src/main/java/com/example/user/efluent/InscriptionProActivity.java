package com.example.user.efluent;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscriptionProActivity extends AppCompatActivity {

    Button createPro;
    EditText lastName;
    EditText firstName;
    EditText email;
    EditText phoneNumber;
    EditText password;
    EditText numADELI;

    static public MainActivity oldActivity;

    ProgressDialog dialog;

    static public LoginManager login;

    Orthophonist ortho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_pro);

        createPro = (Button)findViewById(R.id.RegisterPro);
        lastName = (EditText)findViewById(R.id.InscriptionProName);
        firstName = (EditText)findViewById(R.id.InscriptionProFirstName);
        email = (EditText)findViewById(R.id.InscriptionProEmail);
        password = (EditText)findViewById(R.id.InscriptionProPassword);
        phoneNumber   = (EditText)findViewById(R.id.InscriptionProNumber);
        numADELI   = (EditText)findViewById(R.id.InscriptionProNoAdeli);

        lastName.setText("Volpe");
        firstName.setText("Matias");
        email.setText("mymail@hotmail.com");
        password.setText("123456");

        final InscriptionProActivity self = this;

        createPro.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        /*Create an empty object Patient, and fill the attributes by getText of
                        * fields in the AddPatientActivity*/
                        ortho = new Orthophonist(null);
                        ortho.last_name = lastName.getText().toString();
                        //System.out.println("patient.last_name = " + patient.last_name);
                        ortho.first_name = firstName.getText().toString();
                        //System.out.println("patient.first_name = " + patient.first_name);
                        ortho.email = email.getText().toString();
                        //System.out.println("patient.email = " + patient.email);
                        ortho.phone_number = phoneNumber.getText().toString();
                        ortho.password = password.getText().toString();
                        ortho.numADELI = numADELI.getText().toString();

                        ortho.username = (ortho.first_name + ortho.last_name).toLowerCase();
                        //System.out.println("patient.password = " + patient.password);


                        if (!ortho.isValid()){
                            final AlertDialog show = new AlertDialog.Builder(InscriptionProActivity.this)
                                    .setTitle("Missing information")
                                    .setMessage("Last name, First Name, E-mail and Password must be filled")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            return;
                        }
                        if(ortho.password.length() < 6) {

                            final AlertDialog show = new AlertDialog.Builder(InscriptionProActivity.this)
                                    .setTitle("Mot de passe pas assez long")
                                    .setMessage("Veuillez rentrer un mot de passe de plus de 6 caractères")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            return;
                        }
                        login.createOrthophoniste(ortho, self);

                        /*Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);*/

                        dialog = new ProgressDialog(self);
                        dialog.setTitle("Loging in");
                        dialog.setMessage("Wait while loading...");
                        dialog.show();
                    }
                });

    }

    public void singUpSuccess() {
        //oldActivity.
        dialog.dismiss();
        Toast toast = Toast.makeText(getApplicationContext(), "Inscription Orthophonist successful!", Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        oldActivity.login(ortho.username, ortho.password);

    }

    /*public void addNewToList(Patient patient) {
        fragmentICameFrom.AddOrthoToList(ortho);
    }*/
}
