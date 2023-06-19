package com.example.aplicacionproyectofinal.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.menu.Menu;

import java.util.Objects;

public class Intro extends AppCompatActivity {

    private static final int DELAY_MILLISECONDS = 3000; // Constante para el tiempo de retraso en milisegundos
    ImageView ivIntroLogo;
    LinearLayout linearLayout2, linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ivIntroLogo = findViewById(R.id.ivLogoIntro);
        linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout = findViewById(R.id.linearLayout);
        animarLogo();

        hideActionBar(); // Ocultar la barra de acción
        hideNavigationBar(); // Ocultar la barra de navegación

        new Handler().postDelayed(() -> {
            // Crear una intención para iniciar la actividad del menú
            Intent intent = new Intent(Intro.this, Menu.class);
            startActivity(intent);

            finish(); // Finalizar la actividad actual para que no se pueda volver atrás
        }, DELAY_MILLISECONDS);
    }

    private void hideActionBar() {
        Objects.requireNonNull(getSupportActionBar()).hide(); // Ocultar la barra de acción en la parte superior de la pantalla
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; // Establecer la bandera para ocultar la barra de navegación
        decorView.setSystemUiVisibility(flags); // Aplicar la configuración de la bandera a la vista decorativa
    }

    public void animarLogo() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Configuramos la animación
                AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(2000);
                // Establecemos el texto del TextView
                ivIntroLogo.startAnimation(animation);
                linearLayout.startAnimation(animation);
                linearLayout2.startAnimation(animation);
            }
        });
    }
}