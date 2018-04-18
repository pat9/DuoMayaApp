package com.example.luisr.duomayaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Adaptadores.AdapterJuegoAhorcado;
import Clases.ClsJuegoAhorcado;
import Interfaces.CustomItemClickListener;
import AsyncTasks.*;
/**
 * Created by LuisR on 25/02/2018.
 */

public class FragmentJuegosClass extends Fragment implements descargarDatosAsyncTask.interfacedelhilo{

    ArrayList<ClsJuegoAhorcado> ListaAhorcado, ListaAdivina;
    RecyclerView recyclerView, lstAdivina;
    Context context;
    int Lista = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_juegos,container,false);
        ListaAhorcado= new ArrayList<>();
        ListaAdivina = new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.idRecyclerJuegoAhorcado);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        recyclerView.setHasFixedSize(true);

        lstAdivina=(RecyclerView)view.findViewById(R.id.lstAdivina);
        lstAdivina.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        lstAdivina.setHasFixedSize(true);

        GenerarLista();





        return view;

    }

    private void GenerarLista() {
        descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
        obj.delegado = this;
        try {
            obj.execute(new URL("http://aprendermayaws.gearhostpreview.com/AprenderMayaWS.asmx/LeccionesPublicas"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void datosDescagados(String Datos) {
        try {
            JSONArray array = new JSONArray(Datos);
            for(int i = 0; i<array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);
                ListaAhorcado.add(new ClsJuegoAhorcado(object.getInt("CodigoLeccion"), "Ahorcado",object.getString("Nombre"), R.mipmap.ic_launcher));
                ListaAdivina.add(new ClsJuegoAhorcado(object.getInt("CodigoLeccion"), "Adivna",object.getString("Nombre"), R.mipmap.ic_launcher));
            }

            AdapterJuegoAhorcado adap= new AdapterJuegoAhorcado(getContext(), ListaAhorcado, new CustomItemClickListener() {
                @Override
                public void OnItemClick(View v, int Position) {
                    Intent itent = new Intent(getActivity(), AhorcadoActivity.class);
                    itent.putExtra("ID", ListaAhorcado.get(Position).getCodigo());
                    startActivity(itent);
                    getActivity().finish();
                }
            });

            AdapterJuegoAhorcado adap1 = new AdapterJuegoAhorcado(getContext(), ListaAdivina, new CustomItemClickListener() {
                @Override
                public void OnItemClick(View v, int Position) {
                    Intent itent = new Intent(getActivity(), QuizActivity.class);
                    itent.putExtra("ID", ListaAhorcado.get(Position).getCodigo());
                    startActivity(itent);
                    getActivity().finish();
                }
            } );

            recyclerView.setAdapter(adap);
            lstAdivina.setAdapter(adap1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
