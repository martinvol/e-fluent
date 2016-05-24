package com.example.user.efluent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class TabFragment1 extends ListFragment {

    private ArrayList<Patient> patient_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_1, container, false);
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

        View addButton = getActivity().findViewById(R.id.GoAddPatient);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", "GoAddPatient");
                Intent intent = new Intent(getView().getContext(), AddPatientActivity.class);
                startActivity(intent);
            }
        });

    }



    public void setPatients (ArrayList<Patient> patient_list){
        this.patient_list = patient_list;

        System.out.println("DESDE EL Fragment");
        System.out.println(patient_list.size());

        ArrayList<String> patient_names = new ArrayList<String>();

        for(Patient patient: patient_list ){
            System.out.println("first name: " + patient.first_name);
            patient_names.add(patient.first_name);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.rowlayout, R.id.label, patient_names.toArray(new String[patient_names.size()]));
        setListAdapter(adapter);
    }

}
