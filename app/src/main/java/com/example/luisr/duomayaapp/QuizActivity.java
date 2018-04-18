package com.example.luisr.duomayaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import AsyncTasks.descargarDatosAsyncTask;
import Clases.Contenido;

public class QuizActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo{
    Button R1, R2, R3, R4;
    ImageView imgJuego;
    int Accion = 0;
    Contenido Palabra;
    ArrayList<Contenido> Palabras = new ArrayList<>();
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        R1 = findViewById(R.id.btnResp1);
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                if(btn.getText().toString() == Palabra.PalabraMaya)
                {
                    JuegoGanado();
                }
                else
                {
                    JuegoPerdido();
                }
            }
        });
        R2 = findViewById(R.id.btnResp2);
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                if(btn.getText().toString() == Palabra.PalabraMaya)
                {
                    JuegoGanado();
                }
                else
                {
                    JuegoPerdido();
                }
            }
        });
        R3 = findViewById(R.id.btnResp3);
        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                if(btn.getText().toString() == Palabra.PalabraMaya)
                {
                    JuegoGanado();
                }
                else
                {
                    JuegoPerdido();
                }
            }
        });
        R4 = findViewById(R.id.btnResp4);
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                if(btn.getText().toString() == Palabra.PalabraMaya)
                {
                    JuegoGanado();
                }
                else
                {
                    JuegoPerdido();
                }
            }
        });
        imgJuego = findViewById(R.id.ImgJuego);
        ObtenerPalabra();

    }

    public void ObtenerPalabra()
    {
        Bundle bundle = getIntent().getExtras();
        String URL="http://aprendermayaws.gearhostpreview.com/AprenderMayaWS.asmx/ListaPalabrasLeccion?ID="+bundle.getInt("ID");
        descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
        obj.delegado=this;
        try {
            Accion = 1;
            //Continua en datosDescargados
            obj.execute(new URL(URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void datosDescagados(String Datos) {
        if(Accion == 1)
        {
            try
            {
                JSONArray jsonArray = new JSONArray(Datos);

                for(int i=0; i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Contenido contenido = new Contenido();
                    contenido.CodigoContenido = object.getInt("CodigoContenido");
                    contenido.PalabraEsp = object.getString("PalabraEs");
                    contenido.PalabraMaya = object.getString("PalabraMaya");
                    contenido.CodigoLeccion = object.getInt("CodigoLeccion");
                    contenido.ImagenCont = object.getString("ImagenCont");
                    Palabras.add(contenido);
                }

                int NumeroAletorio = (int) (Math.random() * Palabras.size());
                Palabra = Palabras.get(NumeroAletorio);
                Palabras.remove(NumeroAletorio);
                IniciarJuego();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(Accion == 2)
        {
            progressDialog.hide();
            CerrarActivity();
        }
    }

    public void IniciarJuego()
    {

        int NumeroAletorio = (int) (Math.random() * 4);
        int NumeroAletorio2 = (int) (Math.random() * Palabras.size());
        int NumeroAletorio3 = (int) (Math.random() * Palabras.size());
        int NumeroAletorio4 = (int) (Math.random() * Palabras.size());

        if(NumeroAletorio == 0)
        {
            R1.setText(Palabra.PalabraMaya);
            Picasso.with(this).load(Palabra.ImagenCont).into(imgJuego);
            R2.setText(Palabras.get(NumeroAletorio2).PalabraMaya);
            R3.setText(Palabras.get(NumeroAletorio3).PalabraMaya);
            R4.setText(Palabras.get(NumeroAletorio4).PalabraMaya);

        }
        if(NumeroAletorio == 1)
        {
            R2.setText(Palabra.PalabraMaya);
            Picasso.with(this).load(Palabra.ImagenCont).into(imgJuego);
            R3.setText(Palabras.get(NumeroAletorio2).PalabraMaya);
            R1.setText(Palabras.get(NumeroAletorio3).PalabraMaya);
            R4.setText(Palabras.get(NumeroAletorio4).PalabraMaya);
        }
        if(NumeroAletorio == 2)
        {
            R3.setText(Palabra.PalabraMaya);
            Picasso.with(this).load(Palabra.ImagenCont).into(imgJuego);
            R4.setText(Palabras.get(NumeroAletorio2).PalabraMaya);
            R2.setText(Palabras.get(NumeroAletorio3).PalabraMaya);
            R1.setText(Palabras.get(NumeroAletorio4).PalabraMaya);
        }
        if(NumeroAletorio == 3)
        {
            R4.setText(Palabra.PalabraMaya);
            Picasso.with(this).load(Palabra.ImagenCont).into(imgJuego);
            R2.setText(Palabras.get(NumeroAletorio2).PalabraMaya);
            R1.setText(Palabras.get(NumeroAletorio3).PalabraMaya);
            R3.setText(Palabras.get(NumeroAletorio4).PalabraMaya);
        }
    }

    public void JuegoGanado()
    {
        preferences = this.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gasnaste");
        builder.setMessage("Has ganado.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = new ProgressDialog(QuizActivity.this);
                progressDialog.setTitle("Registrando victoria...");
                progressDialog.setMessage("Espere por favor");
                progressDialog.show();
                Accion = 2;
                descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
                obj.delegado = QuizActivity.this;
                try {
                    obj.execute(new URL("http://aprendermayaws.gear.host/AprenderMayaWS.asmx/RegistrarPuntos?CodigoUsuario="+preferences.getInt("Codigo",0)+"&Puntos=10&Descripcion=Ahorcado"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void JuegoPerdido()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Perdiste");
        builder.setMessage("Has perdido.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CerrarActivity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void CerrarActivity()
    {
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
        finish();
    }


}
