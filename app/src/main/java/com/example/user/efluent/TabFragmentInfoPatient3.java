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

public class TabFragmentInfoPatient3 extends ListFragment implements ExerciseReceiver{

    //private ArrayList<Patient> patient_list;
    //ArrayList<Exercise> exerciseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_info_patient_3, container, false);
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
            if (exercise.done){
                exerciseListFiltered.add(exercise);
            }
        }


        CustomExerciseListAdapter adapter  = new CustomExerciseListAdapter(getActivity(),
                exerciseListFiltered.toArray(new Exercise[exerciseListFiltered.size()]));
        setListAdapter(adapter);
    }
}
