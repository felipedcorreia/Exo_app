package correia.felipe.exo_app;

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

/**
 * Created by Felipe on 06/10/2017.
 */

public class Activity_Forgot_Password extends AppCompatActivity {

    private String FEED_URL = "http://blessp.azurewebsites.net/api/recovery";
    //private String FEED_URL = "http://192.168.0.14:8000/api/recovery";

    EditText edt_email;
    Button btn_enviar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edt_email = (EditText) findViewById(R.id.edt_email_forgot_password);
        btn_enviar = (Button) findViewById(R.id.btn_forgot_password);

        btn_enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (edt_email.getText().length() == 0) {
                    edt_email.setError("Digite um email");
                    edt_email.requestFocus();
                }

                if(edt_email.getText().length() != 0){
                    final String email = edt_email.getText().toString();
                    new AsyncLogin().execute(email);
                }
            }
        });

    }

    private class AsyncLogin extends AsyncTask<String, String, Integer> {
        ProgressDialog pdLoading = new ProgressDialog(Activity_Forgot_Password.this);

        final String makeJson = makeJson(edt_email);

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
                Log.d("FORGOT PASSWORD", "DO IN BACK Status Code : " + statusCode);
                answer = EntityUtils.toString(resposta.getEntity());
                Log.d("FORGOT PASSWORD", "DO IN BACK Resposta: " + answer);

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
                        final String answer = getAnswer(FEED_URL, makeJson);
                        //JSONObject answer_json = new JSONObject(answer);
                        //final String message = answer_json.getString("message");
                        //final String message = answer_json.toString();
                        Log.d("FORGOT PASSWORD", "messagem: " + answer);

                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Your UI updates here
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Forgot_Password.this);
                                //builder1.setMessage(message);
                                builder1.setMessage(answer);
                                builder1.setCancelable(true);

                                builder1.setNeutralButton(
                                        "OK",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent back_login = new Intent(Activity_Forgot_Password.this, Activity_Login.class);
                                                startActivity(back_login);
                                            }
                                        }
                                );



                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                                Button bqNeutral = alert11.getButton(DialogInterface.BUTTON_NEUTRAL);
                                bqNeutral.setTextColor(Color.parseColor("#4169E1"));
                            }
                        });


                    }
                }.start();

            }
        }
    }


    public String makeJson(EditText email) {
        JSONArray jArray = new JSONArray();
        JSONObject jObj = new JSONObject();

        try {
            jObj.put("email", email.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        jArray.put(jObj);
        return (jObj.toString());
    }

    public static String getAnswer(String url, String data) {

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
            Log.d("FORGOT PASSWORD", "Status Code: " + statusCode);
            answer = EntityUtils.toString(resposta.getEntity());
            Log.d("FORGOT PASSWORD", "Resposta: " + answer);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }
}
