package com.example.luisr.duomayaapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import AsyncTasks.descargarDatosAsyncTask;
public class RegistroActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo {
    EditText txtUsuario, txtPassword, txtEmail, txtNombre, txtApellido;
    Button btnEntrar;
    ImageView imgPerfil;
    private String Accion, URLRESULTADO;
    private File FotoFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtPassword = findViewById(R.id.txtPassword);
        txtApellido = findViewById(R.id.txtApellido);
        txtEmail = findViewById(R.id.txtEmail);
        txtNombre = findViewById(R.id.txtNombre);
        txtUsuario = findViewById(R.id.txtUsuario);
        btnEntrar = findViewById(R.id.btnEntrar);
        imgPerfil = findViewById(R.id.imgperfil);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
                obj.delegado = RegistroActivity.this;
                try
                {
                    obj.execute(new URL("http://aprendermayaws.gearhostpreview.com/AprenderMayaWS.asmx/RegistroUsuario?NickName="+txtUsuario.getText().toString()+"&Password="+txtPassword.getText().toString()+"&FotoPerfil="+URLRESULTADO+"&Correo="+txtEmail.getText().toString()+"&Nombre="+txtNombre.getText().toString()+"&Apellido="+txtApellido.getText().toString()+""));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirMenuFoto();
            }
        });

    }



    public void AbrirMenuFoto()
    {
        final  CharSequence[] arreglo = {"Camara", "Galeria"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Selecciona");
        builder.setSingleChoiceItems(arreglo, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0)
                {
                    Accion = "CAMARA";
                    dispatchTakePictureIntent();
                }
                else
                {
                    Accion ="GALERIA";
                    SelectGaleria();
                }
                dialog.cancel();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            long captureTime = System.currentTimeMillis();
            String photoPath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/Point" + captureTime + ".jpg";
            try {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                FotoFinal = new File(photoPath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FotoFinal));
                startActivityForResult(Intent.createChooser(intent, "Capture una foto"), 1);
            } catch (Exception e) {

            }
        }
    }

    private static final  int SELECT_FILE  =1;
    protected void SelectGaleria()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        if (requestcode == REQUEST_IMAGE_CAPTURE && resultcode == RESULT_OK && Accion =="CAMARA") {
            Bundle extras = data.getExtras();

            if(extras != null)
            {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Uri img = Uri.fromFile(FotoFinal);
                imgPerfil.setImageBitmap(imageBitmap);
                String RequestID = MediaManager.get().upload(img).callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {

                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {

                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        URLRESULTADO =  resultData.get("url").toString();

                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }
                }).dispatch();

            }
        }
        else
        {
            if(resultcode == RESULT_OK && Accion =="GALERIA")
            {
                Uri selectedImage = data.getData();
                InputStream is;
                try
                {
                    is = getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    String resID = MediaManager.get().upload(selectedImage).callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {

                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            URLRESULTADO =  resultData.get("url").toString();
                         }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }
                    }).dispatch();
                    imgPerfil.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    System.out.print("Ups algo salio mal");
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(this, "Salio de la galeria", Toast.LENGTH_SHORT).show();
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
