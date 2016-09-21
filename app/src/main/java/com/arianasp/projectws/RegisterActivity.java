package com.arianasp.projectws;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mycomputer on 21/09/16.
 */

public class RegisterActivity extends Activity
{
    EditText editTextUserName,editTextUserEmail,editTextPassword,editTextConfirmPassword;
    Button btnRegister;

    LoginDBAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDBAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.regEtName);
        editTextUserEmail =(EditText)findViewById(R.id.regEtEmail);
        editTextPassword=(EditText)findViewById(R.id.regEtPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.regEtConfPassword);

        btnRegister=(Button)findViewById(R.id.btnReg);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName=editTextUserName.getText().toString();
                String email=editTextUserEmail.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();

                // check if any of the fields are vaccant
                if(userName.equals("")|| email.equals("")|| password.equals("")||confirmPassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(email, password);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    Intent ihome=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(ihome);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
}
