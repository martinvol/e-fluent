package com.example.user.efluent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AddPatientActivity extends AppCompatActivity {

    public static LoginManager login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        // Callbacks
        final Button button = (Button) findViewById(R.id.CreatePatient);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                //login.addPatient();
                Log.i("test", "from the button");
            }
        });

    }
}
