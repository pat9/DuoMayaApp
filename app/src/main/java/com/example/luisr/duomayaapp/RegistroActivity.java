package com.example.luisr.duomayaapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import AsyncTasks.descargarDatosAsyncTask;
public class RegistroActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo {
    EditText txtUsuario, txtPassword, txtEmail, txtNombre, txtApellido;
    Button btnEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        PermisoCamara();
        txtPassword = findViewById(R.id.txtPassword);
        txtApellido = findViewById(R.id.txtApellido);
        txtEmail = findViewById(R.id.txtEmail);
        txtNombre = findViewById(R.id.txtNombre);
        txtUsuario = findViewById(R.id.txtUsuario);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
                obj.delegado = RegistroActivity.this;
                try
                {
                    obj.execute(new URL("http://aprendermayaws.gearhostpreview.com/AprenderMayaWS.asmx/RegistroUsuario?NickName="+txtUsuario.getText().toString()+"&Password="+txtPassword.getText().toString()+"&FotoPerfil=Foto"+"&Correo="+txtEmail.getText().toString()+"&Nombre="+txtNombre.getText().toString()+"&Apellido="+txtApellido.getText().toString()+""));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public void PermisoCamara()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No se necesita dar una explicación al usuario, sólo pedimos el permiso.
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, 0);
                // MY_PERMISSIONS_REQUEST_CAMARA es una constante definida en la app. El método callback obtiene el resultado de la petición.
            }
        }
    }


    @Override
    public void datosDescagados(String Datos) {
        if(Integer.parseInt(Datos) == 1)
        {
            Toast.makeText(this,"Registro completo", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
