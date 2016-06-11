package com.example.user.efluent;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Bundle;
import android.os.Environment;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class ExerciseVocal extends AppCompatActivity {

    public static String word = null;
    Button play,stop;
    ImageButton record;
    //private MediaRecorder myAudioRecorder;
    private String outputFile = null;

    TextView exerciseWord;

    public static LoginManager login;

    private WavAudioRecorder mRecorder;


    /*Button micCall;
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };
    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_vocal);

        exerciseWord = (TextView )findViewById(R.id.WordToPronounce);
        exerciseWord.setText(word);

        play = (Button)findViewById(R.id.playButton);
        stop = (Button)findViewById(R.id.stopButton);
        record = (ImageButton)findViewById(R.id.CallMic);

        stop.setEnabled(false);
        play.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        System.out.println("Outputfile location : " + outputFile);

        /*myAudioRecorder = new MediaRecorder();

        myAudioRecorder.setAudioSamplingRate(441000);
        // myAudioRecorder.setAudioEncodingBitRate(16);
        myAudioRecorder.setAudioEncodingBitRate(96000);

        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        myAudioRecorder.setOutputFile(outputFile);*/

        mRecorder = WavAudioRecorder.getInstanse();
        mRecorder.setOutputFile(outputFile);



        final ExerciseVocal self = this;

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    /*myAudioRecorder.prepare();
                    myAudioRecorder.start();*/
                    mRecorder.prepare();
                    mRecorder.start();
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                /*catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAudioRecorder.stop();
                mRecorder.stop();
                mRecorder.reset();
                login.sendExercise(self, outputFile);
                //myAudioRecorder.release();

                //myAudioRecorder  = null;

                stop.setEnabled(false);
                play.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });


        /*micCall=(Button)findViewById(R.id.button1);
        micCall.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        AppLog.logString("Start Recording");
                        startRecording();
                        break;
                    case MotionEvent.ACTION_UP:
                        AppLog.logString("stop Recording");
                        stopRecording();
                        break;
                }
                return false;
            }
        }); */
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if(!file.exists()){
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }

    private void startRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            AppLog.logString("Error: " + what + ", " + extra);
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            AppLog.logString("Warning: " + what + ", " + extra);
        }
    };

    private void stopRecording(){
        if(null != recorder){
            recorder.stop();
            recorder.reset();
            recorder.release();

            recorder = null;
        }
    } */
}
