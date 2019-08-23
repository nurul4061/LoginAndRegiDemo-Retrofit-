package capsulestudio.loginandregi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button login,regi;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String email_key = "email";
    public static final String password_key = "password";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        login=(Button) findViewById(R.id.login);
        regi=(Button) findViewById(R.id.regi);

        UserPrefernces();

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);

            }
        });


    }

    private void UserPrefernces()
    {
        String email=sharedpreferences.getString(email_key,"no");
        String pass=sharedpreferences.getString(password_key,"no");

        if(email!="no" && pass!="no")
        {
            Intent i=new Intent(getApplicationContext(),SuccessActivity.class);
            startActivity(i);
        }
    }
}
