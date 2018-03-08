package com.example.luisr.duomayaapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by LuisR on 25/02/2018.
 */

public class FragmentCulturaClass extends Fragment {

    ArrayList<ClsArticulos> listadeArticulos;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    AdapterArticulo adapterArticulo;

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
        String URL="http://aprendermayaws.gearhostpreview.com/AprenderMayaWS.asmx/ListaPublicaciones";

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("Table");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject ParseoOBJ= jsonArray.getJSONObject(i);

                        String Titulo= ParseoOBJ.getString("Nombre");
                        String Descripcion= ParseoOBJ.getString("Correo");
                        String Foto= ParseoOBJ.getString("FotoPerfil");

                        listadeArticulos.add(new ClsArticulos(Titulo,Descripcion,Foto));

                    }

                    adapterArticulo= new AdapterArticulo(getContext(),listadeArticulos);
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


}
