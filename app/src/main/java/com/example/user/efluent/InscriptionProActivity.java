package com.example.user.efluent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscriptionProActivity extends AppCompatActivity {

    public static MainActivity fragmentICameFrom;
    Button createPro;
    EditText lastName;
    EditText firstName;
    EditText email;
    EditText phoneNumber;
    EditText password;
    EditText numADELI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_pro);

        createPro = (Button)findViewById(R.id.RegisterPro);
        lastName = (EditText)findViewById(R.id.InscriptionProName);
        firstName = (EditText)findViewById(R.id.InscriptionProFirstName);
        email = (EditText)findViewById(R.id.InscriptionProEmail);
        password   = (EditText)findViewById(R.id.InscriptionProPassword);
        phoneNumber   = (EditText)findViewById(R.id.InscriptionProNumber);
        numADELI   = (EditText)findViewById(R.id.InscriptionProNoAdeli);

        final InscriptionProActivity self = this;

        createPro.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        /*Create an empty object Patient, and fill the attributes by getText of
                        * fields in the AddPatientActivity*/
                        Orthophonist ortho = new Orthophonist(null);
                        ortho.last_name = lastName.getText().toString();
                        //System.out.println("patient.last_name = " + patient.last_name);
                        ortho.first_name = firstName.getText().toString();
                        //System.out.println("patient.first_name = " + patient.first_name);
                        ortho.email = email.getText().toString();
                        //System.out.println("patient.email = " + patient.email);
                        ortho.phone_number = phoneNumber.getText().toString();
                        ortho.password = password.getText().toString();
                        ortho.numADELI = numADELI.getText().toString();
                        //System.out.println("patient.password = " + patient.password);
                        LoginManager.createOrthophoniste(ortho, self);

                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);

                        Toast toast = Toast.makeText(getApplicationContext(), "Inscription Orthophonist successful!", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });

    }

    /*public void addNewToList(Patient patient) {
        fragmentICameFrom.AddOrthoToList(ortho);
    }*/
}
