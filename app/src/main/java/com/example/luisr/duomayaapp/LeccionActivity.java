package com.example.luisr.duomayaapp;

import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import AsyncTasks.*;
import Clases.Contenido;
import Clases.Leccion;

public class LeccionActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo {
    TextView txtPalabra,txtPalabraEs;
    Button btnAtras, btnSig;
    ProgressBar prgLeccion;
    ImageView imgCont;
    ArrayList<Contenido> Palabras;
    int Contador = 0;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leccion);

        txtPalabra = findViewById(R.id.txtPalabra);
        txtPalabraEs = findViewById(R.id.txtPalabraEs);
        imgCont = findViewById(R.id.imgCont);
        imgCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mediaPlayer.setDataSource(Palabras.get(Contador).AudioCont);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    //mediaPlayer.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        prgLeccion = findViewById(R.id.prgLeccion);
        Palabras = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            int CodigoLeccion = bundle.getInt("CodigoLeccion");
            LlenarPalabras(CodigoLeccion);
        }

        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Contador > 0)
                {
                    Contador--;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        prgLeccion.setProgress(Contador, true);
                    }
                    else
                    {
                        prgLeccion.setProgress(Contador);
                    }
                }
                mediaPlayer.stop();
                mediaPlayer.reset();
                Log.d("Contador", ""+Contador);
                txtPalabra.setText(Palabras.get(Contador).PalabraMaya);
                txtPalabraEs.setText(Palabras.get(Contador).PalabraEsp);
                Picasso.with(LeccionActivity.this).load(Palabras.get(Contador).ImagenCont).into(imgCont);
            }
        });

        btnSig = findViewById(R.id.btnSig);
        btnSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Contador < Palabras.size()-1 )
                {
                    Contador++;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        prgLeccion.setProgress(Contador, true);
                    }
                    else
                    {
                        prgLeccion.setProgress(Contador);
                    }

                }
                mediaPlayer.stop();
                mediaPlayer.reset();
                Log.d("Contador", ""+Contador);
                txtPalabra.setText(Palabras.get(Contador).PalabraMaya);
                txtPalabraEs.setText(Palabras.get(Contador).PalabraEsp);
                Picasso.with(LeccionActivity.this).load(Palabras.get(Contador).ImagenCont).into(imgCont);
            }
        });

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
                contenido.AudioCont = object.getString("AudioCont");
                Palabras.add(contenido);
            }
            txtPalabra.setText(Palabras.get(0).PalabraMaya);
            txtPalabraEs.setText(Palabras.get(0).PalabraEsp);
            Picasso.with(this).load(Palabras.get(0).ImagenCont).into(imgCont);
            prgLeccion.setMax(Palabras.size()-1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
