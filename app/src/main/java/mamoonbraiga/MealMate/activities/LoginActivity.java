package mamoonbraiga.MealMate.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-11-09.
 * This class handles login/sigup requests
 */
public class LoginActivity extends AppCompatActivity{

    private static final String Tag = "Login Activity";
    private static final int Siguup_request = 0;

    @InjectView(R.id.input_email)
    EditText email_text;
    @InjectView(R.id.input_password)
    EditText password_text;
    @InjectView(R.id.btn_login)
    Button login_button;
    @InjectView(R.id.link_signup)
    TextView signup_link;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                signup();
            }
        });
    }

    private void signup() {

    }

    private void login() {

    }

}
