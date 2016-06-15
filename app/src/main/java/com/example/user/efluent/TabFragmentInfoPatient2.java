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

import java.util.ArrayList;

public class TabFragmentInfoPatient2 extends ListFragment implements ExerciseReceiver {

    //private ArrayList<Patient> patient_list;
    //ArrayList<Exercise> exerciseList;
    public Patient patient;
    public LoginManager login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_info_patient_2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View addButton = getActivity().findViewById(R.id.GoAddPatient);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", "GoGiveExercise");
                Intent intent = new Intent(getView().getContext(), GiveExerciseActivity.class);
                GiveExerciseActivity.patient_to_add = patient;
                GiveExerciseActivity.login = login;
                startActivity(intent);
            }
        });
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };*/
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.rowlayout, R.id.label,values);
        setListAdapter(adapter);*/

        /*View addButton = getActivity().findViewById(R.id.GoAddPatient);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", "GoAddPatient");
                Intent intent = new Intent(getView().getContext(), AddPatientActivity.class);
                startActivity(intent);
            }
        });*/

    }

    public void setExercises(ArrayList<Exercise> exerciseList){
        //this.exerciseList = exerciseList;
        ArrayList<Exercise> exerciseListFiltered = new ArrayList<Exercise>();

        for(Exercise exercise: exerciseList ){
            if (!exercise.done){
                exerciseListFiltered.add(exercise);
            }
        }


        CustomExerciseListAdapter adapter  = new CustomExerciseListAdapter(getActivity(),
                exerciseListFiltered.toArray(new Exercise[exerciseListFiltered.size()]));
        setListAdapter(adapter);

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.rowlayout, R.id.label, patient_names.toArray(new String[patient_names.size()]));
        setListAdapter(adapter);*/

        /*getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.i("test", "Click from the list");
                final String item = (String) parent.getItemAtPosition(position);
                Log.i("test", "Word of exercises is: " + item);
            }
        });*/
    }




    /*public void setPatients (ArrayList<Patient> patient_list){
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
    } */

}
