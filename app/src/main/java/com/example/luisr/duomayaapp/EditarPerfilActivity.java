package com.example.luisr.duomayaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.net.URL;

import AsyncTasks.*;

public class EditarPerfilActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo{

    EditText txtNombre, txtApellido, txtCorreo, txtContraseña;
    Button btnGuardar;
    public  static  final  String MyPrefences = "Usuario";
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtEmail);
        txtContraseña = findViewById(R.id.txtContraseña);
        LlenarText();
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarDatos();
            }
        });
    }

    public void LlenarText()
    {
        preferences = getSharedPreferences(MyPrefences, Context.MODE_PRIVATE);
        txtNombre.setText(preferences.getString("Nombre",""));
        txtApellido.setText(preferences.getString("Apellido",""));
        txtCorreo.setText(preferences.getString("correo",""));
        txtContraseña.setText(preferences.getString("password",""));
    }

    public void GuardarDatos()
    {

        if(ValidarText())
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Guardando...");
            progressDialog.setMessage("Espere por favor.");
            progressDialog.show();
            descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
            obj.delegado = this;
            try {
                obj.execute(new URL("http://aprendermayaws.gear.host/AprenderMayaWS.asmx/ModificarPerfil?Codigo="+preferences.getInt("Codigo",0)+"&Nombre="+txtNombre.getText().toString()+"&Apellido="+txtApellido.getText().toString()+"&Correo="+txtCorreo.getText().toString()+"&Contra="+txtContraseña.getText().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else
        {


        }
    }

    public boolean ValidarText()
    {
        if (txtNombre.getText().length() == 0)
        {
            return false;
        }
        else if (txtApellido.getText().length() == 0)
        {
            return false;
        }
        else if(txtCorreo.getText().length() == 0)
        {
            return false;
        }
        else if (txtContraseña.getText().length() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    @Override
    public void datosDescagados(String Datos) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo", txtCorreo.getText().toString());
        editor.putString("password", txtContraseña.getText().toString());
        editor.putString("Nombre", txtNombre.getText().toString());
        editor.putString("Apellido", txtApellido.getText().toString());
        progressDialog.hide();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guardado");
        builder.setMessage("Datos guardados");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditarPerfilActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
