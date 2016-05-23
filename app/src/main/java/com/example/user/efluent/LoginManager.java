package com.example.user.efluent;

import android.util.ArrayMap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Callback;


public class LoginManager {

    private static String ADRESS = "10.0.2.2:8000/API";
    private static String FULLURL;

    //public void LoginManager
    private OkHttpClient client;
    private MainActivity activity;

    public String token;

    public LoginManager(MainActivity activity){
        this.activity = activity;
        client = new OkHttpClient();
        FULLURL = "http://" + ADRESS;
    }

    private void getToken(String response){
        try {
            JSONObject reader = new JSONObject(response);
            //JSONObject tokenOb  = reader.getJSONObject("token");
            this.token = reader.getString("token");
            Log.i("test", "The token is: " + token );

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void login(String username, String password){
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(FULLURL + "/api-token-auth/")
                .post(formBody)
                .build();

        Log.i("test", username);
        Log.i("test", password);

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                String out =  response.body().string();
                System.out.println(out);
                if (response.code() == 400){

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showErrorLogin();
                        }
                    });

                    return;
                }
                if (!response.isSuccessful()){
                    throw new IOException("Unexpected code " + response);
                }

                // save token

                getToken(out);
                patientList();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.loginSucess();
                    }
                });

            }
        });
    }

    private Request withHeader(String url){
        Request request = new Request.Builder()
                .addHeader("Authorization", "Token " + this.token)
                .url(FULLURL + url)
                .build();
        return request;
    }

    private void patientList(){
        Request request = withHeader("/patient_list/");

        final LoginManager self = this;

        final ArrayList<Patient> patient_list = new ArrayList<Patient>();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                String out =  response.body().string();
                System.out.println(out);

                try {
                    JSONArray array = new JSONArray(out);
                    Patient patient = new Patient(self);
                    for (int i=0; i < array.length(); i++) {
                        JSONObject patient_json = array.getJSONObject(i);
                        patient.id = patient_json.getString("id");
                        JSONObject user_json = patient_json.getJSONObject("user");
                        patient.first_name = user_json.getString("first_name");
                        patient.last_name = user_json.getString("last_name");
                        patient.email = user_json.getString("email");
                        /*System.out.println(patient.first_name);
                        System.out.println(patient.last_name);
                        System.out.println(patient.email);*/
                        patient_list.add(patient);

                        //TODO Here it's missing a callback to return the list of patients
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}