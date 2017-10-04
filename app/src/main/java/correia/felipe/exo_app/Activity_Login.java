package correia.felipe.exo_app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Felipe on 04/08/2017.
 */

public class Activity_Login extends AppCompatActivity {

    protected static final int TIMER_RUNTIME = 10000;
    public static final int READ_TIMEOUT=15000;
    public static final int CONNECTION_TIMEOUT=10000;
    protected boolean mLoading;
    protected ProgressBar mProgressBar;

    EditText editEmail, editPassword;
    Button btnLogin;
    TextView txtRegister, txtRegisterLink;

    public String answer;

    public String TAG_EMAIL;
    public String TAG_PASSWORD;

    private String FEED_URL = "http://blessp.azurewebsites.net/api/login";
    //private String FEED_URL = "http://192.168.0.14:8000/api/login";



    JSONArray user = null;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = (ProgressBar) findViewById(R.id.pg_loading);

        editEmail = (EditText) findViewById(R.id.edt_email);
        editPassword = (EditText) findViewById(R.id.edt_password);

        btnLogin = (Button) findViewById(R.id.btn_login);

        txtRegister = (TextView) findViewById(R.id.txt_register);
        txtRegisterLink = (TextView) findViewById(R.id.txt_register_link);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //mProgressBar.setVisibility(View.VISIBLE);
                if (editEmail.getText().length() == 0) {
                    editEmail.setError("Digite um email");
                    editEmail.requestFocus();
                    mProgressBar.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(), "Digite o usuário e/ou senha", Toast.LENGTH_LONG).show();
                }
                if(editPassword.getText().length() == 0) {
                    editPassword.setError("Digite um email");
                    editPassword.requestFocus();
                    mProgressBar.setVisibility(View.GONE);
                }

                if(editEmail.getText().length() != 0 && editPassword.getText().length() != 0){

                    checkLogin();

                 /*

                    final String json = makeJson(editEmail, editPassword);
                    Log.d("Script", "JSON LOGIN: " + json);
                    new Thread() {
                        public void run() {

                            try {
                                Log.d("THREAD", "INICIO DA THREAD: ");
                                //Thread.sleep(10000);


                                int wait = 0;
                                mLoading = true;
                                answer = getSetDataWeb(FEED_URL, json);
                                while (mLoading && (wait < TIMER_RUNTIME)) {
                                    sleep(200);
                                    if (mLoading) {
                                        wait += 200;
                                    }
                                    Log.d("THREAD", "WAIT: " + wait);
                                }

                                //com API

                            } catch (InterruptedException e) {

                            } finally {
                                //    mProgressBar.setVisibility(View.GONE);

                            }
                        }
                    }.start();
                    if (answer == null) {
                        Toast.makeText(getApplicationContext(), "Tente novamente", Toast.LENGTH_LONG).show();
                    } else {
                        JSONObject token_json = null;
                        try {
                            token_json = new JSONObject(answer);
                            String token = token_json.getString("token");
                            Log.d("LOGIN", "TOKEN: " + token);
                            final Token_Item token_item = new Token_Item();
                            token_item.setToken(token);

                            Intent login = new Intent(Activity_Login.this, Activity_Principal.class);
                            login.putExtra("token", token_item.getToken());
                            startActivity(login);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }*/

                }
            }
        });


        txtRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Activity_Login.this, Activity_register.class);
                startActivity(register);

            }
        });
    }

    public String makeJson(EditText email, EditText password) {
        JSONArray jArray = new JSONArray();
        JSONObject jObj = new JSONObject();

        try {
            jObj.put("email", email.getText().toString());
            jObj.put("password", password.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        jArray.put(jObj);
        return (jObj.toString());
    }



    public static String getSetDataWeb(String url, String data) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        String answer = null;
        try {
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            StringEntity postString = new StringEntity(data, "UTF-8");
            httpPost.setEntity(postString);

            HttpResponse resposta = httpClient.execute(httpPost);

            int statusCode = resposta.getStatusLine().getStatusCode();
            Log.d("LOGIN", "Status Code: " + statusCode);
            answer = EntityUtils.toString(resposta.getEntity());
            Log.d("LOGIN", "Resposta: " + answer);

            if(statusCode != 200){
                Log.d("LOGIN", "USUARIO INVALIDO: " + answer);
                answer = null;
            }
/*
            // 200 represents HTTP OK
            if (statusCode == 200) {
                //String response = streamToString(resposta.getEntity().getContent());
                answer = EntityUtils.toString(resposta.getEntity());
                Log.d("LOGIN", "ANSWER 200: " + answer);

                //parseResult(response);
                return (answer);

                // 201 represents HTTP OK
            } else if(statusCode == 201){

                //String response = streamToString(resposta.getEntity().getContent());
                answer = EntityUtils.toString(resposta.getEntity());
                Log.d("LOGIN", "ANSWER 201: " + answer);
                //parseResult(response);

                return (answer);
                // 400 represents HTTP solicitação invalida
            } else if(statusCode != 200 || statusCode != 201){

                //String response = streamToString(resposta.getEntity().getContent());
                answer = null;
                Log.d("LOGIN", "ANSWER ERROR: " + answer);
                //parseResult(response);

            } else{
                answer = null;
                // Failed
            }*/

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }


    public void checkLogin(){


        if (editEmail.getText().length() == 0) {
            editEmail.setError("Digite um email");
            editEmail.requestFocus();
            mProgressBar.setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(), "Digite o usuário e/ou senha", Toast.LENGTH_LONG).show();
        }
        if(editPassword.getText().length() == 0) {
            editPassword.setError("Digite um email");
            editPassword.requestFocus();
            mProgressBar.setVisibility(View.GONE);
        }
        final String email = editEmail.getText().toString();
        final String password = editPassword.getText().toString();

        new AsyncLogin().execute(email,password);
    }

    private class AsyncLogin extends AsyncTask<String, String, Integer>{
        ProgressDialog pdLoading = new ProgressDialog(Activity_Login.this);

        final String makeJson = makeJson(editEmail, editPassword);



        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }


        @Override
        protected Integer doInBackground(String... params) {
            Integer statusCode = 0;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(FEED_URL);
            String answer = null;
            try {
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                StringEntity postString = new StringEntity(makeJson, "UTF-8");
                httpPost.setEntity(postString);

                HttpResponse resposta = httpClient.execute(httpPost);

                statusCode = resposta.getStatusLine().getStatusCode();
                Log.d("LOGIN", "Status Code: " + statusCode);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return statusCode;
        }
        @Override
        protected void onPostExecute(Integer result ){
            pdLoading.dismiss();
            if (result == 200) {
                new Thread() {
                    public void run() {
                        try {
                            answer = getSetDataWeb(FEED_URL, makeJson);
                            JSONObject token_json = new JSONObject(answer);
                            String token = token_json.getString("token");
                            Log.d("LOGIN", "TOKEN: " + token);
                            final Token_Item token_item = new Token_Item();
                            token_item.setToken(token);

                            Intent login = new Intent(Activity_Login.this, Activity_Principal.class);
                            login.putExtra("token", token_item.getToken());
                            startActivity(login);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }else{
                Toast.makeText(Activity_Login.this, "Status Code: "+result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}














