package com.example.user.efluent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class TabFragmentPro2 extends ListFragment implements MeetingReceiver{

    private ArrayList<Meeting> meetingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_2, container, false);
    }

    public void setMeetings(ArrayList<Meeting> meetingList){
        this.meetingList = meetingList;
        ArrayList<String> patient_names = new ArrayList<String>();

        for(Meeting meeting: meetingList ){
            System.out.println("first name: " + meeting.time.toString());
            patient_names.add(meeting.time.toString());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.rowlayout, R.id.label, patient_names.toArray(new String[patient_names.size()]));
        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.i("test", "Click from the list");
                final String item = (String) parent.getItemAtPosition(position);
                Log.i("test", "Name of patient is: " + item);
            }
        });
    }

}