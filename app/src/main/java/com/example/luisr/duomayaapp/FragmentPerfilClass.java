package com.example.luisr.duomayaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import Clases.Usuario;

/**
 * Created by LuisR on 25/02/2018.
 */

public class FragmentPerfilClass extends Fragment {
    TextView txtNombre;
    ImageView imgPerfil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_perfil,container,false);
        txtNombre = rootView.findViewById(R.id.txtNombreUsu);
        imgPerfil = rootView.findViewById(R.id.imgperfil);


        Bundle bundle = getArguments();



        if(bundle != null)
        {
            Usuario usuario = (Usuario)bundle.get("Usuario");
            txtNombre.setText(usuario.Nombre + " " + usuario.Apellido +" ("+usuario.NickName+")");
            Picasso.with(getActivity()).load(usuario.FotoPerfil).into(imgPerfil);

        }



        return rootView;

    }

    public static FragmentPerfilClass newInstance() {

        Bundle args = new Bundle();
        FragmentPerfilClass fragment = new FragmentPerfilClass();
        fragment.setArguments(args);
        return fragment;
    }

}
