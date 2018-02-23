package com.example.luisr.duomayaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button BtnLogin=(Button) findViewById(R.id.btnLogin);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirActivityLogin();
            }
        });

    }

    private void AbrirActivityLogin() {

        Intent intent= new Intent(LoginActivity.this,actionLoginActivity.class);
        startActivity(intent);
    }
}
