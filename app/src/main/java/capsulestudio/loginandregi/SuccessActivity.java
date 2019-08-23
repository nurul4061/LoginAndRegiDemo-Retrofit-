package capsulestudio.loginandregi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessActivity extends AppCompatActivity
{

    Button logout,btnEdit;
    EditText editEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        logout=(Button) findViewById(R.id.btnLogout);
        btnEdit=(Button) findViewById(R.id.btnEdit);
        editEmail=(EditText) findViewById(R.id.editEmail);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=editEmail.getText().toString();


                getData(str);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(SuccessActivity.this, MainActivity.class));
            }
        });
    }


    private void getData(String str)
    {
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<GetUserName> userCall = service.userGetIn(str);

        userCall.enqueue(new Callback<GetUserName>() {
            @Override
            public void onResponse(Call<GetUserName> call, Response<GetUserName> response)
            {
                Log.d("nurul","onResponse");
                if(response.body().getStatus()==1)
                {
                    Log.d("nurul",String.valueOf(response.body().getId()));
                }

            }

            @Override
            public void onFailure(Call<GetUserName> call, Throwable t)
            {
                Log.d("nurul","OnFailure: " + t.getMessage());

            }
        });


    }
}
