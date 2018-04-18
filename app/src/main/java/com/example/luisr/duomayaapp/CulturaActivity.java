package com.example.luisr.duomayaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CulturaActivity extends AppCompatActivity {

    TextView txtTitulo, txtCont;
    ImageView ImgCult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultura);

        txtCont = findViewById(R.id.txtCont);
        txtTitulo = findViewById(R.id.txtTitulo);
        ImgCult = findViewById(R.id.ImgCult);

        Bundle bundle =getIntent().getExtras();

        if (bundle !=null){

            Picasso.with(this).load(bundle.getString("Img")).into(ImgCult);
            txtTitulo.setText(bundle.getString("TituloC"));
            txtCont.setText(bundle.getString("Descrip"));
        }

    }
}
