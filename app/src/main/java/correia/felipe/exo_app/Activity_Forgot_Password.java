package correia.felipe.exo_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Felipe on 06/10/2017.
 */

public class Activity_Forgot_Password extends AppCompatActivity {

    EditText edt_email;
    Button btn_enviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edt_email = (EditText) findViewById(R.id.edt_email_forgot_password);
        btn_enviar = (Button) findViewById(R.id.btn_forgot_password);

    }
}
