package com.example.luisr.duomayaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import AsyncTasks.*;
import Clases.Contenido;

public class LeccionActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo {
    TextView txtPalabra;
    ImageView imgCont;
    ArrayList<Contenido> Palabras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leccion);

        txtPalabra = findViewById(R.id.txtPalabra);
        imgCont = findViewById(R.id.imgCont);
        Palabras = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            int CodigoLeccion = bundle.getInt("CodigoLeccion");
            LlenarPalabras(CodigoLeccion);
        }
    }

    public void LlenarPalabras(int ID)
    {
        descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
        obj.delegado = this;
        try {
            obj.execute(new URL("http://aprendermayaws.gearhostpreview.com/AprenderMayaWS.asmx/ListaPalabrasLeccion?ID="+ID));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void datosDescagados(String Datos) {
        try {
            JSONArray array = new JSONArray(Datos);
            for(int i=0; i<array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);
                Contenido contenido = new Contenido();
                contenido.PalabraMaya = object.getString("PalabraMaya");
                contenido.PalabraEsp = object.getString("PalabraEs");
                contenido.ImagenCont = object.getString("ImagenCont");
                Palabras.add(contenido);
            }
            txtPalabra.setText(Palabras.get(0).PalabraMaya);
            Picasso.with(this).load(Palabras.get(0).ImagenCont).into(imgCont);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
