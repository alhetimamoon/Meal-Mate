package mamoonbraiga.MealMate.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mamoonbraiga.poodle_v3.R;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by MamoonBraiga on 2015-11-09.
 * This class handles login/sigup requests
 */
public class LoginActivity extends AppCompatActivity{

    private static final String Tag = "Login Activity";
    private static final int Siguup_request = 0;
    int test;
    private static final String url= "http://www.mealmate.co/api/users/sign_in";
    private SharedPreferences sharedPref;

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
        sharedPref = getSharedPreferences("loginState", Context.MODE_PRIVATE);

        if (sharedPref.getString("isSignedOn", "") != null){
            if (sharedPref.getString("isSignedOn", "").equals("yes")){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
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

    private void login(){
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

        ArrayList<String> params = new ArrayList<>();
        params.add(url);
        params.add(email);
        params.add(password);

        // TODO: Implement your own authentication logic here.
        LoginTask login = null;
        try {
            login = (LoginTask) new LoginTask().execute(params);
            Thread.sleep(500);
            final JSONObject response = login.getResponse();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            try {
                                Log.d("the value ", response.getString("success"));
                                if (response.getBoolean("success") == (Boolean.TRUE))
                                    onLoginSuccess();
                                else
                                    onLoginFailed();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("isSignedOn", "yes");
        editor.apply();
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
    public class LoginTask extends AsyncTask<ArrayList<String>, Void, JSONObject> {
        private static final String TAG = "BackgroundTask";
        private String response;
        JSONObject message;

        @Override
        protected JSONObject doInBackground(ArrayList<String>... params) {
            Response response = null;

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("email", params[0].get(1))
                    .add("password", params[0].get(2))
                    .build();

            Request request = new Request.Builder()
                    .url(params[0].get(0))
                    .post(formBody)
                    .build();

            try {

                response = client.newCall(request).execute();
                message = new JSONObject(response.body().string());
                return message;
            } catch (Exception e) {
                e.printStackTrace();}
            return null;

        }
        public JSONObject getResponse(){
            return this.message;
        }

    }

}