package com.example.user.efluent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class InscriptionProActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_pro);

        final Button button = (Button) findViewById(R.id.RegisterPro);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> Pro tab");
                Toast.makeText(InscriptionProActivity.this, "Inscription Effectuée!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                //ProActivity.login = login;
                startActivity(intent);


            }
        });

    }
}
