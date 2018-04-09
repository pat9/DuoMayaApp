package com.example.luisr.duomayaapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Clases.ClsArticulos;

public class DetalleCulturaActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewTitulo;
    TextView textViewDescrip;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cultura);

        imageView=(ImageView)findViewById(R.id.ImgCult);
        textViewTitulo=(TextView)findViewById(R.id.textTituloCult);
        textViewDescrip=(TextView)findViewById(R.id.descriptxt);
        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbarDetalle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       Bundle bundle =getIntent().getExtras();



        if (bundle !=null){

            Picasso.with(this).load(bundle.getString("Img")).into(imageView);
            textViewTitulo.setText(bundle.getString("TituloC"));
            textViewDescrip.setText(bundle.getString("Descrip"));
        }

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
        finish();
    }

}
