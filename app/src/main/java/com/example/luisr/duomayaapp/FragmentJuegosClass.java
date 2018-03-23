package com.example.luisr.duomayaapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Adaptadores.AdapterJuegoAhorcado;
import Clases.ClsJuegoAhorcado;

/**
 * Created by LuisR on 25/02/2018.
 */

public class FragmentJuegosClass extends Fragment {

    ArrayList<ClsJuegoAhorcado> ListaAhorcado;
    RecyclerView recyclerView;
    Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_juegos,container,false);
        ListaAhorcado= new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.idRecyclerJuegoAhorcado);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        recyclerView.setHasFixedSize(true);

        GenerarLista();

        AdapterJuegoAhorcado adap= new AdapterJuegoAhorcado(getContext(),ListaAhorcado);
        recyclerView.setAdapter(adap);


        return view;

    }

    private void GenerarLista() {
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Frutas",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Verduras",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Animales",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Figuras",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Perrsonajes",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Culturales",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Frutas",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Frutas",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Frutas",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Frutas",R.mipmap.ic_launcher));
        ListaAhorcado.add(new ClsJuegoAhorcado("Ahorcado","Frutas",R.mipmap.ic_launcher));

    }
}
