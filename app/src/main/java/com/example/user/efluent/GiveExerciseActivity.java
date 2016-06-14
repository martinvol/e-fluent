package com.example.user.efluent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class GiveExerciseActivity extends AppCompatActivity {

    String[] themeExo = new String[]{"choisir le thème..","Reeducation Orale", "Reeducation Ecrite", "Rythme"};
    String[] typeExo = new String[]{"choisir le type..","Reconnaissance Vocale", "Puissance Voix"};
    String[] listExercisesVocal = new String[]{"choisir l'exercise..",
            "baba",
            "dada",
            "deux",
            "jeux",
            "noeud",
            "papa",
            "tata",
            "voeux"};
    String[] listExercisesPuissance = new String[]{"","Sonometre"};
    String[] emptyList = new String[]{};

    //Strings to be sent to the server
    String theme = null;
    String type = null;
    String nameExo = null;

    public static Patient patient_to_add;
    public static LoginManager login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_exercise);

        final Button buttonGiveExo = (Button) findViewById(R.id.buttonGiveExercise);
        //buttonGiveExo.setEnabled(false);
        //buttonGiveExo.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        final Spinner dropdown1 = (Spinner)findViewById(R.id.dropDownList1);
        final Spinner dropdown2 = (Spinner)findViewById(R.id.dropDownList2);
        //Hide dropdown2
        dropdown2.setVisibility(View.GONE);
        final Spinner dropdown3 = (Spinner)findViewById(R.id.dropDownList3);
        //Hide dropdown3
        dropdown3.setVisibility(View.GONE);

        /**list of adapters**/
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, themeExo);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeExo);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emptyList);
        final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listExercisesVocal);
        final ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listExercisesPuissance);


        dropdown1.setAdapter(adapter1);
        dropdown1.setSelection(0, false);

        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1){
                    theme = dropdown1.getSelectedItem().toString();
                    dropdown2.setAdapter(adapter2);
                    dropdown2.setSelection(0, false);
                    //Show dropdown2
                    dropdown2.setVisibility(View.VISIBLE);

                    dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            if (position == 1){
                                type = dropdown2.getSelectedItem().toString();
                                dropdown3.setAdapter(adapter4);
                                dropdown3.setSelection(0, false);
                                //Show dropdown2
                                dropdown3.setVisibility(View.VISIBLE);

                                dropdown3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                        nameExo = dropdown3.getSelectedItem().toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {
                                        // your code here
                                    }
                                });

                            }
                            else {
                                theme = dropdown2.getSelectedItem().toString();
                                dropdown3.setAdapter(adapter5);
                                dropdown3.setSelection(0, false);
                                //Show dropdown2
                                dropdown3.setVisibility(View.VISIBLE);
                                dropdown3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                        nameExo = dropdown3.getSelectedItem().toString();
                                        //buttonGiveExo.getBackground().setColorFilter(null);
                                        //buttonGiveExo.setEnabled(true);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {
                                        // your code here
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }
                    });
                }
                else {
                    dropdown2.setAdapter(adapter3);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        final GiveExerciseActivity self = this;

        buttonGiveExo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("test", "-> ProActivity tab");
                //String text = dropdown.getSelectedItem().toString();
                Intent intent = new Intent(v.getContext(), InfoPatientActivity.class);
                if ( theme != null && type != null && nameExo != null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Exo choisi :" + theme + "/" + type + "/" + nameExo + "/", Toast.LENGTH_LONG);
                    toast.show();

                    login.addExercise(self, patient_to_add, type.replace(" ",""), nameExo);
                    startActivity(intent);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Choisir bien l'exercice!", Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });
    }

    public void giveSuccess() {
        Toast toast = Toast.makeText(getApplicationContext(), "Exercice ajouté!", Toast.LENGTH_LONG);
        toast.show();
    }
}
