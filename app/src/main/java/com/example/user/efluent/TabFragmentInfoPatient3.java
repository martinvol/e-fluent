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
import android.widget.ListView;

import java.util.ArrayList;

public class TabFragmentInfoPatient3 extends Fragment {

    ArrayList<Resultat> resultat_list;
    private ListView listview_exercice_non_fait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_info_patient_3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listview_exercice_non_fait = (ListView) getActivity().findViewById(R.id.list_exercices_done);

        listview_exercice_non_fait.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

            }
        });

        View addButton = getActivity().findViewById(R.id.GoAddExerciceAFaire);
        listview_exercice_non_fait = (ListView)getActivity().findViewById(R.id.list_exercices_todo);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getView().getContext(), AddExoForMyPatientActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setResults(ArrayList<Resultat> resultat_list) {
        this.resultat_list = resultat_list;

        ArrayList<String> exo_names = new ArrayList<String>();

        for (Resultat resultat : resultat_list) {
            exo_names.add(resultat.exercice.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.item_exercices_todo, R.id.label, exo_names.toArray(new String[exo_names.size()]));
        listview_exercice_non_fait.setAdapter(adapter);
    }