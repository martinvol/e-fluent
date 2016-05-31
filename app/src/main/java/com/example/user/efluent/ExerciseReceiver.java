package com.example.user.efluent;

import android.app.Activity;

import java.util.ArrayList;


public interface ExerciseReceiver {
    public void setExercises(ArrayList<Exercise> meetingList);

    public Activity getActivity();
}
