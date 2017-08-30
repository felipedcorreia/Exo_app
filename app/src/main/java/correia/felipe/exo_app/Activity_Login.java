package correia.felipe.exo_app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Felipe on 04/08/2017.
 */

public class Activity_Login extends AppCompatActivity {


    EditText editEmail, editPassword;
    Button btnLogin, btnJson;
    TextView txtRegister, txtRegisterLink, txtjson;

    public String TAG_EMAIL;
    public String TAG_PASSWORD;


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

                //callserver(json);
                //Log.d("Json","request");
                new Thread() {
                    public void run() {
                        HttpConnection.getSetDataWeb("http://8ecfbaac.ngrok.io/api/login", json);
                        //Log.i("Script", "ANSWER" + answer);
                        //int answer = HttpConnection.getStatusCode("http://8ecfbaac.ngrok.io/api/login", json);


                    }
                }.start();
                //Toast.makeText(getApplicationContext(),answer , Toast.LENGTH_LONG).show();
                Log.d("Script", "ANSWER" + answer);
                Intent login = new Intent(Activity_Login.this, Activity_Principal.class);
                startActivity(login);
                /*if(){
                    Intent login = new Intent();
                    startActivity(login);
                }*/


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

    public int answer;

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














