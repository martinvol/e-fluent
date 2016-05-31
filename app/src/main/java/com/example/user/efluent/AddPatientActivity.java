package com.example.user.efluent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPatientActivity extends AppCompatActivity {


    public static TabFragmentPro1 fragmentICameFrom;
    Button addPatient;
    EditText lastName;
    EditText firstName;
    EditText email;
    EditText phoneNumber;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        addPatient = (Button)findViewById(R.id.CreatePatient);
        lastName = (EditText)findViewById(R.id.AddPatientName);
        firstName = (EditText)findViewById(R.id.AddPatientFirstName);
        email = (EditText)findViewById(R.id.AddPatientEmail);
        password   = (EditText)findViewById(R.id.AddPatientPassword);
        phoneNumber   = (EditText)findViewById(R.id.AddPatientNumber);

        final AddPatientActivity self = this;

        addPatient.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        /*Create an empty object Patient, and fill the attributes by getText of
                        * fields in the AddPatientActivity*/
                        Patient patient = new Patient(null);
                        patient.last_name = lastName.getText().toString();
                        //System.out.println("patient.last_name = " + patient.last_name);
                        patient.first_name = firstName.getText().toString();
                        //System.out.println("patient.first_name = " + patient.first_name);
                        patient.email = email.getText().toString();
                        //System.out.println("patient.email = " + patient.email);
                        //patient.phone_number = phoneNumber.getText().toString();
                        patient.password = password.getText().toString();
                        //System.out.println("patient.password = " + patient.password);
                        LoginManager.addPatient(patient, self);

                        Intent intent = new Intent(view.getContext(), ProActivity.class);
                        startActivity(intent);

                        Toast toast = Toast.makeText(getApplicationContext(), "Add Patient successful!", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });

    }

    public void addNewToList(Patient patient) {
        fragmentICameFrom.AddPatientToList(patient);
    }
}
