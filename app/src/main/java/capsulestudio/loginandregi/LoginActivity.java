package capsulestudio.loginandregi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog pDialog;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String email_key = "email";
    public static final String password_key = "password";

    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (validate() == false) {
            onLoginFailed();
            return;
        }

        //_loginButton.setEnabled(false);

        loginByServer();
    }

    private void loginByServer()
    {
        pDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_AppBarOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();

        String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);

        Call<MSG> userCall = service.userLogIn(email,password);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();
                Log.d("nurul", "" + response.body().getMessage());


                if(response.body().getSuccess() == 1)
                {
                    Log.d("nurul", "onResponse.getSuccess");

                    String e  = _emailText.getText().toString();
                    String p  = _passwordText.getText().toString();

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(email_key, e);
                    editor.putString(password_key, p);

                    editor.commit();


                    startActivity(new Intent(LoginActivity.this, SuccessActivity.class));
                    Toast.makeText(getApplicationContext(),"Log in Successful",Toast.LENGTH_SHORT).show();

                    finish();
                }else
                {
                    Log.d("nurul", "onResponse.getSuccess.else");
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t)
            {
                Log.d("nurul", "onFailure");
                hidepDialog();
                Log.d("nurul", t.toString());
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }



    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            requestFocus(_emailText);
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Password is empty");
            requestFocus(_passwordText);
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}