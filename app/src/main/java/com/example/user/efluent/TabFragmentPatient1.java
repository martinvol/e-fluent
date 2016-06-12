package com.example.user.efluent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

public class TabFragmentPatient1 extends ListFragment implements ExerciseReceiver{

    LoginManager login;

    ArrayList<Exercise> exerciseList;

    public void setExercises(ArrayList<Exercise> exerciseList){
        this.exerciseList = exerciseList;
        ArrayList<String> patient_names = new ArrayList<String>();

        for(Exercise exercise: exerciseList ){
            // System.out.println("first name: " + exercise.time.toString());
            if (!exercise.done){
                patient_names.add(exercise.word);
                //FIXME this should be on the adapter
            }
        }


        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.rowlayout, R.id.label, patient_names.toArray(new String[patient_names.size()]));*/
        CustomExerciseListAdapter adapter  = new CustomExerciseListAdapter(getActivity(),
                exerciseList.toArray(new Exercise[exerciseList.size()]));
        //patient_list.toArray(new Patient[patient_list.size()])

        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.i("test", "Click from the list");
                final Exercise item = (Exercise) parent.getItemAtPosition(position);
                Log.i("test", "Word of exercises is: " + item);
                Intent intent = new Intent(getView().getContext(), ExerciseVocal.class);
                ExerciseVocal.word = item.mot;
                ExerciseVocal.login = login;

                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_patient_1, container, false);
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

        /*View goExo = getActivity().findViewById(R.id.GoExoVocal);

        goExo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", "GoExoVocal");
                Intent intent = new Intent(getView().getContext(), ExerciseVocal.class);
                startActivity(intent);
            }
        });*/

    }
}