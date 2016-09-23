package com.arianasp.projectws;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mycomputer on 22/09/16.
 */
public class HomeActivity extends AppCompatActivity{
    TextView tvGreet,tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        tvGreet = (TextView)findViewById(R.id.tvgreet);
        tvLogout = (TextView)findViewById(R.id.tvlogout);
        SharedPreferences sp1;
        //retrieve valuee shared preference
        sp1 = getSharedPreferences("login", MODE_PRIVATE);
        tvGreet.setText("Welcome " + sp1.getString("email", ""));
    }

    public void logout(View v){
        clearspi();
        Intent i=new Intent();
        i.setClass(this,MainActivity.class);
        startActivity(i);
    }

    public void clearspi(){
        SharedPreferences clearSP1 = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor ed = clearSP1.edit();
        ed.clear();
        ed.commit();
    }
}
