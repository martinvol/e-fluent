package com.example.user.efluent;

/**
 * Created by User on 08/06/2016.
 */
import android.util.Log;

public class AppLog {
    private static final String APP_TAG = "AudioRecorder";

    public static int logString(String message) {
        return Log.i(APP_TAG, message);
    }
}
