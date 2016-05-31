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
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class TabFragmentInfoPatient2 extends Fragment {

    ArrayList<Resultat> resultat_list;
    private ListView listview_exercice_done;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_info_patient_2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listview_exercice_done = (ListView) getActivity().findViewById(R.id.list_exercices_done_of_my_patient);

        listview_exercice_done.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                // isValidate = "true" et on envoie dans la DB + actualisation de la liste
            }
        });
    }

    public void setResults(ArrayList<Resultat> resultat_list) {
        this.resultat_list = resultat_list;

        ArrayList<HashMap<String, String>> resultat_hashmap_done = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> resultat_item = new HashMap<String, String>();

        for (Resultat resultat : resultat_list) {
            resultat_item.put(resultat.exercice.name, resultat.pourcentage + "%");
            //enfin on ajoute cette hashMap dans la arrayList
            resultat_hashmap_done.add(resultat_item);
        }

        SimpleAdapter adapter1 = new SimpleAdapter(getActivity(), resultat_hashmap_done, R.layout.item_for_exercices_done_list,
                new String[]{"exercice1", "resultat1"}, new int[]{R.id.name_exercice_done, R.id.pourcentage});
        listview_exercice_done.setAdapter(adapter1);
    }

}