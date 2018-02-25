package com.example.luisr.duomayaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import AsyncTasks.descargarDatosAsyncTask;

public class actionLoginActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo {
    EditText txtUsuario, txtPassword;
    Button btnEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_login);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
                obj.delegado = actionLoginActivity.this;
                try
                {
                    obj.execute(new URL("http://aprendermayaws.gear.host/AprenderMayaWS.asmx/Login?Usuario="+txtUsuario.getText().toString()+"&Password="+txtPassword.getText().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void datosDescagados(String Datos) {
        try
        {
            JSONObject Objeto = new JSONObject(Datos);
            if(Objeto.getString("NickName") == "null" )
            {
                Toast.makeText(this, "Error en el login", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Bienvenido " + Objeto.getString("NickName"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(actionLoginActivity.this,InicioActivity.class);
                startActivity(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error en el login", Toast.LENGTH_SHORT).show();
        }
    }
}
