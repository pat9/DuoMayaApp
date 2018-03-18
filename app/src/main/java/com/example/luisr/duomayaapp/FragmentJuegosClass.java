package com.example.luisr.duomayaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by LuisR on 25/02/2018.
 */

public class FragmentJuegosClass extends Fragment {
    Button btnAhorcado;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_juegos,container,false);

        btnAhorcado = rootView.findViewById(R.id.btnAhorcado);
        btnAhorcado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AhorcadoActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }
}
