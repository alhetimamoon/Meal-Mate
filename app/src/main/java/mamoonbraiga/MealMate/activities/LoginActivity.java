package mamoonbraiga.MealMate.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        Log.d(Tag, "Signup");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        signup_link.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = email_text.getText().toString();
        final String password = password_text.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (email.equals("mamoon.alheti@gmail.com"))
                            onLoginSuccess();
                        else
                            onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Siguup_request) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void onLoginSuccess() {
        login_button.setEnabled(true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login_button.setEnabled(true);
    }




    public boolean validate() {
        boolean valid = true;

        String email = email_text.getText().toString();
        String password = password_text.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_text.setError("enter a valid email address");
            valid = false;
        } else {
            email_text.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_text.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password_text.setError(null);
        }

        return valid;
    }

}
