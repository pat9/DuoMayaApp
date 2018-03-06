package com.example.luisr.duomayaapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import Adaptadores.AdapterArticulo;
import Clases.ClsArticulos;

/**
 * Created by LuisR on 25/02/2018.
 */

public class FragmentCulturaClass extends Fragment {

    ArrayList<ClsArticulos> listadeArticulos;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_cult,container,false);
        listadeArticulos= new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        GenerarLista();

        AdapterArticulo adapterArticulo= new AdapterArticulo(listadeArticulos);
        recyclerView.setAdapter(adapterArticulo);

        adapterArticulo.setOnclickLisener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Articulo se leccionado "+ listadeArticulos.get(recyclerView.getChildAdapterPosition(view)).getTitulo(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    private void GenerarLista() {

        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));   listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));   listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));
        listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));   listadeArticulos.add(new ClsArticulos("Juego Maya","Esta es una preba del juego maya",R.mipmap.ic_launcher_round));


    }
}
