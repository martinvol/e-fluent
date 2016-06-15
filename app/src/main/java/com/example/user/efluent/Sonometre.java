package com.example.user.efluent;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Sonometre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonometre);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Sonometre self = this;
        final TextView label = (TextView) findViewById(R.id.sonometre_out);
        final TextView comment = (TextView) findViewById(R.id.comment_sonometre);
        final ImageButton button = (ImageButton) findViewById(R.id.micro_sonometre);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> ProActivity tab");
                start();

                class GetAudio extends TimerTask {

                    public void run() {

                        self.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                label.setText(String.valueOf(getAmplitude()));
                                if (getAmplitude() < 1000) {
                                    comment.setTextColor(Color.RED);
                                    comment.setText("Il faut parler plus fort");
                                }
                                else if(getAmplitude() > 10000) {
                                    comment.setTextColor(Color.RED);
                                    comment.setText("Il faut parler moins fort");
                                }
                                else {
                                    comment.setTextColor(Color.GREEN);
                                    comment.setText("Bonne puissance de voix !");
                                }
                            }
                        });
                    }
                }

                Timer timer = new Timer();
                final int FPS = 5;
                GetAudio audioTaskt = new GetAudio();
                timer.scheduleAtFixedRate(audioTaskt, 0, 1000/FPS);
            }
        });





    }

    private AudioRecord ar = null;
    private int minSize;

    public void start() {
        minSize= AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        ar = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,minSize);
        ar.startRecording();
    }

    public void stop() {
        if (ar != null) {
            ar.stop();
        }
    }

    public double getAmplitude() {
        short[] buffer = new short[minSize];
        ar.read(buffer, 0, minSize);
        int max = 0;
        for (short s : buffer)
        {
            if (Math.abs(s) > max)
            {
                max = Math.abs(s);
            }
        }
        return max;
    }


}

