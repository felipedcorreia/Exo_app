package correia.felipe.exo_app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by Felipe on 04/08/2017.
 */

public class Activity_Login extends AppCompatActivity {


    EditText editEmail, editPassword;
    Button btnLogin;
    TextView txtRegister, txtRegisterLink;

    public String answer;

    public String TAG_EMAIL;
    public String TAG_PASSWORD;

    private String FEED_URL = "http://blessp.azurewebsites.net/api/login";


    JSONArray user = null;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.edt_email);
        editPassword = (EditText) findViewById(R.id.edt_password);

        btnLogin = (Button) findViewById(R.id.btn_login);

        txtRegister = (TextView) findViewById(R.id.txt_register);
        txtRegisterLink = (TextView) findViewById(R.id.txt_register_link);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String json = makeJson(editEmail, editPassword);
                Log.d("Script", "JSON LOGIN: " + json);
                //callserver(json);
                //Log.d("Json","request");
                new Thread() {
                    public void run() {
                        //HttpConnection.getSetDataWeb("http://8ecfbaac.ngrok.io/api/login", json);
                        //answer = HttpConnection.getSetDataWeb(FEED_URL, json);
                        answer = getSetDataWeb(FEED_URL, json);
                        //Log.i("Script", "ANSWER" + answer);
                        //int answer = HttpConnection.getStatusCode("http://8ecfbaac.ngrok.io/api/login", json);
                    }
                }.start();
                //Toast.makeText(getApplicationContext(),answer , Toast.LENGTH_LONG).show();
                Log.d("Script", "ANSWER: " + answer);


                //com API
                if(answer == null) {
                    Toast.makeText(getApplicationContext(),"Tente novamente" , Toast.LENGTH_LONG).show();
                }else{
                    JSONObject token_json = null;
                    try {
                        token_json = new JSONObject(answer);
                        String token = token_json.getString("token");
                        Log.d("LOGIN", "TOKEN: " + token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Intent login = new Intent(Activity_Login.this, Activity_Principal.class);
                    startActivity(login);

                }
/*

                Intent login = new Intent(Activity_Login.this, Activity_Principal.class);
                startActivity(login);

*/

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
            // ArrayList<String> valores = new ArrayList<String>();
            //valores.add(new BasicNameValuePair("method", method));
            //valores.add(data);
            //valores.add(new BasicNameValuePair("json", data));


            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            StringEntity postString = new StringEntity(data, "UTF-8");
            httpPost.setEntity(postString);

            // httpPost.setEntity(new UrlEncodedFormEntity(valores));
            HttpResponse resposta = httpClient.execute(httpPost);
            //String s = EntityUtils.toString(resposta.getEntity());

            int statusCode = resposta.getStatusLine().getStatusCode();
            Log.d("LOGIN", "Status Code: " + statusCode);
            answer = EntityUtils.toString(resposta.getEntity());
            Log.d("LOGIN", "Resposta: " + answer);
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
            } else if(statusCode == 401){

                //String response = streamToString(resposta.getEntity().getContent());
                answer = null;
                Log.d("LOGIN", "ANSWER 401: " + answer);
                //parseResult(response);

            } else{
                answer = null;
                return answer;
                // Failed
            }
*/
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }
/*
private void call_api(final String email, final String password){

    AsyncTask<String, String, String> sync = new AsyncTask<String, String, String>() {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd.setMessage("Por favor, espere");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(FEED_URL);
            try{

                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                StringEntity postString = new StringEntity(, "UTF-8");
                httpPost.setEntity(postString);

                // httpPost.setEntity(new UrlEncodedFormEntity(valores));
                HttpResponse resposta = httpClient.execute(httpPost);
                String s = EntityUtils.toString(resposta.getEntity());
                Log.d("LOGIN", "Resposta: " + s);
                int statusCode = resposta.getStatusLine().getStatusCode();
                Log.d("LOGIN", "Status Code: " + statusCode);
                answer = EntityUtils.toString(resposta.getEntity());


            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }


}*/


    @SuppressLint("NewApi")
    private void callserver(final String data) {
        new Thread() {
            public void run() {
                String answer = HttpConnection.getSetDataWeb("http://8ecfbaac.ngrok.io/api/login", data);
                //Log.i("Script", "ANSWER" + answer);
                //    int answer = HttpConnection.getStatusCode("http://8ecfbaac.ngrok.io/api/login", data);
                Log.d("Script", "ANSWER" + answer);

            }
        }.start();


    }

}














