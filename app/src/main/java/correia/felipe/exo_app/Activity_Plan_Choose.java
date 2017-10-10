package correia.felipe.exo_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Activity_Plan_Choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_choose);

        TextView plan_1, description_1, price_1, plan_2, description_2, price_2;
        Button btn_price_1, btn_price_2;

        plan_1 = (TextView)findViewById(R.id.title_plan_1);
        description_1 = (TextView)findViewById(R.id.description_plan_1);
        price_1 = (TextView)findViewById(R.id.price_plan_1);
        btn_price_1 = (Button)findViewById(R.id.btn_plan1);

        plan_2 = (TextView)findViewById(R.id.title_plan_2);
        description_2 = (TextView)findViewById(R.id.description_plan_2);
        price_2 = (TextView)findViewById(R.id.price_plan_2);
        btn_price_2 = (Button)findViewById(R.id.btn_plan2);


        btn_price_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_register = new Intent(Activity_Plan_Choose.this, Activity_register.class);

                startActivity(go_register);
            }
        });

        btn_price_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_register = new Intent(Activity_Plan_Choose.this, Activity_register.class);

                startActivity(go_register);
            }
        });



    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        ProgressDialog pdLoading = new ProgressDialog(Activity_Plan_Choose.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            //Integer result;
            Integer statusCode = 0;
            String tokenParser = getIntent().getStringExtra("token");

            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                httpGet.setHeader("Authorization",
                        "Bearer" + tokenParser);
                HttpResponse httpResponse = httpclient.execute(httpGet);
                //HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));

                String response = streamToString(httpResponse.getEntity().getContent());
                Log.d("doInBackground", "Response: " + response);
                parseResult(response);

                statusCode = httpResponse.getStatusLine().getStatusCode();

                /*
                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = statusCode; // Successful
                } else {
                    result = 0; //"Failed
                }*/
            } catch (Exception e) {

            }

            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 200) {
                //Toast.makeText(Activity_Principal.this, "Status Code: "+result, Toast.LENGTH_SHORT).show();

            } else {
                //Toast.makeText(Activity_Principal.this, "Status Code: "+result, Toast.LENGTH_SHORT).show();

            }

            //Hide progressbar
            pdLoading.dismiss();
        }
    }

    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    private void parseResult(String result) throws IOException {
       /* try {
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        }

}
