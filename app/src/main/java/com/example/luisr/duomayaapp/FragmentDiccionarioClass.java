package com.example.luisr.duomayaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Adaptadores.AdapterLeccion;
import AsyncTasks.*;
import Clases.Leccion;


/**
 * Created by LuisR on 25/02/2018.
 */

public class FragmentDiccionarioClass extends Fragment implements descargarDatosAsyncTask.interfacedelhilo, ConsultarDatosAsyncTask.interfacedelhilo  {
    RecyclerView listaLecciones;
    View rootView;
    int Accion = 0, LeccionesActivas = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_dicc,container,false);
        listaLecciones = rootView.findViewById(R.id.lstLecciones);
        listaLecciones.setLayoutManager(new LinearLayoutManager(getContext()));
        listaLecciones.setHasFixedSize(true);
        LlenarLista();

        return  rootView;
    }

    public void LlenarLista()
    {
        Accion = 1;
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
        if(Accion == 1)
        {
            LlenarLecciones(Datos);
        }

    }

    public void LlenarLecciones(String Datos)
    {
        final ArrayList<Leccion> Lecciones =  new ArrayList<Leccion>();
        try {
            JSONArray jsonArray = new JSONArray(Datos);
            for(int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                Leccion leccion = new Leccion();
                leccion.CodigoLeccion = object.getInt("CodigoLeccion");
                leccion.Nombre = object.getString("Nombre");
                leccion.Nivel = object.getInt("CodigoNivel");
                leccion.Imagen = object.getString("ImagenLeccion");
                leccion.Decripcion = object.getString("Descripcion");
                Lecciones.add(leccion);
            }
            LeccionesActivas = Lecciones.size();
            AdapterLeccion adapterLeccion = new AdapterLeccion(getContext(), Lecciones);
            adapterLeccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LeccionActivity.class);
                    intent.putExtra("CodigoLeccion", Lecciones.get(listaLecciones.getChildAdapterPosition(v)).CodigoLeccion);
                    startActivity(intent);
                }
            });
            listaLecciones.setAdapter(adapterLeccion);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        ConsultarDatosAsyncTask obj = new ConsultarDatosAsyncTask();
                        obj.delegado = FragmentDiccionarioClass.this;
                        try {
                            obj.execute(new URL("http://aprendermayaws.gear.host/AprenderMayaWS.asmx/NumeroLeccionesActivas"));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        };
        timer.schedule(task,0, 2000);

    }

    public void ActualizarLista(int Numero)
    {
        if(Numero != LeccionesActivas)
        {
            LlenarLista();
        }
    }


    @Override
    public void Respuesta(String Datos) {
        int Numeros = Integer.parseInt(Datos);
        ActualizarLista(Numeros);

    }
}
