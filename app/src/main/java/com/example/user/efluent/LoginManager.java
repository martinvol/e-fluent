package com.example.user.efluent;

import android.util.Log;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Callback;


public class LoginManager {

    //public void LoginManager
    private OkHttpClient client;
    private MainActivity activity;

    public LoginManager(MainActivity activity){
        this.activity = activity;
        client = new OkHttpClient();
    }

    public void login(String username, String password){
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8000/API/api-token-auth/")
                .post(formBody)
                .build();

        Log.i("test", username);
        Log.i("test", password);


        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
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


                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.loginSucess();
                    }
                });

            }
        });

        /*try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
            System.out.println(response.body().string());
        } catch (java.io.IOException e){
            Log.i("test", "error");
            //return false;
        }*/



        //return true;
    }
}
