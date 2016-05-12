package com.example.user.efluent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.connexionMain);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Log.i("test", "mesage");
            }
        });



        /*EditText text = new EditText(this);
        //Intent intent1 = new Intent(this, )
        text.setText("Bonjour, c'est bon rentrez chez vous ");
        setContentView(text);*/
    }
}


