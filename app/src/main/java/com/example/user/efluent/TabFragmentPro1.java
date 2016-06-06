package com.example.user.efluent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class TabFragmentPro1 extends ListFragment {

    /*Arraylist of patients are retrieved from LoginManager*/
    private ArrayList<Patient> patient_list;
    LoginManager login;

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
                AddPatientActivity.login = login;
                startActivity(intent);
            }
        });

        /*View addButtonInfo = getActivity().findViewById(R.id.GoPatientInfo);

        addButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", "GoInfoPatient");
                Intent intent = new Intent(getView().getContext(), InfoPatientActivity.class);
                startActivity(intent);
            }
        }); */

        /*final Button buttonPatientInfo = (Button) getView().findViewById(R.id.inscriptionMain);
        buttonPatientInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> Info Patient");
                Intent intent = new Intent(v.getContext(), InfoPatientActivity.class);
                //ProActivity.login = login;
                startActivity(intent);


            }
        });*/

    }



    public void setPatients (final ArrayList<Patient> patient_list){
        this.patient_list = patient_list;

        System.out.println("DESDE EL Fragment");
        System.out.println(patient_list.size());

        CustomPatientListAdapter adapter= new CustomPatientListAdapter(getActivity(),
                patient_list.toArray(new Patient[patient_list.size()])
        );


        setListAdapter(adapter);

        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.i("test", "Click from the list");

                Patient item = patient_list.get(position);

                Log.i("test","from the click: " + item.first_name);

                Log.i("Test", "GoInfoPatient");
                Intent intent = new Intent(getView().getContext(), InfoPatientActivity.class);
                InfoPatientActivity.patient = item;
                startActivity(intent);
            }
        });
    }

}
