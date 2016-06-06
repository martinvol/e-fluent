package com.example.user.efluent;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPatientActivity extends AppCompatActivity {

    public static LoginManager login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        // Callbacks
        final Button button = (Button) findViewById(R.id.CreatePatient);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                String last_name = ((EditText)findViewById(R.id.AddPatientName)).toString();
                String name = ((EditText)findViewById(R.id.AddPatientFirstName)).toString();
                String password = ((EditText)findViewById(R.id.AddPatientPassword)).toString();

                if (last_name .equals("") || name.equals("") || password.equals("")){
                    /*new AlertDialog.Builder(context)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })*/
                }

                //login.addPatient();
                Log.i("test", "from the button");
            }
        });

    }
}
