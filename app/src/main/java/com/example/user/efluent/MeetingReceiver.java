package com.example.user.efluent;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by martin on 30/05/16.
 */
public interface MeetingReceiver {

    public void setMeetings(ArrayList<Meeting> meetingList);

    public Activity getActivity();
}
