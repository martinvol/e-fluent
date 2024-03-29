package com.example.user.efluent;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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


public class LoginManager {

    private static String ADDRESS = "162.243.214.40:8080/API";
    //private static String ADDRESS = "10.0.2.2:8000/API";
    private static String FULLURL;

    public ArrayList<Patient> patient_list;

    //public void LoginManager
    private OkHttpClient client;
    private MainActivity activity;

    public String token;


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
                if (!(activity == null)){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                                if (role.equals("Patient")) {
                                    activity.loginSucessPatient();
                                } else {
                                    activity.loginSucessOrtho();
                                }

                        }
                    });
                }

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

                    if ( !(null == fragment1)) {
                        fragment1.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragment1.setPatients(patient_list);
                                self.patient_list = patient_list;
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void patientList2(final GiveRendezvousActivity activity1){
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

                    if ( !(null == activity1)) {
                        activity1.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity1.setPatients(patient_list);
                            }
                        });
                    }
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
                        JSONObject typeJson = exercise_json.getJSONObject("exercise");
                        exercise.type = typeJson.getString("name");
                        //exercise.type = meeting_json.getInt("exercise"); //FIXME it's more than a int
                        exerciseList.add(exercise);
                        //Log.i("LoginManager",exercise.type);
                    }

                    // A new thread is created for the display of data on the user's side
                    //fragment.getActivity().runOnUiThread(new Runnable() {
                    if (!(tab == null)) {
                        tab.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                    tab.setExercises(exerciseList);
                                }

                        });
                    }


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
                        Log.i("test", "The time of the meeting is: " + meeting_json.getString("time"));
                        meetings_list.add(meeting);
                    }

                    /*A new thread is created for the display of data on the user's side*/
                    //fragment.getActivity().runOnUiThread(new Runnable() {
                    if (!(fragment == null)) {
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                fragment.setMeetings(meetings_list);
                            }
                        });
                    }


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

                if (!(activity == null)) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            activity.addNewToList(patient);
                        }
                    });
                }

            }
        });



    }

    public void sendExerciseSonometre(final Sonometre act, String ex_id){
        RequestBody formBody = new FormBody.Builder()
                .build();

        Request request = withHeader("/makeexercise/" + ex_id + "/")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                final String reponse_text =  response.body().string();
                Log.i("test",reponse_text);

                if (!(act == null)) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            act.notifyResult(reponse_text);
                        }

                    });
                }

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

            Request request = withHeader("/makeexercise/" + ex_id + "/")
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new File(path) ))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    final String reponse_text =  response.body().string();
                    Log.i("test",reponse_text);

                    if (!(act == null)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                    act.notifyResult(reponse_text);
                                }

                        });
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** ADD ORTHOPHONISTE TO THE SERVER HERE**/
    public void createOrthophoniste(Orthophonist ortho, final InscriptionProActivity activity) {

        RequestBody formBody = new FormBody.Builder()
                .add("email", ortho.email)
                .add("username", ortho.username)
                .add("password", ortho.password)
                .build();

        Request request = withHeader("/register_orthophoniste/")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, final Response response) throws IOException {

                final String text_response = response.body().string();

                if (!(activity == null)) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("test", text_response);
                            activity.singUpSuccess();
                        }
                    });
                }
            }
        });
    }


    public void addExercise(final GiveExerciseActivity activity, Patient patient_to_add, Integer type, String nameExo) {

        RequestBody formBody = new FormBody.Builder()
                .add("patient", patient_to_add.id)
                .add("word", nameExo)
                .add("exercise", type.toString())
                .build();

        //Request request = withHeader("/give_exercise/" + type + "/" + nameExo + "/" +patient_to_add.id + "/")
        Request request = withHeader("/give_exercise/")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, final Response response) throws IOException {

                final String text_response = response.body().string();

                if (!(activity == null)) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("test", text_response);
                            activity.giveSuccess();
                        }
                    });
                }
            }
        });

    }

    public void createRDV(final GiveRendezvousActivity activity, Patient patient, String formatedDate) {
        RequestBody formBody = new FormBody.Builder()
                .add("time", formatedDate)
                .add("patient", patient.id)
                .build();
        Request request = withHeader("/set_RDV/")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, final Response response) throws IOException {

                final String text_response = response.body().string();

                if (!(activity == null)) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("test", text_response);
                            activity.giveSuccess();
                        }
                    });
                }
            }
        });

    }

    public void mitrivialFunc(String formatedDate, GiveRendezvousActivity self, Patient choosenPatient) {
        RequestBody formBody = new FormBody.Builder()
                .add("patient", choosenPatient.id)
                .add("time", formatedDate)
                .build();
    }
}