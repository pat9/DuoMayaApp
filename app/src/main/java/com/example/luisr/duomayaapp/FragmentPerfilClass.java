package com.example.luisr.duomayaapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import Clases.Usuario;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LuisR on 25/02/2018.
 * Edited by Daniel Pat on 28/02/2018
 */

public class FragmentPerfilClass extends Fragment {
    TextView txtNombre;
    ImageView imgPerfil, btnFoto;
    View rootView;
    String Accion = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_perfil,container,false);
        txtNombre = rootView.findViewById(R.id.txtNombreUsu);
        imgPerfil = rootView.findViewById(R.id.imgperfil);
        btnFoto = rootView.findViewById(R.id.btnFoto);
        Bundle bundle = getArguments();

        if(bundle != null)
        {
            Usuario usuario = (Usuario)bundle.get("Usuario");
            txtNombre.setText(usuario.Nombre + " " + usuario.Apellido +" ("+usuario.NickName+")");
            Picasso.with(getActivity()).load(usuario.FotoPerfil).into(imgPerfil);
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
                imgPerfil.setImageBitmap(imageBitmap);
            }
        }
        else
        {
            if(resultcode == RESULT_OK && Accion =="GALERIA")
            {
                Uri selectedImage = data.getData();
                Log.d("uri", selectedImage.toString());
                InputStream is;
                try
                {
                    is = getActivity().getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
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


}
