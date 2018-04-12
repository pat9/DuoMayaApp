package com.example.luisr.duomayaapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Adaptadores.AdapterRanking;
import AsyncTasks.*;
import Clases.Ranking;

public class RankingActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo{
    RecyclerView lstRanking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        lstRanking = findViewById(R.id.lstRanking);
        lstRanking.setLayoutManager(new LinearLayoutManager(this));
        lstRanking.setHasFixedSize(true);

        GenerarLista();

    }

    public void  GenerarLista()
    {
        descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
        obj.delegado = this;
        try {
            obj.execute(new URL("http://aprendermayaws.gearhostpreview.com/AprenderMayaWS.asmx/GetRanking"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void datosDescagados(String Datos)
    {
        ArrayList<Ranking> Lista = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(Datos);
            for(int i=0; i < array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);
                Ranking ranking = new Ranking();
                ranking.Position = i+1;
                ranking.NickName = object.getString("NickName");
                ranking.Puntos = object.getInt("Puntos");
                ranking.FotoPerfil = object.getString("FotoPerfil");
                Lista.add(ranking);
                AdapterRanking adapterRanking = new AdapterRanking(this, Lista);
                lstRanking.setAdapter(adapterRanking);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
