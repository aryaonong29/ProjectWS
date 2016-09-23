package com.arianasp.projectws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

/**
 * Created by mycomputer on 21/09/16.
 */

public class RegisterActivity extends Activity
{
//    public static final String MyPREFERENCES = "MyPrefs" ;
//    public static final String Email = "emailKey";
//    public static final String Password = "pwdKey";
    AwesomeValidation valid = new AwesomeValidation(BASIC);
    TextView tv_reg,tvBack;
    EditText editTextUserName,editTextUserEmail,editTextPassword,editTextConfirmPassword;
    Button btnRegister;
    String userName,email,password,confirmPassword;
    SharedPreferences sp;
    ProgressDialog dialogReg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        valid.addValidation(RegisterActivity.this, R.id.regEtEmail, Patterns.EMAIL_ADDRESS, R.string.invalidEmail);
        valid.addValidation(RegisterActivity.this, R.id.regEtName, "[a-zA-Z\\s]+", R.string.invalidName);
        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.regEtName);
        editTextUserEmail =(EditText)findViewById(R.id.regEtEmail);
        editTextPassword=(EditText)findViewById(R.id.regEtPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.regEtConfPassword);
        tvBack=(TextView)findViewById(R.id.tv_back);

        btnRegister=(Button)findViewById(R.id.btnReg);
//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialogReg = new ProgressDialog(RegisterActivity.this);
                dialogReg.setTitle("Registration dulu bro");
                dialogReg.setMessage("Loading ...");
                dialogReg.setProgress(0);
                userName=editTextUserName.getText().toString();
                email=editTextUserEmail.getText().toString();
                password=editTextPassword.getText().toString();
                confirmPassword=editTextConfirmPassword.getText().toString();

                // check if any of the fields are vaccant
                if (!valid.validate()) {
                    editTextUserEmail.requestFocus();
//                } else if (!validatePass1(password)) {
//                    editTextPassword.setError("Invalid Password");
//                    editTextPassword.requestFocus();
                } else if (!confirmPassword.equals(password)) {
                    editTextConfirmPassword.setError("Invalid Password Confirmation");
                    editTextConfirmPassword.requestFocus();
                } else {
                    Toast.makeText(RegisterActivity.this, "Input Success", Toast.LENGTH_LONG).show();
                    dialogReg.show();
                    getApi();
                }
            }
        });
    }

    public void backHome(View v){
        Intent i=new Intent();
        i.setClass(this,MainActivity.class);
        startActivity(i);
    }

    protected boolean validatePass1(String pass1) {
        if (pass1 != null && pass1.length() > 4) {
            return true;
        } else {
            return false;
        }
    }

    private void getApi(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-f6513-login312.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final UserApi user_api = retrofit.create(UserApi.class);

        // // implement interface for get all user
        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int status = response.code();
                tv_reg.setText("");
                tv_reg.setText(String.valueOf(status));

                //this extract data from retrofit with for() loop
                for (Users.UserItem user : response.body().getUsers()) {
                    if (user.getEmail().toString().equals(email)) {
                        Toast.makeText(RegisterActivity.this, "email already in use", Toast.LENGTH_LONG).show();
                        break;
                    }
                    else{
                        postApi();
                        break;
                    }
//
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                dialogReg.dismiss();
                tv_reg.setText(String.valueOf(t));
            }
        });
    }

    private void postApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-f6513-login312.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final UserApi user_api = retrofit.create(UserApi.class);

        // implement interface for add user
        User userPost = new User(userName,email,password,confirmPassword);
        Gson gson2 = new Gson();
        String json = gson2.toJson(userPost);
        Log.e("CEKIDOT", json);
        Call<User> call2 = user_api.saveUser(userPost);

        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int status2 = response.code();
                dialogReg.dismiss();
//                tv_reg.setText(String.valueOf(status2));
                Toast.makeText(RegisterActivity.this, "registration success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call2, Throwable t) {
                dialogReg.dismiss();
                tv_reg.setText(String.valueOf(t));
            }
        });
    }


}
