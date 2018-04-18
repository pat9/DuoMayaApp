package com.example.luisr.duomayaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MemoramaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorama);
        CrearMemo();
    }

    public void CrearMemo()
    {
        LinearLayout fila1 = findViewById(R.id.fila1);
        for (int i = 0; i < 3; i++)
        {
            final LinearLayout buttonContainer = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_button,null);
            buttonContainer.setTag(i);
            fila1.addView(buttonContainer);
        }
    }

}
