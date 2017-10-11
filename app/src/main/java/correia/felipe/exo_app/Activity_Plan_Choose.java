package correia.felipe.exo_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Activity_Plan_Choose extends AppCompatActivity {

    private TextView plan_1, description_1, price_1, plan_2, description_2, price_2;
    private Button btn_price_1, btn_price_2;
    //private String FEED_URL = "http://blessp.azurewebsites.net/api/plans";
    private String FEED_URL = "http://192.168.0.14:8000/api/plans";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_choose);


        plan_1 = (TextView) findViewById(R.id.title_plan_1);
        description_1 = (TextView) findViewById(R.id.description_plan_1);
        price_1 = (TextView) findViewById(R.id.price_plan_1);
        btn_price_1 = (Button) findViewById(R.id.btn_plan1);

        plan_2 = (TextView) findViewById(R.id.title_plan_2);
        description_2 = (TextView) findViewById(R.id.description_plan_2);
        price_2 = (TextView) findViewById(R.id.price_plan_2);
        btn_price_2 = (Button) findViewById(R.id.btn_plan2);

        new AsyncHttpTask().execute(FEED_URL);

        btn_price_1.setOnClickListener(new View.OnClickListener() {

            String id = "1";
            @Override
            public void onClick(View v) {


                Intent go_register = new Intent(Activity_Plan_Choose.this, Activity_Register.class);
                go_register.putExtra("id", id);
                startActivity(go_register);
            }
        });


        btn_price_2.setOnClickListener(new View.OnClickListener() {
            String id = "2";
            @Override
            public void onClick(View v) {
                Intent go_register = new Intent(Activity_Plan_Choose.this, Activity_Register.class);
                go_register.putExtra("id", id);
                startActivity(go_register);
            }
        });


    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        ProgressDialog pdLoading = new ProgressDialog(Activity_Plan_Choose.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected Integer doInBackground(String... params) {

            Integer statusCode = 0;

            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                HttpResponse httpResponse = httpclient.execute(httpGet);


                String response = streamToString(httpResponse.getEntity().getContent());
                Log.d("doInBackground", "Response: " + response);
                statusCode = httpResponse.getStatusLine().getStatusCode();
                Log.d("doInBackground", "Status Code: " + statusCode);
                parseResult(response);


            } catch (Exception e) {

            }

            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 200) {
                Toast.makeText(Activity_Plan_Choose.this, "Status Code: " + result, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(Activity_Plan_Choose.this, "Status Code: " + result, Toast.LENGTH_SHORT).show();
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
        try {
            JSONArray response = new JSONArray(result);
            PlanItem item = null;
            PlanItem item_1 = null;
            int c = 0;

            for (int i = 0; i < response.length(); i++) {

                if (i == 0) {
                    item = new PlanItem();
                    JSONObject jsonO = response.getJSONObject(i);
                    Log.d("Parser", "JSON response: " + response);
                    JSONArray device = jsonO.getJSONArray("devices");
                    Log.d("Parser", "JSONArray device: " + device);
                    for (int j = 0; j < device.length(); j++) {
                        JSONObject jsonO1 = device.getJSONObject(j);
                        String devices_id = jsonO1.getString("id");
                        Log.d("Parser device", "JSONObject id: " + devices_id);
                        String devices_number = jsonO1.getString("devices");
                        Log.d("Parser device", "JSONObject device number: " + devices_number);
                        String devices_code = jsonO1.getString("code");
                        Log.d("Parser device", "JSONObject devices code: " + devices_code);
                        item.setDevices_id(devices_id);
                        item.setDevides_code(devices_code);
                        item.setDevices_number(devices_number);
                    }
                    final String amount = jsonO.getString("amount");
                    Log.d("Parser", "JSONObject amount: " + amount);
                    String code = jsonO.getString("code");
                    Log.d("Parser", "JSONObject code: " + code);
                    final String description = jsonO.getString("description");
                    Log.d("Parser", "JSONObject description: " + description);

                    item.setAmount(amount);
                    item.setCode(code);
                    item.setCode(description);

                    JSONObject trial = jsonO.getJSONObject("trial");
                    Log.d("Parser", "JSONObject Trial: " + trial);


                    String trial_hold_setup_fee = trial.getString("hold_setup_fee");
                    Log.d("Parser trial", "JSONObject hold: " + trial_hold_setup_fee);
                    String trial_days = trial.getString("days");
                    Log.d("Parser trial", "JSONObject days: " + trial_days);
                    String trial_enabled = trial.getString("enabled");
                    Log.d("Parser trial", "JSONObject enabled: " + trial_enabled);
                    item.setTrial_hold_setup_fee(trial_hold_setup_fee);
                    item.setTrial_days(trial_days);
                    item.setTrial_enabled(trial_enabled);

                    final String name = jsonO.getString("name");
                    Log.d("Parser", "JSONObject name: " + name);
                    item.setName(name);

                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Parser thread", "THREAD");
                            plan_1.setText(name);
                            description_1.setText(description);
                            String text2 = price_1.getText().toString();
                            String now2 = text2 + amount;
                            price_1.setText(now2);
                        }
                    });


                } else if (i == 1) {
                    item_1 = new PlanItem();
                    JSONObject jsonO = response.getJSONObject(i);
                    JSONArray device = jsonO.getJSONArray("devices");
                    Log.d("Parser", "JSONArray device: " + device);
                    for (int j = 0; j < device.length(); j++) {
                        JSONObject jsonO1 = device.getJSONObject(j);
                        String devices_id = jsonO1.getString("id");
                        Log.d("Parser device", "JSONObject id: " + devices_id);
                        String devices_number = jsonO1.getString("devices");
                        Log.d("Parser device", "JSONObject device number: " + devices_number);
                        String devices_code = jsonO1.getString("code");
                        Log.d("Parser device", "JSONObject devices code: " + devices_code);
                        item_1.setDevices_id(devices_id);
                        item_1.setDevides_code(devices_code);
                        item_1.setDevices_number(devices_number);
                    }
                    final String amount = jsonO.getString("amount");
                    Log.d("Parser", "JSONObject amount: " + amount);
                    String code = jsonO.getString("code");
                    Log.d("Parser", "JSONObject code: " + code);
                    final String description = jsonO.getString("description");
                    Log.d("Parser", "JSONObject description: " + description);

                    item_1.setAmount(amount);
                    item_1.setCode(code);
                    item_1.setCode(description);

                    JSONObject trial = jsonO.getJSONObject("trial");
                    Log.d("Parser trial", "JSONObject Trial: " + trial);

                    String trial_hold_setup_fee = trial.getString("hold_setup_fee");
                    Log.d("Parser trial", "JSONObject hold: " + trial_hold_setup_fee);
                    String trial_days = trial.getString("days");
                    Log.d("Parser trial", "JSONObject days: " + trial_days);
                    String trial_enabled = trial.getString("enabled");
                    Log.d("Parser trial", "JSONObject enabled: " + trial_enabled);
                    item_1.setTrial_hold_setup_fee(trial_hold_setup_fee);
                    item_1.setTrial_days(trial_days);
                    item_1.setTrial_enabled(trial_enabled);

                    final String name = jsonO.getString("name");
                    Log.d("Parser", "JSONObject name: " + name);
                    item_1.setName(name);

                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Parser thread", "THREAD");
                            plan_2.setText(name);
                            description_2.setText(description);
                            String text2 = price_2.getText().toString();
                            String now2 = text2 + amount;
                            price_2.setText(now2);

                        }

                    });
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
