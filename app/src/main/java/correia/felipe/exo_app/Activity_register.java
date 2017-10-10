package correia.felipe.exo_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;

/**
 * Created by Felipe on 04/08/2017.
 */

public class Activity_register extends AppCompatActivity {

    private EditText edtName;
    private EditText edtCPF;
    private EditText edtBirth;
    private EditText edtDDD;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtAdressStreet;
    private EditText edtAdressNumber;
    private EditText edtAdressComplement;
    private EditText edtAdressNeighborhood;
    private EditText edtAdressCity;
    private EditText edtPassword;
    private EditText edtConfirmPassword;

    private String FEED_URL = "http://blessp.azurewebsites.net/api/";

    private Button btnRegister;

    private Spinner spnPhoneType;
    private Spinner spnChurchType;
    private Spinner spnPlanType;
    private Spinner spnStateType;

    private ArrayAdapter<String> adpPhoneType;
    private ArrayAdapter<String> adpChurchType;
    private ArrayAdapter<String> adpPlanType;
    private ArrayAdapter<String> adpStateType;

    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = (EditText) findViewById(R.id.edtName);
        edtCPF = (EditText) findViewById(R.id.edtCPF);
        edtBirth = (EditText) findViewById(R.id.edtBirth);
        edtDDD = (EditText) findViewById(R.id.edtDDD);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtAdressStreet = (EditText) findViewById(R.id.edtAdressStreet);
        edtAdressNumber = (EditText) findViewById(R.id.edtAdressNumeber);
        edtAdressComplement = (EditText) findViewById(R.id.edtAdressComplement);
        edtAdressNeighborhood = (EditText) findViewById(R.id.edtNeighborhood);
        edtAdressCity = (EditText) findViewById(R.id.edtAdressCity);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);

        spnPhoneType = (Spinner) findViewById(R.id.spnTypePhone);
        spnChurchType = (Spinner) findViewById(R.id.spnTypeChurch);
        spnPlanType = (Spinner) findViewById(R.id.spnTypePlan);
        spnStateType = (Spinner) findViewById(R.id.spnTypeState);

        adpPhoneType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpPhoneType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpChurchType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpChurchType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpPlanType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpPlanType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpStateType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpStateType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnRegister = (Button) findViewById(R.id.btn_cadastrar);


        //setAdapters
        spnPlanType.setAdapter(adpPlanType);
        spnChurchType.setAdapter(adpChurchType);
        spnPhoneType.setAdapter(adpPhoneType);
        spnStateType.setAdapter(adpStateType);

        //addSpinnersItems
        adpPhoneType.add("Casa");
        adpPhoneType.add("Celular");

        adpPlanType.add("Plano 1");
        adpPlanType.add("Plano 2");
        adpPlanType.add("Plano 3");

        adpChurchType.add("Igreja 1");
        adpChurchType.add("Igreja 2");
        adpChurchType.add("Igreja 3");
        adpChurchType.add("Igreja 4");

        adpStateType.add("Estado");
        adpStateType.add("AC");
        adpStateType.add("AL");
        adpStateType.add("AP");
        adpStateType.add("AM");
        adpStateType.add("BA");
        adpStateType.add("CE");
        adpStateType.add("DF");
        adpStateType.add("ES");
        adpStateType.add("GO");
        adpStateType.add("MA");
        adpStateType.add("MT");
        adpStateType.add("MS");
        adpStateType.add("MG");
        adpStateType.add("PA");
        adpStateType.add("PB");
        adpStateType.add("PR");
        adpStateType.add("PI");
        adpStateType.add("RJ");
        adpStateType.add("RN");
        adpStateType.add("RS");
        adpStateType.add("RO");
        adpStateType.add("RR");
        adpStateType.add("SC");
        adpStateType.add("SP");
        adpStateType.add("SE");
        adpStateType.add("TO");


        //clickListeners
        edtAdressStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdressStreet.setText("");
            }
        });

        edtAdressNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdressNumber.setText("");
            }
        });

        edtAdressComplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdressComplement.setText("");
            }
        });

        edtAdressNeighborhood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdressNeighborhood.setText("");
            }
        });

        edtAdressCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdressCity.setText("");
            }
        });

        //final String state = spnStateType.getSelectedItem().toString();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tudo_certinho = false;
                while(tudo_certinho == false) {
                    verifyName(edtName);
                    verifyCPF(edtCPF);
                    verifyBirth(edtBirth);
                    verifyDDD(edtDDD);
                    verifyPhone(edtPhone);
                    verifyEmail(edtEmail);
                    verifyAdressStreet(edtAdressStreet);
                    verifyAdressNumber(edtAdressNumber);
                    verifyAdressComplement(edtAdressComplement);
                    verifyAdressNeighborhood(edtAdressNeighborhood);
                    verifyAdressCity(edtAdressCity);
                    verifyPassword(edtPassword, edtConfirmPassword);
                    verifyState(spnStateType);

                    if(verifyName(edtName) == true &&
                            verifyCPF(edtCPF) == true &&
                            verifyBirth(edtBirth) == true &&
                            verifyDDD(edtDDD) == true &&
                            verifyPhone(edtPhone) == true &&
                            verifyEmail(edtEmail) == true &&
                            verifyAdressStreet(edtAdressStreet) == true &&
                            verifyAdressNumber(edtAdressNumber) == true &&
                            verifyAdressComplement(edtAdressComplement) == true &&
                            verifyAdressNeighborhood(edtAdressNeighborhood) == true &&
                            verifyAdressCity(edtAdressCity) == true &&
                            verifyPassword(edtPassword, edtConfirmPassword) == true &&
                            verifyState(spnStateType) == true){

                        tudo_certinho = true;

                        final String name = edtName.getText().toString();
                        final String cpf = edtCPF.getText().toString();
                        final String birth = edtBirth.getText().toString();
                        final String ddd = edtDDD.getText().toString();
                        final String phone = edtPhone.getText().toString();
                        final String email = edtEmail.getText().toString();
                        final String adress_street = edtAdressStreet.getText().toString();
                        final String adress_number = edtAdressNumber.getText().toString();
                        final String adress_complement = edtAdressComplement.getText().toString();
                        final String adress_neighborhood = edtAdressNeighborhood.getText().toString();
                        final String adress_city = edtAdressCity.getText().toString();
                        final String adress_state = spnStateType.getSelectedItem().toString();

                        new AsyncRegister().execute(name, cpf, birth, ddd, phone, email, adress_street, adress_number, adress_complement,
                                adress_neighborhood, adress_city, adress_state);


                        /*
                        String json = makeJson(edtName, edtCPF, edtBirth, edtDDD, edtPhone, edtEmail, edtAdressStreet, edtAdressNumber,
                                edtAdressComplement, edtAdressNeighborhood, edtAdressCity, spnStateType, edtPassword);

                        callserver(json);
                        Toast.makeText(getApplicationContext(), json,
                                Toast.LENGTH_LONG).show();*/

                    }else{
                        break;
                    }

                }



            }


        });

    }

    public String makeJson(EditText name, EditText cpf, EditText birth,
                           EditText ddd, EditText phone, EditText email,
                           EditText street, EditText number, EditText complement,
                           EditText nei, EditText city, Spinner state,
                           EditText password) {
        JSONArray jArray = new JSONArray();
        JSONObject jObj = new JSONObject();

        try {
            jObj.put("name", name.getText().toString());
            jObj.put("cpf", cpf.getText().toString());
            jObj.put("birth", birth.getText().toString());
            jObj.put("DDD", ddd.getText().toString());
            jObj.put("phone", phone.getText().toString());
            jObj.put("email", email.getText().toString());
            jObj.put("rua", street.getText().toString());
            jObj.put("number", number.getText().toString());
            jObj.put("complemento", complement.getText().toString());
            jObj.put("bairro", nei.getText().toString());
            jObj.put("cidade", city.getText().toString());
            jObj.put("estado", state.getSelectedItem().toString());
            jObj.put("senha", password.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        jArray.put(jObj);
        return (jObj.toString());
    }

    private class AsyncRegister extends AsyncTask<String, String, Integer> {
        ProgressDialog pdLoading = new ProgressDialog(Activity_register.this);

        String makeJson = makeJson(edtName, edtCPF, edtBirth, edtDDD, edtPhone, edtEmail, edtAdressStreet, edtAdressNumber,
                edtAdressComplement, edtAdressNeighborhood, edtAdressCity, spnStateType, edtPassword);

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
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_register.this);
                                //builder1.setMessage(message);
                                builder1.setMessage(answer);
                                builder1.setCancelable(true);

                                builder1.setNeutralButton(
                                        "OK",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent back_login = new Intent(Activity_register.this, Activity_Login.class);
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


    @SuppressLint("NewApi")
    private void callserver(final String data) {
        new Thread() {
            public void run() {
                //String answer = HttpConnection.getSetDataWeb("http://8ecfbaac.ngrok.io/api/json", data);
                String answer = HttpConnection.getSetDataWeb(FEED_URL, data);
                Log.i("Script", "ANSWER" + answer);
            }
        }.start();
    }



    private boolean verifyName(EditText name) {
        boolean verify;
        if (name.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_name,
                    Toast.LENGTH_LONG).show();
            verify = false;

        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyCPF(EditText cpf) {
        boolean verify;
        if (cpf.getText().length() < 10) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_cpf,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyBirth(EditText birth) {
        boolean verify;
        if (birth.getText().length() <8) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_birth,
                    Toast.LENGTH_LONG).show();
        verify = false;
    }else{
        verify = true;
    }
        return verify;
    }

    private boolean verifyDDD(EditText ddd) {
        boolean verify;
        if (ddd.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_ddd,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyPhone(EditText phone) {
        boolean verify;
        if (phone.getText().length() < 7) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_phone,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyEmail(EditText email) {
        boolean verify;
        if (email.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_email,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyAdressStreet(EditText adressStreet) {
        boolean verify;
        if (adressStreet.getText().length() == 0 || adressStreet.getText().toString().equals("Rua")) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_adressStreet,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyAdressNumber(EditText adressNumber) {
        boolean verify;
        if (adressNumber.getText().length() == 0 || adressNumber.getText().toString().equals("Numero")) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_adressNumber,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyAdressComplement(EditText adressComplement) {
        boolean verify;
        if (adressComplement.getText().equals("Número")) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_adressComplement,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyAdressNeighborhood(EditText neighborhood) {
        boolean verify;
        if (neighborhood.getText().length() == 0 || neighborhood.getText().toString().equals("Bairro")) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_adressNeighborhood,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyAdressCity(EditText adressCity) {
        boolean verify;
        if (adressCity.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_adressCity,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;
    }

    private boolean verifyState(Spinner state){
        boolean verify;
        if (state.getSelectedItem().toString().equals("Estado")){
            Toast.makeText(getApplicationContext(), R.string.message_error_empty_state,
                    Toast.LENGTH_LONG).show();
            verify = false;
        }else{
            verify = true;
        }
        return verify;

    }

    private boolean verifyPassword(EditText pass, EditText confirmPass) {
        boolean verify = false;
        if (pass.getText().toString().equals(confirmPass.getText().toString()) && pass.getText().length() != 0) {
            /*Toast is the pop up message
            Toast.makeText(getApplicationContext(), "Senhas conferem",
                    Toast.LENGTH_LONG).show();*/
            verify = true;
        } else if (pass.getText().length() == 0) {
            //Toast is the pop up message
            Toast.makeText(getApplicationContext(), "Por favor, digite uma senha",
                    Toast.LENGTH_LONG).show();
        } else {
            //Toast is the pop up message
            Toast.makeText(getApplicationContext(), "Senhas não conferem!",
                    Toast.LENGTH_LONG).show();
        }
        return verify;
    }

}

