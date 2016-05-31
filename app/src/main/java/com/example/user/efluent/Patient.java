package com.example.user.efluent;

import java.util.ArrayList;

/**
 * Created by martin on 19/05/16.
 */
public class Patient extends User {

    public ArrayList<Resultat> liste_resultats;

    public Patient(LoginManager data){
        super(data);
    }
}
