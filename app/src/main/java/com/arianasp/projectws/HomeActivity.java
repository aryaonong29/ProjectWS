package com.arianasp.projectws;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mycomputer on 22/09/16.
 */
public class HomeActivity extends AppCompatActivity{
    TextView tvLogout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        tvLogout = (TextView)findViewById(R.id.tvlogout);

    }

    public void logout(View v){
        Intent i=new Intent();
        i.setClass(this,MainActivity.class);
        startActivity(i);
    }
}
