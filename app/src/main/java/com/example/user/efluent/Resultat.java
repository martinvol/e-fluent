package com.example.user.efluent;

import java.util.Date;

/**
 * Created by Administrateur on 25/05/16.
 */
public class Resultat {

    public String id;
    public Exercice exercice;
    public String isDone;
    public String isValidate;
    public String pourcentage;

    private LoginManager data;

    public Resultat(LoginManager data){
        this.data = data;
    }
}
