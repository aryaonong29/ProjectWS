package com.arianasp.projectws;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class MainActivity extends AppCompatActivity {

    TextView tv_reg;

    EditText homeEtEmail, homeEtPassword;

    Button homeBtnLogin;
    boolean doubleBackToExitPressedOnce = false, login;
    AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_reg = (TextView)findViewById(R.id.tv_reg);
        homeEtEmail=(EditText)findViewById(R.id.homeEtEmail);
        homeEtPassword=(EditText)findViewById(R.id.homeEtPassword);
        mAwesomeValidation.addValidation(MainActivity.this, R.id.homeEtEmail, Patterns.EMAIL_ADDRESS,R.string.invalidEmail);
        homeBtnLogin=(Button)findViewById(R.id.homeBtnLogin);
        homeBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAwesomeValidation.validate()) {
                    homeEtEmail.requestFocus();
                } else {
                    getApi();
                }
            }
        });
    }

    public void registerAccount(View v){
        Intent i=new Intent();
        i.setClass(this,RegisterActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void getApi(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-f6513-login312.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserApi user_api = retrofit.create(UserApi.class);

        // // implement interface for get all user
        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
//                int status = response.code();
//                tv_reg.setText(String.valueOf(status));
                Log.e("CEK", response.body().getUsers().toArray().toString());
                //this extract data from retrofit with for() loop
                for (Users.UserItem user : response.body().getUsers()) {
                    Log.e("CEK", String.valueOf(user.getEmail().toString().equals(homeEtEmail.getText().toString())));
                    Log.e("CEK", user.getEmail().toString());
                    Log.e("CEK", homeEtEmail.getText().toString());
                    login = false;
                    if (user.getEmail().toString().equals(homeEtEmail.getText().toString()) &&
                            user.getPassword().toString().equals(homeEtPassword.getText().toString())) {
                        login = true;
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                tv_reg.setText(String.valueOf(t));
            }
        });
    }

}