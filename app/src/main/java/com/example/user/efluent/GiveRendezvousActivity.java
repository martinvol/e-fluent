package com.example.user.efluent;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GiveRendezvousActivity extends AppCompatActivity implements
        View.OnClickListener {

    public static LoginManager login;
    public ArrayList<Patient> patient_list;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private Spinner mySpinner;
    private SpinAdapter spinAdapter;

    public Patient patientchoisi;
    public Calendar datechoisie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rendezvous);

        GiveRendezvousActivity self = this;

        btnDatePicker=(Button)findViewById(R.id.buttonDatePicker);
        btnTimePicker=(Button)findViewById(R.id.buttonTimePicker);
        txtDate=(EditText)findViewById(R.id.DateRDV);
        txtTime=(EditText)findViewById(R.id.TimeRDV);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        login.patientList2(self);


        // Initialize the spinAdapter sending the current context
        // Send the simple_spinner_item layout
        // And finally send the Patient array (Your data)

        /* spinAdapter = new SpinnerAdapter(GiveRendezvousActivity.this,
                android.R.layout.simple_spinner_item,
                patient_list);
        mySpinner = (Spinner) findViewById(R.id.spinnerListPatient);
        mySpinner.setAdapter(spinAdapter); // Set the custom adapter to the spinner */

        // You can create an anonymous listener to handle the event when is selected an spinner item
        /*mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Patient patient = spinAdapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(GiveRendezvousActivity.this, "Name: " + patient.first_name,
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        }); */

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        datechoisie.set(mYear + 1900, mMonth, mDay, mHour, mMinute);
        //Object Date is deprecated, better use Calendar!
    }

    public void setPatients (final ArrayList<Patient> patient_list){
        this.patient_list = patient_list;

        System.out.println("INSIDE GIVE_RENDEZVOUS_ACTIVITY!!!");
        System.out.println(patient_list.size());

        // Initialize the spinAdapter sending the current context
        // Send the simple_spinner_item layout
        // And finally send the Patient array (Your data)

        spinAdapter = new SpinAdapter(GiveRendezvousActivity.this,
                R.layout.spinner_row,
                patient_list);
        mySpinner = (Spinner) findViewById(R.id.spinnerListPatient);
        mySpinner.setAdapter(spinAdapter); // Set the custom adapter to the spinner */

        // You can create an anonymous listener to handle the event when is selected an spinner item
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /** TODO Pass the selected Patient to the server here **/
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Patient patient = spinAdapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(GiveRendezvousActivity.this, "Name: " + patient.first_name,
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


    }



    }
