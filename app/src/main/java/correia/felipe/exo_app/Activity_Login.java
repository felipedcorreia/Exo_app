package correia.felipe.exo_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by Felipe on 04/08/2017.
 */

public class Activity_Login extends AppCompatActivity {


    protected ProgressBar mProgressBar;

    EditText editEmail, editPassword;
    Button btnLogin;
    TextView txtRegister, txtRegisterLink, txtForgotPass;

    public String answer;
    public String message_errror_1;



    private String FEED_URL = "http://blessp.azurewebsites.net/api/login";
    //private String FEED_URL = "http://192.168.0.14:8000/api/login";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mProgressBar = (ProgressBar) findViewById(R.id.pg_loading);

        editEmail = (EditText) findViewById(R.id.edt_email);
        editPassword = (EditText) findViewById(R.id.edt_password);

        btnLogin = (Button) findViewById(R.id.btn_login);

        txtRegister = (TextView) findViewById(R.id.txt_register);
        txtRegisterLink = (TextView) findViewById(R.id.txt_register_link);

        txtForgotPass =(TextView) findViewById(R.id.txtForgotPassword);

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent omg_iforgot_my_password = new Intent(Activity_Login.this, Activity_Forgot_Password.class);
                startActivity(omg_iforgot_my_password);

            }
        });





        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (editEmail.getText().length() == 0) {
                    editEmail.setError("Digite um email");
                    editEmail.requestFocus();
                }
                if(editPassword.getText().length() == 0) {
                    editPassword.setError("Digite um email");
                    editPassword.requestFocus();
                }

                if(editEmail.getText().length() != 0 && editPassword.getText().length() != 0){

                    checkLogin();
                }
            }
        });


        txtRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Activity_Login.this, Activity_Plan_Choose.class);
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
                // CASO: 401 quando o usuario esta desativado e digitou a senha incorretamente
            }else if(result == 401){
                new Thread() {
                public void run() {
                    try {
                        answer = getSetDataWeb(FEED_URL, makeJson);
                        JSONObject error_json = new JSONObject(answer);
                        final String error_1 = error_json.getString("prompt1");
                        Log.d("LOGIN", "Error_1: " + error_1);
                        final String error_2 = error_json.getString("prompt2");
                        Log.d("LOGIN", "Error_2: " + error_2);

                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Your UI updates here
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Login.this);
                                builder1.setMessage(error_1);
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "SIM",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                AlertDialog.Builder builder2 = new AlertDialog.Builder(Activity_Login.this);
                                                builder2.setMessage(error_2);
                                                builder2.setCancelable(true);

                                                builder2.setNeutralButton(
                                                        "OK",
                                                        new DialogInterface.OnClickListener(){
                                                            public void onClick(DialogInterface dialog, int id){
                                                                dialog.cancel();
                                                            }
                                                        }
                                                );
                                                AlertDialog alert12 = builder2.create();
                                                alert12.show();

                                                Button bqNeutral = alert12.getButton(DialogInterface.BUTTON_NEUTRAL);
                                                bqNeutral.setTextColor(Color.parseColor("#4169E1"));
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "NÃO",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });



                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                                Button bqNegative = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
                                bqNegative.setTextColor(Color.parseColor("#4169E1"));

                                Button bqPositive = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
                                bqPositive.setTextColor(Color.parseColor("#4169E1"));
                            }
                        });




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();



                Toast.makeText(Activity_Login.this, "Status Code: "+result, Toast.LENGTH_SHORT).show();
            //CASO 404: Erro de Usuario ou senha;
            }else if(result == 404){
                new Thread() {
                    public void run() {
                        try {
                            answer = getSetDataWeb(FEED_URL, makeJson);
                            JSONObject error_json = new JSONObject(answer);
                            final String error_1 = error_json.getString("error");
                            Log.d("LOGIN", "Error_1: " + error_1);


                            Handler mHandler = new Handler(Looper.getMainLooper());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Your UI updates here
                                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Login.this);
                                    builder1.setMessage(error_1);
                                    builder1.setCancelable(true);

                                    builder1.setNeutralButton(
                                            "OK",
                                            new DialogInterface.OnClickListener(){
                                                public void onClick(DialogInterface dialog, int id){
                                                    dialog.cancel();
                                                }
                                            }
                                    );



                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();

                                    Button bqNeutral = alert11.getButton(DialogInterface.BUTTON_NEUTRAL);
                                    bqNeutral.setTextColor(Color.parseColor("#4169E1"));
                                }
                            });




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();



                Toast.makeText(Activity_Login.this, "Status Code: "+result, Toast.LENGTH_SHORT).show();
            //CASO 403: Usuario desativado;
            }else if(result == 403){
                new Thread() {
                    public void run() {
                        try {
                            answer = getSetDataWeb(FEED_URL, makeJson);
                            JSONObject error_json = new JSONObject(answer);
                            final String error_1 = error_json.getString("error");
                            Log.d("LOGIN", "Error_1: " + error_1);
                            final String error_2 = error_json.getString("path");
                            Log.d("LOGIN", "Error_2: " + error_2);


                            Handler mHandler = new Handler(Looper.getMainLooper());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Your UI updates here
                                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Login.this);
                                    builder1.setMessage(error_1);
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "SIM",
                                            new DialogInterface.OnClickListener(){
                                                public void onClick(DialogInterface dialog, int id){
                                                    Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(error_2));
                                                    startActivity(viewIntent);
                                                }
                                            }
                                    );

                                    builder1.setNegativeButton(
                                            "NÃO",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });



                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();

                                    Button bqNegative = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
                                    bqNegative.setTextColor(Color.parseColor("#4169E1"));

                                    Button bqPositive = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
                                    bqPositive.setTextColor(Color.parseColor("#4169E1"));

                                }
                            });




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();



                Toast.makeText(Activity_Login.this, "Status Code: "+result, Toast.LENGTH_SHORT).show();
            }

            //ToDo error 422
        }
    }


}














