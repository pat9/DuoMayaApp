package com.example.luisr.duomayaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import AsyncTasks.descargarDatosAsyncTask;
import Clases.Contenido;


public class AhorcadoActivity extends AppCompatActivity implements descargarDatosAsyncTask.interfacedelhilo {
    LinearLayout Letras, Espacios;
    ArrayList<View> BotonesLetras;
    TextView txtPista;
    Integer Intentos, PalabraLength;
    char[] Palabra;
    String Pista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahorcado);
        Letras = findViewById(R.id.Letras);
        Espacios = findViewById(R.id.Espacios);
        txtPista = findViewById(R.id.txtPista);
        BotonesLetras = Letras.getTouchables();
        Intentos = 0;
        PalabraLength = 0;
        Habilitar(false);
        ObtenerPalabra();
    }

    public void Verificar(View view)
    {
        boolean Encontrado = false;
        Button btn = (Button)view;
        CharSequence Letra = btn.getText();
        for(int i=0; i<Palabra.length; i++)
        {
            if(Letra.charAt(0) == Palabra[i])
            {
                TextView Text = findViewById(i);
                Text.setText(Letra);
                btn.setEnabled(false);
                PalabraLength--;
                Encontrado = true;

                if(PalabraLength == 0)
                {
                    Habilitar(false);
                    Toast.makeText(this, "Ganaste :D", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        if(!Encontrado)
        {
            btn.setEnabled(false);
            Intentos++;
            //txtIntentos.setText(Intentos + " de 4 intentos");
        }

        if(Intentos == 4)
        {
            Toast.makeText(this, "Perdiste :(", Toast.LENGTH_SHORT).show();
            for(int i=0; i<Palabra.length; i++)
            {

                TextView Text = findViewById(i);
                Text.setText(""+Palabra[i]);
                btn.setEnabled(false);

            }
            Habilitar(false);
            return;
        }
    }

    public void Habilitar(boolean Estado)
    {
        for(View Boton: BotonesLetras)
        {
            Boton.setEnabled(Estado);
        }
    }

    public void ObtenerPalabra()
    {
        String URL="http://aprendermayaws.gearhostpreview.com/AprenderMayaWS.asmx/ListaPalabras";
        descargarDatosAsyncTask obj = new descargarDatosAsyncTask();
        obj.delegado=this;
        try {
            //Continua en datosDescargados
            obj.execute(new URL(URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void datosDescagados(String Datos) {
        final ArrayList<Contenido> Lista = new ArrayList<Contenido>();
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
                Lista.add(contenido);
            }

            int NumeroAletorio = (int) (Math.random() * Lista.size()) ;
            Palabra = Lista.get(NumeroAletorio).PalabraMaya.toUpperCase().toCharArray();
            Pista = Lista.get(NumeroAletorio).PalabraEsp;
            txtPista.setText("Se traduce al español como: " + Pista);
            PalabraLength = Palabra.length;
            Construye();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Construye()
    {

        for(int i = 0; i < Palabra.length; i++)
        {
            TextView row = new TextView(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            row.setId(i);
            row.setText("-");
            row.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            row.setPadding(0, 0, 0, 0);
            row.setWidth(100);

            Espacios.addView(row);
        }
        Habilitar(true);
    }



}
