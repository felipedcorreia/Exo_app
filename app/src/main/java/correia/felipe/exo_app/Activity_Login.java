package correia.felipe.exo_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Felipe on 04/08/2017.
 */

public class Activity_Login extends AppCompatActivity {


    EditText editEmail, editPassword;
    Button btnLogin;
    TextView txtRegister, txtRegisterLink;

    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText)findViewById(R.id.edt_email);
        editPassword = (EditText)findViewById(R.id.edt_password);

        btnLogin = (Button)findViewById(R.id.btn_login);

        txtRegister = (TextView)findViewById(R.id.txt_register);
        txtRegisterLink = (TextView)findViewById(R.id.txt_register_link);


        txtRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent register = new Intent(Activity_Login.this, Activity_register.class);
                startActivity(register);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}