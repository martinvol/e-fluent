package com.example.user.efluent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class GiveExerciseActivity extends AppCompatActivity {

    String[] listExercises = new String[]{"papa", "jeux", "deux"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_exercise);

        final Spinner dropdown = (Spinner)findViewById(R.id.dropDownList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listExercises);
        dropdown.setAdapter(adapter);

        final Button buttonGiveExo = (Button) findViewById(R.id.buttonGiveExercise);
        buttonGiveExo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> ProActivity tab");
                String text = dropdown.getSelectedItem().toString();
                Intent intent = new Intent(v.getContext(), InfoPatientActivity.class);
                Toast toast = Toast.makeText(getApplicationContext(), "Mot choisi :" + text, Toast.LENGTH_LONG);
                toast.show();
                //ProActivity.login = login;
                startActivity(intent);


            }
        });
    }
}
