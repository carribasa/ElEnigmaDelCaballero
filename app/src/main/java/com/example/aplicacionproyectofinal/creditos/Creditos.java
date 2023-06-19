package com.example.aplicacionproyectofinal.creditos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.bbdd.Progreso;

import java.util.Objects;

public class Creditos extends AppCompatActivity {

    TextView cre_text01;
    TextView cre_text02;
    TextView cre_text03;
    TextView cre_text04;
    TextView cre_text05;
    TextView cre_text06;
    TextView cre_text07;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        cre_text01 = findViewById(R.id.cre_text01);
        cre_text02 = findViewById(R.id.cre_text02);
        cre_text03 = findViewById(R.id.cre_text03);
        cre_text04 = findViewById(R.id.cre_text04);
        cre_text05 = findViewById(R.id.cre_text05);
        cre_text06 = findViewById(R.id.cre_text06);
        cre_text07 = findViewById(R.id.cre_text07);
        Intent intent = getIntent();
        cre_text01.setText(intent.getStringExtra("creditos"));
        animacion(cre_text01);
        animacion(cre_text02);
        animacion(cre_text03);
        animacion(cre_text04);
        animacion(cre_text05);
        animacion(cre_text06);
        animacion(cre_text07);
    }

    private void animacion(TextView textView) {
        Animation translacion = new TranslateAnimation(0,0,800,0);
        translacion.setDuration(5000);

        AlphaAnimation aparicion = new AlphaAnimation(0, 1);
        aparicion.setDuration(5000);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translacion);
        animationSet.addAnimation(aparicion);
        textView.startAnimation(animationSet);
    }

    public void irAtras(View V) {
        finish();
    }


}