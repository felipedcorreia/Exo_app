package correia.felipe.exo_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

                        String json = makeJson(edtName, edtCPF, edtBirth, edtDDD, edtPhone, edtEmail, edtAdressStreet, edtAdressNumber,
                                edtAdressComplement, edtAdressNeighborhood, edtAdressCity, spnStateType, edtPassword);

                /*MakeJson json1 = new MakeJson();
                json1.makeJson(edtName, edtCPF, edtBirth, edtDDD, edtPhone, edtEmail, edtAdressStreet, edtAdressNumber,
                        edtAdressComplement, edtAdressNeighborhood, edtAdressCity, spnStateType, edtPassword);*/

                        callserver(json);
                        Toast.makeText(getApplicationContext(), json,
                                Toast.LENGTH_LONG).show();
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

