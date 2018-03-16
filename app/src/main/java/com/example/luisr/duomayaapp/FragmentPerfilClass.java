package com.example.luisr.duomayaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.UploadRequest;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Clases.Usuario;

import static android.app.Activity.RESULT_OK;
import AsyncTasks.descargarDatosAsyncTask;
/**
 * Created by LuisR on 25/02/2018.
 * Edited by Daniel Pat on 28/02/2018
 */

public class FragmentPerfilClass extends Fragment implements descargarDatosAsyncTask.interfacedelhilo {
    TextView txtNombre;
    ImageView imgPerfil, btnFoto;
    View rootView;
    String Accion = "";
    File FotoFinal;
    Bundle bundle;
    SharedPreferences preferences;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/DuoMaya/";
    private File file = new File(ruta_fotos);
    public  static  final  String MyPrefences = "Usuario";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_perfil,container,false);
        txtNombre = rootView.findViewById(R.id.txtNombreUsu);
        imgPerfil = rootView.findViewById(R.id.imgperfil);
        btnFoto = rootView.findViewById(R.id.btnFoto);
        bundle = getArguments();

        if(bundle != null)
        {
            preferences = getActivity().getSharedPreferences(MyPrefences, Context.MODE_PRIVATE);
            Usuario usuario = (Usuario)bundle.get("Usuario");
            txtNombre.setText(usuario.Nombre + " " + usuario.Apellido +" ("+usuario.NickName+")");
            Picasso.with(getActivity()).load(usuario.FotoPerfil).into(imgPerfil);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Codigo", usuario.Codigo);
            editor.commit();

        }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  CharSequence[] arreglo = {"Camara", "Galeria"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
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
        });


        return rootView;

    }

    public static FragmentPerfilClass newInstance() {

        Bundle args = new Bundle();
        FragmentPerfilClass fragment = new FragmentPerfilClass();
        fragment.setArguments(args);
        return fragment;
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
                            String URLRESULTADO =  resultData.get("url").toString();
                            descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
                            obj.delegado = FragmentPerfilClass.this;
                            try {
                                obj.execute(new URL("http://aprendermayaws.gear.host/AprenderMayaWS.asmx/CambiarImagen?URL="+URLRESULTADO+"&CodigoUsuario="+preferences.getInt("Codigo",0)));
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Foto", URLRESULTADO);
                                editor.commit();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
                    is = getActivity().getContentResolver().openInputStream(selectedImage);
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
                            String URLRESULTADO =  resultData.get("url").toString();
                            descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
                            obj.delegado = FragmentPerfilClass.this;
                            try {
                                obj.execute(new URL("http://aprendermayaws.gear.host/AprenderMayaWS.asmx/CambiarImagen?URL="+URLRESULTADO+"&CodigoUsuario="+preferences.getInt("Codigo",0)));
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Foto", URLRESULTADO);
                                editor.commit();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
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
                Toast.makeText(getActivity(), "Salio de la galeria", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void datosDescagados(String Datos) {

    }
}
