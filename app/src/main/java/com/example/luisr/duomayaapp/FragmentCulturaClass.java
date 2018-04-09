package com.example.luisr.duomayaapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adaptadores.AdapterArticulo;
import Clases.ClsArticulos;
import Interfaces.IComArticulos;

/**
 * Created by LuisR on 25/02/2018.
 */

public class FragmentCulturaClass extends Fragment {

    ArrayList<ClsArticulos> listadeArticulos;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    AdapterArticulo adapterArticulo;
    Activity activity;

    IComArticulos iComArticulos;

    //Variables

    String Titulo;
    String Descripcion;
    String Foto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_cult,container,false);
        listadeArticulos= new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        requestQueue= Volley.newRequestQueue(getContext());

        GenerarListaJson();

        return view;

    }

    private void GenerarListaJson() {
        String URL="http://aprendermayaws.gear.host/AprenderMayaWS.asmx/ListaCultura";

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("Table");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject ParseoOBJ= jsonArray.getJSONObject(i);
                         Titulo= ParseoOBJ.getString("Titulo");
                         Descripcion= ParseoOBJ.getString("Contenido");
                        Foto= ParseoOBJ.getString("ImagenCult");

                        listadeArticulos.add(new ClsArticulos(Titulo,Descripcion,Foto));

                    }

                    adapterArticulo= new AdapterArticulo(getContext(),listadeArticulos);

                    adapterArticulo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           // iComArticulos.EnviarDatosArticulosDetalle(listadeArticulos.get(recyclerView.getChildAdapterPosition(view)));

                            Intent intent = new Intent(getActivity(), DetalleCulturaActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Img",listadeArticulos.get(recyclerView.getChildAdapterPosition(view)).getFotoArticulo());
                            bundle.putString("Descrip",listadeArticulos.get(recyclerView.getChildAdapterPosition(view)).getDescripcion());
                            bundle.putString("TituloC",listadeArticulos.get(recyclerView.getChildAdapterPosition(view)).getTitulo());

                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                    recyclerView.setAdapter(adapterArticulo);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        requestQueue.add(objectRequest);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            this.activity=(Activity) context;
            iComArticulos = (IComArticulos) context;
        }
    }
}
