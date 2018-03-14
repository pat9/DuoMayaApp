package com.example.luisr.duomayaapp;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.cloudinary.android.MediaManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Clases.ClsArticulos;
import Interfaces.IComArticulos;

public class InicioActivity extends AppCompatActivity implements IComArticulos {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Fragmentdetalle_cultura fragmentdetalle_cultura;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/DouMaya/";

    private File file = new File(ruta_fotos);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        MediaManager.init(this,Configuracion());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().hide();

        file.mkdirs();



        BottomNavigationView bottomNav = findViewById(R.id.Id_BottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(NavListener);

        Bundle bundle = getIntent().getExtras();
        FragmentPerfilClass fragmentPerfilClass = new FragmentPerfilClass();
        fragmentPerfilClass.setArguments(bundle);



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




    }

    public static Map Configuracion()
    {
        Map Config = new HashMap();
        Config.put("cloud_name", "pat9");
        Config.put("api_key", "481118882942431");
        Config.put("api_secret", "ZiGf8m8U772_wHl-Oa0VMvymB4o");
        return Config;
    }
}
