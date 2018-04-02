package com.example.luisr.duomayaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import Clases.Usuario;

public class actionLoginActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo {
    EditText txtUsuario, txtPassword;
    Button btnEntrar;
    ProgressDialog progressDialog;

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
                IniciarSesion();

            }
        });


    }

    public void IniciarSesion()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Iniciando...");
        progressDialog.setMessage("Espere porfis");
        progressDialog.show();
        descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
        obj.delegado = actionLoginActivity.this;
        try
        {
            obj.execute(new URL("http://aprendermayaws.gear.host/AprenderMayaWS.asmx/Login?Usuario="+txtUsuario.getText().toString()+"&Password="+txtPassword.getText().toString()));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void datosDescagados(String Datos) {
        try
        {
            JSONObject Objeto = new JSONObject(Datos);
            if(Objeto.getString("NickName") == "null" )
            {
                progressDialog.hide();
                Toast.makeText(this, "Error en el login", Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog.hide();
                Usuario usuario = new Usuario();
                usuario.Codigo = Objeto.getInt("Codigo");
                usuario.NickName = Objeto.getString("NickName");
                usuario.Password = Objeto.getString("Contraseña");
                usuario.FotoPerfil = Objeto.getString("FotoPerfil");
                usuario.Correo = Objeto.getString("Correo");
                usuario.Nombre = Objeto.getString("Nombre");
                usuario.Apellido = Objeto.getString("Apellido");
                usuario.Tipo = Objeto.getInt("IDTipo");
                Toast.makeText(this, "Bienvenido " + Objeto.getString("NickName"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(actionLoginActivity.this,InicioActivity.class);
                intent.putExtra("Usuario", usuario);
                startActivity(intent);
                finish();
            }

        } catch (JSONException e) {
            progressDialog.hide();
            e.printStackTrace();
            Toast.makeText(this, "Error en el login", Toast.LENGTH_SHORT).show();
        }
    }
}
