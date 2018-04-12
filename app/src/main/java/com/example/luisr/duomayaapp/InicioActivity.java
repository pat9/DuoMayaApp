package com.example.luisr.duomayaapp;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.cloudinary.android.MediaManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Clases.ClsArticulos;
import Clases.Usuario;
import Interfaces.IComArticulos;

public class InicioActivity extends AppCompatActivity implements IComArticulos {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Fragmentdetalle_cultura fragmentdetalle_cultura;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/DouMaya/";
    private SharedPreferences preferences;
    public  static  final  String MyPrefences = "Usuario";

    private File file = new File(ruta_fotos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().hide();

        file.mkdirs();



        BottomNavigationView bottomNav = findViewById(R.id.Id_BottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(NavListener);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            Usuario usuario = (Usuario)bundle.get("Usuario");
            preferences = this.getSharedPreferences(MyPrefences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Codigo", usuario.Codigo);
            editor.putString("Nombre", usuario.Nombre);
            editor.putString("Apellido", usuario.Apellido);
            editor.putString("Usuario", usuario.NickName);
            editor.putString("Foto", usuario.FotoPerfil);
            editor.putString("correo", usuario.Correo);
            editor.putString("password", usuario.Password);
            editor.commit();
        }
        FragmentPerfilClass fragmentPerfilClass = new FragmentPerfilClass();
        fragmentPerfilClass.setArguments(bundle);

        Bundle bundle1 = getIntent().getExtras();


        if (savedInstanceState==null){
           getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_ContainerXML,fragmentPerfilClass).commit();
        }



    }


    private BottomNavigationView.OnNavigationItemSelectedListener NavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.support.v4.app.Fragment FragmentSelec=null;

            Bundle bundle = getIntent().getExtras();

            switch (item.getItemId()){
                case R.id.IdNav_casa:
                    FragmentSelec= new FragmentPerfilClass();
                    break;
                case R.id.IdNav_Cultura:
                    FragmentSelec= new FragmentCulturaClass();
                    break;
                case R.id.IdNav_Info:
                    FragmentSelec= new FragmentInfoClass();
                    break;
                case R.id.IdNav_Juego:
                    FragmentSelec= new FragmentJuegosClass();
                    break;
                case R.id.IdNav_Diccioanrio:
                    FragmentSelec= new FragmentDiccionarioClass();
                    break;
            }
            FragmentSelec.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_ContainerXML,FragmentSelec).commit();

            return true;
        }
    };


    @Override
    public void EnviarDatosArticulosDetalle(ClsArticulos clsArticulos) {
        fragmentdetalle_cultura = new Fragmentdetalle_cultura();

        //Enviamos el Objeto Mediante un Bundle  para poder cacharlo en el  fragment de destino
        Bundle bundle = new Bundle();
        bundle.putSerializable("Objeto",clsArticulos);
        fragmentdetalle_cultura.setArguments(bundle);

        //Cargamos el Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Fragment_ContainerXML,fragmentdetalle_cultura).addToBackStack(null).commit();

        Intent intent= new Intent(this,DetalleCulturaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir");
        builder.setMessage("¿Desea salir de la aplicación?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

}

