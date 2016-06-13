package com.example.user.efluent;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Callback;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;



public class LoginManager {

    private static String ADDRESS = "162.243.214.40:9000/API";
    //private static String ADDRESS = "10.0.2.2:8000/API";
    private static String FULLURL;

    //public void LoginManager
    private OkHttpClient client;
    private MainActivity activity;

    public String token;

    public Patient addpatient;

    public LoginManager(MainActivity activity){
        this.activity = activity;
        client = new OkHttpClient();
        FULLURL = "http://" + ADDRESS;
    }

    private String get_item(String response, String key){
        try {
            JSONObject reader = new JSONObject(response);
            //JSONObject tokenOb  = reader.getJSONObject("token");
            return reader.getString(key);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public void login(String username, String password){
        RequestBody formBody = new FormBody.Builder()
                .add("username", username.toLowerCase())
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
                token = get_item(out,"token" );
                Log.i("test", "The token is: " + token );

                final String role = get_item(out,"role_name");

                Log.i("test", "role is: " + role);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(role.equals("Patient")){
                            activity.loginSucessPatient();
                        } else {
                            activity.loginSucessOrtho();
                        }
                    }
                });

            }
        });
    }

    private Request.Builder withHeader(String url){
        Request.Builder request = new Request.Builder()
                .addHeader("Authorization", "Token " + this.token)
                .url(FULLURL + url);
                //.build();
        return request;
    }

    public void patientList(final TabFragmentPro1 fragment1){
        Request request = withHeader("/patient_list/").build();

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

                    for (int i=0; i < array.length(); i++) {
                        Patient patient = new Patient(self);
                        JSONObject patient_json = array.getJSONObject(i);
                        patient.id = patient_json.getString("id");
                        JSONObject user_json = patient_json.getJSONObject("user");
                        patient.first_name = user_json.getString("first_name");
                        patient.last_name = user_json.getString("last_name");
                        patient.email = user_json.getString("email");
                        patient_list.add(patient);
                    }

                    fragment1.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment1.setPatients(patient_list);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void getListOfExercisesFromRequest(Request request, final ExerciseReceiver tab){
        client.newCall(request).enqueue(new Callback() {

            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override public void onResponse(Call call, Response response) throws IOException {
                String out =  response.body().string();
                System.out.println(out);

                final ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();

                try {
                    JSONArray array = new JSONArray(out);
                    for (int i=0; i < array.length(); i++) {
                        Exercise exercise = new Exercise();
                        JSONObject exercise_json = array.getJSONObject(i);
                        exercise.done = exercise_json.getBoolean("done");
                        exercise.word = exercise_json.getString("word");
                        exercise.id = exercise_json.getInt("id");
                        //exercise.type = meeting_json.getInt("exercise"); //FIXME it's more than a int
                        exerciseList.add(exercise);
                    }

                    // A new thread is created for the display of data on the user's side
                    //fragment.getActivity().runOnUiThread(new Runnable() {
                    tab.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tab.setExercises(exerciseList);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                } /*catch (ParseException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    public void getListOfExercises(final ExerciseReceiver tab, String id) {

        Request request = withHeader("/exercises/"+id+"/").build();
        getListOfExercisesFromRequest(request, tab);

    }

    public void getListOfExercises(final ExerciseReceiver tab) {
        Request request = withHeader("/exercises/").build();
        getListOfExercisesFromRequest(request, tab);
    }

    public void getListOfMeetings(final MeetingReceiver fragment) {
        Request request = withHeader("/create_meeting/").build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                String out =  response.body().string();
                System.out.println(out);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                final ArrayList<Meeting> meetings_list = new ArrayList<Meeting>();

                try {
                    JSONArray array = new JSONArray(out);
                    for (int i=0; i < array.length(); i++) {
                        Meeting meeting = new Meeting();
                        JSONObject meeting_json = array.getJSONObject(i);
                        meeting.time = format.parse(meeting_json.getString("time"));
                        //Log.i("test", "The time of the meeting is: " + meeting.time.toString());
                        meetings_list.add(meeting);
                    }

                    /*A new thread is created for the display of data on the user's side*/
                    //fragment.getActivity().runOnUiThread(new Runnable() {
                    fragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment.setMeetings(meetings_list);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        //FIXME this method is too generic
    }

    public void addPatient(final Patient patient, final AddPatientActivity activity) {
        RequestBody formBody = new FormBody.Builder()
                .add("first_name", patient.first_name)
                .add("last_name", patient.last_name)
                .add("password", patient.password)
                .add("email", patient.email)
                .build();


        Request request = withHeader("/add_patient/")
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.addNewToList(patient);
                    }
                });

            }
        });



    }

    public void sendExercise(final ExerciseVocal act, String path, String ex_id){

        Log.i("test","pido ejes");
        // InputStream iS = resources.getAssets().open("bla.txt");
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");

        try {
            File file;
            InputStream inputStream = act.getAssets().open("hello.wav");
            RequestBody requestBody = RequestBodyUtil.create(MEDIA_TYPE_MARKDOWN, inputStream);


            /*Request request = withHeader("/makeexercise/1/")
                    .post(requestBody)
                    .build();*/

            Request request = withHeader("/makeexercise/" + ex_id + "/")
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new File(path) ))
                    //.post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    final String reponse_text =  response.body().string();
                    Log.i("test",reponse_text);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            act.notify_result(reponse_text);
                        }
                    });

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** ADD ORTHOPHONISTE TO THE SERVER HERE**/
    public static void createOrthophoniste(Orthophonist ortho, InscriptionProActivity self) {
    }
}
