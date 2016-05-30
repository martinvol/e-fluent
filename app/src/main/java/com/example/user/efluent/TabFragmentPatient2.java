package com.example.user.efluent;

/**
 * Created by User on 24/05/2016.
 */
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

public class TabFragmentPatient2 extends ListFragment implements MeetingReceiver {

    private ArrayList<Meeting> meetingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_patient_2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };*/
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.rowlayout, R.id.label,values);
        setListAdapter(adapter);*/

    }

    public void setMeetings(ArrayList<Meeting> meetingList){
        this.meetingList = meetingList;

        System.out.println("DESDE EL Fragment");
        System.out.println(meetingList.size());

        ArrayList<String> patient_names = new ArrayList<String>();

        for(Meeting meeting: meetingList ){
            System.out.println("first name: " + meeting.time.toString());
            patient_names.add(meeting.time.toString());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.rowlayout, R.id.label, patient_names.toArray(new String[patient_names.size()]));
        setListAdapter(adapter);

        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Intent intent = new Intent(MainActivity.this, SendMessage.class);
                //String message = "abc";
                //intent.putExtra(EXTRA_MESSAGE, message);
                //startActivity(intent);
                Log.i("test", "Click from the list");
                final String item = (String) parent.getItemAtPosition(position);
                Log.i("test", "Name of patient is: " + item);
            }
        });
    }

}

