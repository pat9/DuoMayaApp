package com.example.luisr.duomayaapp;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class InicioActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        drawerLayout=(DrawerLayout) findViewById(R.id.DrawerId);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().hide();

        BottomNavigationView bottomNav = findViewById(R.id.Id_BottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(NavListener);

        Bundle bundle = getIntent().getExtras();
        FragmentPerfilClass fragmentPerfilClass = new FragmentPerfilClass();
        fragmentPerfilClass.setArguments(bundle);

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_ContainerXML,fragmentPerfilClass).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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

}
