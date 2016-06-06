package com.example.user.efluent;

/**
 * Created by martin on 19/05/16.
 */
public class User {

    public String first_name;
    public String last_name;
    public String email;
    public String id;
    public String password;

    private LoginManager data;

    public User(LoginManager data){
        this.data = data;
    }

    public Boolean isValid(){
        return !(first_name.equals("") || last_name.equals("") || password.equals("")
                || email.equals(""));

    }
}