package com.example.luisr.duomayaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Clases.ClsArticulos;

/**
 * Created by Luis Ramirez on 09/03/2018.
 */

public class Fragmentdetalle_cultura extends Fragment {

    private ImageView imgDetalle;
    private TextView txtCult;
    private TextView DescripDetalle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View rootView = inflater.inflate(R.layout.detalle_cultura_click,container,false);

     imgDetalle=(ImageView)rootView.findViewById(R.id.ImgDetalleCultura);
     txtCult=(TextView)rootView.findViewById(R.id.txtDetalleCultura);
     DescripDetalle=(TextView)rootView.findViewById(R.id.DescripDetalleCult);

     Bundle bundle=getArguments();
        ClsArticulos clsArticulos=null;

        if (bundle !=null){
            clsArticulos=(ClsArticulos) bundle.getSerializable("Objeto");
            Picasso.with(getContext()).load(clsArticulos.getFotoArticulo()).into(imgDetalle);
            txtCult.setText(clsArticulos.getTitulo());
            DescripDetalle.setText(clsArticulos.getDescripcion());
        }

        return rootView;
    }
}
