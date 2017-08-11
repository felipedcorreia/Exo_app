package correia.felipe.exo_app;

import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Felipe on 11/08/2017.
 */

public class MakeJson{

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

}
