package com.arianasp.projectws;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn;
    LoginDBAdapter loginDataBaseAdapter;
    com.rey.material.widget.TextView tvRegis;

    TextView tv_respond, tv_result_api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDBAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get The Refference Of Buttons
        btnSignIn=(Button)findViewById(R.id.homeBtnLogin);
        tvRegis = (com.rey.material.widget.TextView)findViewById(R.id.tv_reg);

        // Set OnClick Listener on SignUp button
        tvRegis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity
                Intent intentRegister=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

//        tv_respond = (TextView)findViewById(R.id.tv_respond);
//        tv_result_api = (TextView)findViewById(R.id.tv_result_api);
//
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://private-bb8c7-tryretrofit.apiary-mock.com/users/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        UserApi user_api = retrofit.create(UserApi.class);
//
//        // // implement interface for get all user
//        Call<Users> call = user_api.getUsers();
//        call.enqueue(new Callback<Users>() {
//
//            @Override
//            public void onResponse(Call<Users> call, Response<Users> response) {
//                int status = response.code();
//                tv_respond.setText(String.valueOf(status));
//                tv_result_api.setText("");
//                //this extract data from retrofit with for() loop
//                for(Users.UserItem user : response.body().getUsers()) {
//                    tv_result_api.append(
//                            "Id = " + String.valueOf(user.getId()) +
//                                    System.getProperty("line.separator") +
//                                    "Email = " + user.getEmail() +
//                                    System.getProperty("line.separator") +
//                                    "Password = " + user.getPassword() +
//                                    System.getProperty("line.separator") +
//                                    "Token Auth = " + user.getToken_auth() +
//                                    System.getProperty("line.separator") +
//                                    "Created at = " + user.getCreated_at() +
//                                    System.getProperty("line.separator") +
//                                    "Updated at = " + user.getUpdated_at() +
//                                    System.getProperty("line.separator") +
//                                    System.getProperty("line.separator")
//                    );
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Users> call, Throwable t) {
//                tv_respond.setText(String.valueOf(t));
//            }
//        });
    }

    // Methos to handleClick Event of Sign In Button
    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_main);
        dialog.setTitle("Login");

        // get the Refferences of views
        final EditText editTextUserEmail=(EditText)dialog.findViewById(R.id.homeEtEmail);
        final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.homeEtPassword);

        Button btnSignIn=(Button)dialog.findViewById(R.id.homeBtnLogin);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User email and Password
                String userEmail=editTextUserEmail.getText().toString();
                String password=editTextPassword.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userEmail);

                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword))
                {
                    Toast.makeText(MainActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Email or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}