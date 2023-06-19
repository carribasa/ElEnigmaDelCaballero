package com.example.aplicacionproyectofinal.minijuego03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.ajustes.Ajustes;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.mundo.Mundo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class CodigoEnigmatico extends AppCompatActivity {

    //Botones ok
    ImageButton boton01,boton02,boton03,boton04,boton05,boton06,boton07,boton08;
    //Botones gemas
    ImageView imagen01,imagen02,imagen03,imagen04,imagen11,imagen12,imagen13,imagen14,imagen21,imagen22,imagen23,imagen24,imagen31,imagen32,
            imagen33,imagen34,imagen41,imagen42,imagen43,imagen44,imagen51,imagen52,imagen53,imagen54,imagen61,imagen62,imagen63,imagen64,
            imagen71,imagen72,imagen73,imagen74,imagen81,imagen82,imagen83,imagen84;

    GifImageView confetti;
    FrameLayout linearResultado;
    TextView textoGanador,ee_monGan,ee_monAnt,ee_monAct;
    ImageView ee_cerrar;
    LinearLayout linearTutorial;
    TextView tvTextoTutorial;
    LinearLayout linearPausa;
    ImageView imagenFondoPausa;
    Progreso progreso;
    Integer monedasGanadas = 350;
    private String textoTutorial = "Debes descifrar la contraseña de colores de la última columna:\n" +
            "1- Para ello, al pulsar en las casillas de la primera columna, cada cuadrado irá cambiando de color. Los colores disponibles son azul, rosa, amarillo, morado, verde y rojo. Ten en cuenta que no puedes repetir el mismo color en esa columna.\n" +
            "2- Al colocar los colores sin repetir, aparecerá un botón para confirmar tu decisión.Después de confirmar tu elección, recibirás una respuesta sobre la precisión de los colores seleccionados. Esta respuesta se mostrará en forma de mensaje y de gemas:\n" +
            "-Gema verde: Si has seleccionado un color correcto en la posición correcta.\n          " +
            "-Gema roja: Si has seleccionado un color correcto pero en la posición incorrecta.\n" +
            "3- Si logras adivinar la contraseña final en el orden correcto, habrás ganado el juego. Si no, tendrás que volver a intentarlo, disponiendo de 8 intentos. \n" +
            "¡Mucha suerte!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_enigmatico);

        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        Intent intent = getIntent();
        progreso = (Progreso) intent.getSerializableExtra("progreso");
        inicializador();
        crearClave();
        animacionInicio();
    }

    public void ocultarTutorial(View V) {
        linearTutorial.setVisibility(View.INVISIBLE);
    }

    public void mostrarTutorial(View V) {
        linearTutorial.setVisibility(View.VISIBLE);
    }


    public void animarTexto() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Configuramos la animación
                AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(1000);
                // Establecemos el texto del TextView
                tvTextoTutorial.setText(textoTutorial);
                tvTextoTutorial.startAnimation(animation);
            }
        });
    }
    private void animacionInicio() {
        ImageView icono = findViewById(R.id.code_icono);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(1);

        // Escala
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.5f, 1, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setStartOffset(3000);

        // Animación combinada
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        icono.startAnimation(animationSet);

        ImageView tituloGrande = findViewById(R.id.code_titulo_grande);
        ScaleAnimation scaleAnimation02 = new ScaleAnimation(1, 0.5f, 1, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1);
        scaleAnimation02.setDuration(2000);
        scaleAnimation02.setRepeatCount(1);
        scaleAnimation02.setRepeatMode(Animation.REVERSE);

        AnimationSet animationSet02 = new AnimationSet(true);
        animationSet02.addAnimation(scaleAnimation02);
        animationSet02.addAnimation(alphaAnimation);
        tituloGrande.startAnimation(animationSet02);

        ImageView fondoGrande = findViewById(R.id.code_pruebaImagen);
        fondoGrande.startAnimation(alphaAnimation);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Este método se ejecuta cuando la animación comienza
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Este método se ejecuta cuando la animación termina
                icono.setVisibility(View.GONE);
                tituloGrande.setVisibility(View.GONE);
                fondoGrande.setVisibility(View.GONE);
                animarTexto();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Este método se ejecuta cuando la animación se repite
            }
        });
    }

    private void inicializador() {
        boton01 = findViewById(R.id.code_botonOk01);
        boton02 = findViewById(R.id.code_botonOk02);
        boton03 = findViewById(R.id.code_botonOk03);
        boton04 = findViewById(R.id.code_botonOk04);
        boton05 = findViewById(R.id.code_botonOk05);
        boton06 = findViewById(R.id.code_botonOk06);
        boton07 = findViewById(R.id.code_botonOk07);
        boton08 = findViewById(R.id.code_botonOk08);

        //Botones gemas
        imagen01 = findViewById(R.id.code_imV01);
        imagen02 = findViewById(R.id.code_imV02);
        imagen03 = findViewById(R.id.code_imV03);
        imagen04 = findViewById(R.id.code_imV04);

        imagen11 = findViewById(R.id.code_imV11);
        imagen12 = findViewById(R.id.code_imV12);
        imagen13 = findViewById(R.id.code_imV13);
        imagen14 = findViewById(R.id.code_imV14);
        imagen11.setEnabled(false);
        imagen12.setEnabled(false);
        imagen13.setEnabled(false);
        imagen14.setEnabled(false);

        imagen21 = findViewById(R.id.code_imV21);
        imagen22 = findViewById(R.id.code_imV22);
        imagen23 = findViewById(R.id.code_imV23);
        imagen24 = findViewById(R.id.code_imV24);
        imagen21.setEnabled(false);
        imagen22.setEnabled(false);
        imagen23.setEnabled(false);
        imagen24.setEnabled(false);

        imagen31 = findViewById(R.id.code_imV31);
        imagen32 = findViewById(R.id.code_imV32);
        imagen33 = findViewById(R.id.code_imV33);
        imagen34 = findViewById(R.id.code_imV34);
        imagen31.setEnabled(false);
        imagen32.setEnabled(false);
        imagen33.setEnabled(false);
        imagen34.setEnabled(false);

        imagen41 = findViewById(R.id.code_imV41);
        imagen42 = findViewById(R.id.code_imV42);
        imagen43 = findViewById(R.id.code_imV43);
        imagen44 = findViewById(R.id.code_imV44);
        imagen41.setEnabled(false);
        imagen42.setEnabled(false);
        imagen43.setEnabled(false);
        imagen44.setEnabled(false);

        imagen51 = findViewById(R.id.code_imV51);
        imagen52 = findViewById(R.id.code_imV52);
        imagen53 = findViewById(R.id.code_imV53);
        imagen54 = findViewById(R.id.code_imV54);
        imagen51.setEnabled(false);
        imagen52.setEnabled(false);
        imagen53.setEnabled(false);
        imagen54.setEnabled(false);

        imagen61 = findViewById(R.id.code_imV61);
        imagen62 = findViewById(R.id.code_imV62);
        imagen63 = findViewById(R.id.code_imV63);
        imagen64 = findViewById(R.id.code_imV64);
        imagen61.setEnabled(false);
        imagen62.setEnabled(false);
        imagen63.setEnabled(false);
        imagen64.setEnabled(false);

        imagen71 = findViewById(R.id.code_imV71);
        imagen72 = findViewById(R.id.code_imV72);
        imagen73 = findViewById(R.id.code_imV73);
        imagen74 = findViewById(R.id.code_imV74);
        imagen71.setEnabled(false);
        imagen72.setEnabled(false);
        imagen73.setEnabled(false);
        imagen74.setEnabled(false);

        imagen81 = findViewById(R.id.code_imV81);
        imagen82 = findViewById(R.id.code_imV82);
        imagen83 = findViewById(R.id.code_imV83);
        imagen84 = findViewById(R.id.code_imV84);

        //animacion final
        linearResultado = findViewById(R.id.linearResultado);
        confetti = findViewById(R.id.confetti);
        textoGanador = findViewById(R.id.textoGanador);
        ee_cerrar = findViewById(R.id.ee_cerrar);
        ee_monGan = findViewById(R.id.ee_mongan);
        ee_monAnt = findViewById(R.id.ee_monant);
        ee_monAct = findViewById(R.id.ee_monact);

        //tutorial
        tvTextoTutorial = findViewById(R.id.tvTextoTutorial);
        linearTutorial = findViewById(R.id.linearTutorial);
        imagenFondoPausa = findViewById(R.id.imagenFondoPausa);
        linearPausa = findViewById(R.id.linearPausa);

    }

    public void cambiarColor01 (View v){
        comprobacionImagen(imagen01);
        String etiqueta01 = imagen01.getTag().toString();
        String etiqueta02 = imagen02.getTag().toString();
        String etiqueta03 = imagen03.getTag().toString();
        String etiqueta04 = imagen04.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton01);
    }

    public void cambiarColor02 (View v){
        comprobacionImagen(imagen02);
        String etiqueta01 = imagen01.getTag().toString();
        String etiqueta02 = imagen02.getTag().toString();
        String etiqueta03 = imagen03.getTag().toString();
        String etiqueta04 = imagen04.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton01);
    }

    public void cambiarColor03 (View v){
        comprobacionImagen(imagen03);
        String etiqueta01 = imagen01.getTag().toString();
        String etiqueta02 = imagen02.getTag().toString();
        String etiqueta03 = imagen03.getTag().toString();
        String etiqueta04 = imagen04.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton01);
    }

    public void cambiarColor04 (View v){
        comprobacionImagen(imagen04);
        String etiqueta01 = imagen01.getTag().toString();
        String etiqueta02 = imagen02.getTag().toString();
        String etiqueta03 = imagen03.getTag().toString();
        String etiqueta04 = imagen04.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton01);
    }

    public void cambiarColor11 (View v){
        comprobacionImagen(imagen11);
        String etiqueta01 = imagen11.getTag().toString();
        String etiqueta02 = imagen12.getTag().toString();
        String etiqueta03 = imagen13.getTag().toString();
        String etiqueta04 = imagen14.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton02);
    }

    public void cambiarColor12 (View v){
        comprobacionImagen(imagen12);
        String etiqueta01 = imagen11.getTag().toString();
        String etiqueta02 = imagen12.getTag().toString();
        String etiqueta03 = imagen13.getTag().toString();
        String etiqueta04 = imagen14.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton02);
    }

    public void cambiarColor13 (View v){
        comprobacionImagen(imagen13);
        String etiqueta01 = imagen11.getTag().toString();
        String etiqueta02 = imagen12.getTag().toString();
        String etiqueta03 = imagen13.getTag().toString();
        String etiqueta04 = imagen14.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton02);
    }

    public void cambiarColor14 (View v){
        comprobacionImagen(imagen14);
        String etiqueta01 = imagen11.getTag().toString();
        String etiqueta02 = imagen12.getTag().toString();
        String etiqueta03 = imagen13.getTag().toString();
        String etiqueta04 = imagen14.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton02);
    }
    public void cambiarColor21 (View v){
        comprobacionImagen(imagen21);
        String etiqueta01 = imagen21.getTag().toString();
        String etiqueta02 = imagen22.getTag().toString();
        String etiqueta03 = imagen23.getTag().toString();
        String etiqueta04 = imagen24.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton03);
    }

    public void cambiarColor22 (View v){
        comprobacionImagen(imagen22);
        String etiqueta01 = imagen21.getTag().toString();
        String etiqueta02 = imagen22.getTag().toString();
        String etiqueta03 = imagen23.getTag().toString();
        String etiqueta04 = imagen24.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton03);
    }

    public void cambiarColor23 (View v){
        comprobacionImagen(imagen23);
        String etiqueta01 = imagen21.getTag().toString();
        String etiqueta02 = imagen22.getTag().toString();
        String etiqueta03 = imagen23.getTag().toString();
        String etiqueta04 = imagen24.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton03);
    }

    public void cambiarColor24 (View v){
        comprobacionImagen(imagen24);
        String etiqueta01 = imagen21.getTag().toString();
        String etiqueta02 = imagen22.getTag().toString();
        String etiqueta03 = imagen23.getTag().toString();
        String etiqueta04 = imagen24.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton03);
    }
    public void cambiarColor31 (View v){
        comprobacionImagen(imagen31);
        String etiqueta01 = imagen31.getTag().toString();
        String etiqueta02 = imagen32.getTag().toString();
        String etiqueta03 = imagen33.getTag().toString();
        String etiqueta04 = imagen34.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton04);
    }

    public void cambiarColor32 (View v){
        comprobacionImagen(imagen32);
        String etiqueta01 = imagen31.getTag().toString();
        String etiqueta02 = imagen32.getTag().toString();
        String etiqueta03 = imagen33.getTag().toString();
        String etiqueta04 = imagen34.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton04);
    }

    public void cambiarColor33 (View v){
        comprobacionImagen(imagen33);
        String etiqueta01 = imagen31.getTag().toString();
        String etiqueta02 = imagen32.getTag().toString();
        String etiqueta03 = imagen33.getTag().toString();
        String etiqueta04 = imagen34.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton04);
    }

    public void cambiarColor34 (View v){
        comprobacionImagen(imagen34);
        String etiqueta01 = imagen31.getTag().toString();
        String etiqueta02 = imagen32.getTag().toString();
        String etiqueta03 = imagen33.getTag().toString();
        String etiqueta04 = imagen34.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton04);
    }

    public void cambiarColor41 (View v){
        comprobacionImagen(imagen41);
        String etiqueta01 = imagen41.getTag().toString();
        String etiqueta02 = imagen42.getTag().toString();
        String etiqueta03 = imagen43.getTag().toString();
        String etiqueta04 = imagen44.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton05);
    }

    public void cambiarColor42 (View v){
        comprobacionImagen(imagen42);
        String etiqueta01 = imagen41.getTag().toString();
        String etiqueta02 = imagen42.getTag().toString();
        String etiqueta03 = imagen43.getTag().toString();
        String etiqueta04 = imagen44.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton05);
    }

    public void cambiarColor43 (View v){
        comprobacionImagen(imagen43);
        String etiqueta01 = imagen41.getTag().toString();
        String etiqueta02 = imagen42.getTag().toString();
        String etiqueta03 = imagen43.getTag().toString();
        String etiqueta04 = imagen44.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton05);
    }

    public void cambiarColor44 (View v){
        comprobacionImagen(imagen44);
        String etiqueta01 = imagen41.getTag().toString();
        String etiqueta02 = imagen42.getTag().toString();
        String etiqueta03 = imagen43.getTag().toString();
        String etiqueta04 = imagen44.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton05);
    }

    public void cambiarColor51 (View v){
        comprobacionImagen(imagen51);
        String etiqueta01 = imagen51.getTag().toString();
        String etiqueta02 = imagen52.getTag().toString();
        String etiqueta03 = imagen53.getTag().toString();
        String etiqueta04 = imagen54.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton06);
    }

    public void cambiarColor52 (View v){
        comprobacionImagen(imagen52);
        String etiqueta01 = imagen51.getTag().toString();
        String etiqueta02 = imagen52.getTag().toString();
        String etiqueta03 = imagen53.getTag().toString();
        String etiqueta04 = imagen54.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton06);
    }

    public void cambiarColor53 (View v){
        comprobacionImagen(imagen53);
        String etiqueta01 = imagen51.getTag().toString();
        String etiqueta02 = imagen52.getTag().toString();
        String etiqueta03 = imagen53.getTag().toString();
        String etiqueta04 = imagen54.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton06);
    }

    public void cambiarColor54 (View v){
        comprobacionImagen(imagen54);
        String etiqueta01 = imagen51.getTag().toString();
        String etiqueta02 = imagen52.getTag().toString();
        String etiqueta03 = imagen53.getTag().toString();
        String etiqueta04 = imagen54.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton06);
    }

    public void cambiarColor61 (View v){
        comprobacionImagen(imagen61);
        String etiqueta01 = imagen61.getTag().toString();
        String etiqueta02 = imagen62.getTag().toString();
        String etiqueta03 = imagen63.getTag().toString();
        String etiqueta04 = imagen64.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton07);
    }

    public void cambiarColor62 (View v){
        comprobacionImagen(imagen62);
        String etiqueta01 = imagen61.getTag().toString();
        String etiqueta02 = imagen62.getTag().toString();
        String etiqueta03 = imagen63.getTag().toString();
        String etiqueta04 = imagen64.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton07);
    }

    public void cambiarColor63 (View v){
        comprobacionImagen(imagen63);
        String etiqueta01 = imagen61.getTag().toString();
        String etiqueta02 = imagen62.getTag().toString();
        String etiqueta03 = imagen63.getTag().toString();
        String etiqueta04 = imagen64.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton07);
    }

    public void cambiarColor64 (View v){
        comprobacionImagen(imagen64);
        String etiqueta01 = imagen61.getTag().toString();
        String etiqueta02 = imagen62.getTag().toString();
        String etiqueta03 = imagen63.getTag().toString();
        String etiqueta04 = imagen64.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton07);
    }

    public void cambiarColor71 (View v){
        comprobacionImagen(imagen71);
        String etiqueta01 = imagen71.getTag().toString();
        String etiqueta02 = imagen72.getTag().toString();
        String etiqueta03 = imagen73.getTag().toString();
        String etiqueta04 = imagen74.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton08);
    }

    public void cambiarColor72 (View v){
        comprobacionImagen(imagen72);
        String etiqueta01 = imagen71.getTag().toString();
        String etiqueta02 = imagen72.getTag().toString();
        String etiqueta03 = imagen73.getTag().toString();
        String etiqueta04 = imagen74.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton08);
    }

    public void cambiarColor73 (View v){
        comprobacionImagen(imagen73);
        String etiqueta01 = imagen71.getTag().toString();
        String etiqueta02 = imagen72.getTag().toString();
        String etiqueta03 = imagen73.getTag().toString();
        String etiqueta04 = imagen74.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton08);
    }

    public void cambiarColor74 (View v){
        comprobacionImagen(imagen74);
        String etiqueta01 = imagen71.getTag().toString();
        String etiqueta02 = imagen72.getTag().toString();
        String etiqueta03 = imagen73.getTag().toString();
        String etiqueta04 = imagen74.getTag().toString();
        visibilidadDeBoton(etiqueta01,etiqueta02,etiqueta03,etiqueta04,boton08);
    }

    private void comprobacionImagen(ImageView imagenActual){
        float y01 = imagen01.getY();
        float y02 = imagen02.getY();
        float y03 = imagen03.getY();
        switch (imagenActual.getTag().toString()) {
            case "black":
            case "red":
                if (imagenActual.getY()==y01){
                    imagenActual.setImageResource(R.drawable.code_figura01_blue);
                }else if(imagenActual.getY()==y02){
                    imagenActual.setImageResource(R.drawable.code_figura02_blue);
                }else if(imagenActual.getY()==y03){
                    imagenActual.setImageResource(R.drawable.code_figura03_blue);
                }else {
                    imagenActual.setImageResource(R.drawable.code_figura04_blue);
                }
                imagenActual.setTag("blue");
                break;
            case "blue":
                if (imagenActual.getY()==y01){
                    imagenActual.setImageResource(R.drawable.code_figura01_pink);
                }else if(imagenActual.getY()==y02){
                    imagenActual.setImageResource(R.drawable.code_figura02_pink);
                }else if(imagenActual.getY()==y03){
                    imagenActual.setImageResource(R.drawable.code_figura03_pink);
                }else {
                    imagenActual.setImageResource(R.drawable.code_figura04_pink);
                }
                imagenActual.setTag("pink");
                break;
            case "pink":
                if (imagenActual.getY()==y01){
                    imagenActual.setImageResource(R.drawable.code_figura01_yellow);
                }else if(imagenActual.getY()==y02){
                    imagenActual.setImageResource(R.drawable.code_figura02_yellow);
                }else if(imagenActual.getY()==y03){
                    imagenActual.setImageResource(R.drawable.code_figura03_yellow);
                }else {
                    imagenActual.setImageResource(R.drawable.code_figura04_yellow);
                }
                imagenActual.setTag("yellow");
                break;
            case "yellow":
                if (imagenActual.getY()==y01){
                    imagenActual.setImageResource(R.drawable.code_figura01_purple);
                }else if(imagenActual.getY()==y02){
                    imagenActual.setImageResource(R.drawable.code_figura02_purple);
                }else if(imagenActual.getY()==y03){
                    imagenActual.setImageResource(R.drawable.code_figura03_purple);
                }else {
                    imagenActual.setImageResource(R.drawable.code_figura04_purple);
                }
                imagenActual.setTag("purple");
                break;
            case "purple":
                if (imagenActual.getY()==y01){
                    imagenActual.setImageResource(R.drawable.code_figura01_green);
                }else if(imagenActual.getY()==y02){
                    imagenActual.setImageResource(R.drawable.code_figura02_green);
                }else if(imagenActual.getY()==y03){
                    imagenActual.setImageResource(R.drawable.code_figura03_green);
                }else {
                    imagenActual.setImageResource(R.drawable.code_figura04_green);
                }
                imagenActual.setTag("green");
                break;
            case "green":

                if (imagenActual.getY()==y01){
                    imagenActual.setImageResource(R.drawable.code_figura01_red);
                }else if(imagenActual.getY()==y02){
                    imagenActual.setImageResource(R.drawable.code_figura02_red);
                }else if(imagenActual.getY()==y03){
                    imagenActual.setImageResource(R.drawable.code_figura03_red);
                }else {
                    imagenActual.setImageResource(R.drawable.code_figura04_red);
                }
                imagenActual.setTag("red");
                break;
        }
    }

    private void visibilidadDeBoton(String etiqueta01, String etiqueta02, String etiqueta03, String etiqueta04, ImageButton boton01) {
        if (!etiqueta01.equals("black") &&
                !etiqueta02.equals("black") &&
                !etiqueta03.equals("black") &&
                !etiqueta04.equals("black")
        ){
            if(etiqueta01.equals(etiqueta02) || etiqueta01.equals(etiqueta03) || etiqueta01.equals(etiqueta04)
                    || etiqueta02.equals(etiqueta03) || etiqueta02.equals(etiqueta04) || etiqueta03.equals(etiqueta04)
            ){
                boton01.setVisibility(View.INVISIBLE);
            }else{
                boton01.setVisibility(View.VISIBLE);
            }
        }else{
            boton01.setVisibility(View.INVISIBLE);
        }
    }

    private void crearClave() {
        ArrayList<String> colores = new ArrayList<String>();
        colores.add("red");
        colores.add("blue");
        colores.add("pink");
        colores.add("yellow");
        colores.add("purple");
        colores.add("green");
        Collections.shuffle(colores);

        imagen81.setTag(colores.get(0));
        imagen82.setTag(colores.get(1));
        imagen83.setTag(colores.get(2));
        imagen84.setTag(colores.get(3));

        Log.i("Contraseña",imagen81.getTag().toString()+imagen82.getTag().toString()
        +imagen83.getTag().toString()+imagen84.getTag().toString());
    }

    public void accionBoton01 (View v){
        ImageView imagenGema01 = findViewById(R.id.code_gema01);
        ImageView imagenGema02 = findViewById(R.id.code_gema02);
        ImageView imagenGema03 = findViewById(R.id.code_gema03);
        ImageView imagenGema04 = findViewById(R.id.code_gema04);
        if (!metodoAccionBoton(imagen01,imagen02,imagen03,imagen04,boton01,
                imagenGema01,imagenGema02,imagenGema03,imagenGema04)){
            imagen11.setEnabled(true);
            imagen12.setEnabled(true);
            imagen13.setEnabled(true);
            imagen14.setEnabled(true);
        }
    }

    public void accionBoton02 (View v){
        ImageView imagenGema01 = findViewById(R.id.code_gema11);
        ImageView imagenGema02 = findViewById(R.id.code_gema12);
        ImageView imagenGema03 = findViewById(R.id.code_gema13);
        ImageView imagenGema04 = findViewById(R.id.code_gema14);
        if (!metodoAccionBoton(imagen11,imagen12,imagen13,imagen14,boton02,
                imagenGema01,imagenGema02,imagenGema03,imagenGema04)){
            imagen21.setEnabled(true);
            imagen22.setEnabled(true);
            imagen23.setEnabled(true);
            imagen24.setEnabled(true);
        }
    }

    public void accionBoton03 (View v){
        ImageView imagenGema01 = findViewById(R.id.code_gema21);
        ImageView imagenGema02 = findViewById(R.id.code_gema22);
        ImageView imagenGema03 = findViewById(R.id.code_gema23);
        ImageView imagenGema04 = findViewById(R.id.code_gema24);
        if (!metodoAccionBoton(imagen21,imagen22,imagen23,imagen24,boton03,
                imagenGema01,imagenGema02,imagenGema03,imagenGema04)){
            imagen31.setEnabled(true);
            imagen32.setEnabled(true);
            imagen33.setEnabled(true);
            imagen34.setEnabled(true);
        }
    }

    public void accionBoton04 (View v){
        ImageView imagenGema01 = findViewById(R.id.code_gema31);
        ImageView imagenGema02 = findViewById(R.id.code_gema32);
        ImageView imagenGema03 = findViewById(R.id.code_gema33);
        ImageView imagenGema04 = findViewById(R.id.code_gema34);
        if (!metodoAccionBoton(imagen31,imagen32,imagen33,imagen34,boton04,
                imagenGema01,imagenGema02,imagenGema03,imagenGema04)){
            imagen41.setEnabled(true);
            imagen42.setEnabled(true);
            imagen43.setEnabled(true);
            imagen44.setEnabled(true);
        }
    }

    public void accionBoton05 (View v){
        ImageView imagenGema01 = findViewById(R.id.code_gema41);
        ImageView imagenGema02 = findViewById(R.id.code_gema42);
        ImageView imagenGema03 = findViewById(R.id.code_gema43);
        ImageView imagenGema04 = findViewById(R.id.code_gema44);
        if (!metodoAccionBoton(imagen41,imagen42,imagen43,imagen44,boton05,
                imagenGema01,imagenGema02,imagenGema03,imagenGema04)){
            imagen51.setEnabled(true);
            imagen52.setEnabled(true);
            imagen53.setEnabled(true);
            imagen54.setEnabled(true);
        }
    }

    public void accionBoton06 (View v){
        ImageView imagenGema01 = findViewById(R.id.code_gema51);
        ImageView imagenGema02 = findViewById(R.id.code_gema52);
        ImageView imagenGema03 = findViewById(R.id.code_gema53);
        ImageView imagenGema04 = findViewById(R.id.code_gema54);
        if (!metodoAccionBoton(imagen51,imagen52,imagen53,imagen54,boton06,
                imagenGema01,imagenGema02,imagenGema03,imagenGema04)){
            imagen61.setEnabled(true);
            imagen62.setEnabled(true);
            imagen63.setEnabled(true);
            imagen64.setEnabled(true);
        }
    }

    public void accionBoton07 (View v){
        ImageView imagenGema01 = findViewById(R.id.code_gema61);
        ImageView imagenGema02 = findViewById(R.id.code_gema62);
        ImageView imagenGema03 = findViewById(R.id.code_gema63);
        ImageView imagenGema04 = findViewById(R.id.code_gema64);
        if (!metodoAccionBoton(imagen61,imagen62,imagen63,imagen64,boton07,
                imagenGema01,imagenGema02,imagenGema03,imagenGema04)){
            imagen71.setEnabled(true);
            imagen72.setEnabled(true);
            imagen73.setEnabled(true);
            imagen74.setEnabled(true);
        }
    }

    public void accionBoton08 (View v){
        ImageView imagenGema01 = findViewById(R.id.code_gema71);
        ImageView imagenGema02 = findViewById(R.id.code_gema72);
        ImageView imagenGema03 = findViewById(R.id.code_gema73);
        ImageView imagenGema04 = findViewById(R.id.code_gema74);

        if (!metodoAccionBoton(imagen71,imagen72,imagen73,imagen74,boton08,
                imagenGema01,imagenGema02,imagenGema03,imagenGema04)){
            Toast.makeText(getApplicationContext(),"HAS PERDIDOOO!!!",
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"LA CONTRASEÑA ERA:", Toast.LENGTH_SHORT).show();
            hasPerdido(imagen81,1300);
            hasPerdido(imagen82,1600);
            hasPerdido(imagen83,1900);
            hasPerdido(imagen84,2200);
        }
    }

    private void hasPerdido(ImageView imagen,int tiempo) {
        float y01 = imagen01.getY();
        float y02 = imagen02.getY();
        float y03 = imagen03.getY();
        switch (imagen.getTag().toString()) {
            case "blue":
                if (imagen.getY()==y01){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura01_blue),tiempo);
                }else if(imagen.getY()==y02){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura02_blue),tiempo);
                }else if(imagen.getY()==y03){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura03_blue),tiempo);
                }else {
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura04_blue),tiempo);
                }
                break;
            case "pink":
                if (imagen.getY()==y01){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura01_pink),tiempo);
                }else if(imagen.getY()==y02){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura02_pink),tiempo);
                }else if(imagen.getY()==y03){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura03_pink),tiempo);
                }else {
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura04_pink),tiempo);
                }
                break;
            case "yellow":
                if (imagen.getY()==y01){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura01_yellow),tiempo);
                }else if(imagen.getY()==y02){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura02_yellow),tiempo);
                }else if(imagen.getY()==y03){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura03_yellow),tiempo);
                }else {
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura04_yellow),tiempo);
                }
                break;
            case "purple":
                if (imagen.getY()==y01){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura01_purple),tiempo);
                }else if(imagen.getY()==y02){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura02_purple),tiempo);
                }else if(imagen.getY()==y03){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura03_purple),tiempo);
                }else {
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura04_purple),tiempo);
                }
                break;
            case "green":
                if (imagen.getY()==y01){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura01_green),tiempo);
                }else if(imagen.getY()==y02){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura02_green),tiempo);
                }else if(imagen.getY()==y03){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura03_green),tiempo);
                }else {
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura04_green),tiempo);
                }
                break;
            case "red":
                if (imagen.getY()==y01){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura01_red),tiempo);
                }else if(imagen.getY()==y02){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura02_red),tiempo);
                }else if(imagen.getY()==y03){
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura03_red),tiempo);
                }else {
                    cambiarImagenesGemasPorTiempo(imagen,getDrawable(R.drawable.code_figura04_red),tiempo);
                }
                break;
        }
    }

    private boolean metodoAccionBoton(ImageView imagen01, ImageView imagen02,
                                      ImageView imagen03, ImageView imagen04,
                                      ImageButton boton01, ImageView imagenGema01,
                                      ImageView imagenGema02, ImageView imagenGema03,
                                      ImageView imagenGema04) {

        Boolean hasGanado = false;
        ArrayList<String> eleccion = new ArrayList<>();
        eleccion.add(imagen01.getTag().toString());
        eleccion.add(imagen02.getTag().toString());
        eleccion.add(imagen03.getTag().toString());
        eleccion.add(imagen04.getTag().toString());

        ArrayList<String> contraseña = new ArrayList<>();
        contraseña.add(imagen81.getTag().toString());
        contraseña.add(imagen82.getTag().toString());
        contraseña.add(imagen83.getTag().toString());
        contraseña.add(imagen84.getTag().toString());
        //Prueba
        // Toast.makeText(getApplicationContext(),eleccion.toString(),Toast.LENGTH_SHORT).show();
        Log.i("Contraseña",eleccion.toString());
        // Prueba
        // Toast.makeText(getApplicationContext(),contraseña.toString(), Toast.LENGTH_SHORT).show();
        Log.i("Contraseña",contraseña.toString());

        int coloresCorrectos = 0;
        int coloresEnPosicionCorrecta = 0;
        for (int i = 0; i < 4; i++) {
            if (contraseña.contains(eleccion.get(i))) {
                coloresCorrectos++;
                if (contraseña.get(i).equals(eleccion.get(i)) ){
                    coloresEnPosicionCorrecta++;
                }
            }
        }

        if (coloresEnPosicionCorrecta == 4) {
            imagen01.setEnabled(false);
            imagen02.setEnabled(false);
            imagen03.setEnabled(false);
            imagen04.setEnabled(false);
            cambiarImagenesGemasPorTiempo(imagenGema01, getDrawable(R.drawable.code_gema_verde),300);
            cambiarImagenesGemasPorTiempo(imagenGema02, getDrawable(R.drawable.code_gema_verde),600);
            cambiarImagenesGemasPorTiempo(imagenGema03, getDrawable(R.drawable.code_gema_verde),900);
            cambiarImagenesGemasPorTiempo(imagenGema04, getDrawable(R.drawable.code_gema_verde),1200);
            cambiarImagenesGemasPorTiempo(imagen81,imagen01.getDrawable(),1500);
            cambiarImagenesGemasPorTiempo(imagen82,imagen02.getDrawable(),1800);
            cambiarImagenesGemasPorTiempo(imagen83,imagen03.getDrawable(),2100);
            cambiarImagenesGemasPorTiempo(imagen84,imagen04.getDrawable(),2400);
            //metodo aparece cartel
            Toast.makeText(getApplicationContext(),"Has ganado",Toast.LENGTH_SHORT).show();
            hasGanado = true;
            ganador();

        } else if (coloresCorrectos > 0) {
            imagen01.setEnabled(false);
            imagen02.setEnabled(false);
            imagen03.setEnabled(false);
            imagen04.setEnabled(false);
            hasGanado = false;
            Toast.makeText(getApplicationContext(),
                    "Hay " + coloresCorrectos + " colores correctos, pero solo "
                            + coloresEnPosicionCorrecta + " está(n) en la posición correcta."
                    ,Toast.LENGTH_SHORT).show();
            Log.i("Contraseña","Hay " + coloresCorrectos + " colores correctos, pero solo "
                    + coloresEnPosicionCorrecta + " está(n) en la posición correcta.");
            //calculo coloresCorrectos
            int verdes = coloresEnPosicionCorrecta;
            int rojos = coloresCorrectos-coloresEnPosicionCorrecta;
            infoColoresCorrectos(verdes,rojos, imagenGema01,imagenGema02,imagenGema03,imagenGema04);
        }
        boton01.setVisibility(View.INVISIBLE);
        return hasGanado;
    }

    private void infoColoresCorrectos(int verdes, int rojos,
                                      ImageView imagenGema01,ImageView imagenGema02,
                                      ImageView imagenGema03,ImageView imagenGema04) {
        Drawable drawableVerde = getDrawable(R.drawable.code_gema_verde);
        Drawable drawableRojo = getDrawable(R.drawable.code_gema_roja);
        switch (verdes){
            case 1:
                cambiarImagenesGemasPorTiempo(imagenGema01,drawableVerde,300);
                cambiarImagenesGemasPorTiempo(imagenGema02,drawableRojo,600);
                if (rojos > 1 ) {
                    cambiarImagenesGemasPorTiempo(imagenGema03,drawableRojo,900);
                    if (rojos > 2 ) {
                        cambiarImagenesGemasPorTiempo(imagenGema04,drawableRojo,1200);
                    }
                }
                break;

            case 2:
                cambiarImagenesGemasPorTiempo(imagenGema01,drawableVerde,300);
                cambiarImagenesGemasPorTiempo(imagenGema02,drawableVerde,600);
                if (rojos > 0 ) {
                    cambiarImagenesGemasPorTiempo(imagenGema03,drawableRojo,900);
                    if (rojos > 1 ) {
                        cambiarImagenesGemasPorTiempo(imagenGema04,drawableRojo,1200);
                    }
                }
                break;
            case 3:
                cambiarImagenesGemasPorTiempo(imagenGema01,drawableVerde,300);
                cambiarImagenesGemasPorTiempo(imagenGema02,drawableVerde,600);
                cambiarImagenesGemasPorTiempo(imagenGema03,drawableVerde,900);
                //No puede haber solo un rojo sino estarian todas correctas
                break;
            default:
                cambiarImagenesGemasPorTiempo(imagenGema01,drawableRojo,300);
                cambiarImagenesGemasPorTiempo(imagenGema02,drawableRojo,600);
                if (rojos > 2 ) {
                    cambiarImagenesGemasPorTiempo(imagenGema03,drawableRojo,900);
                    if (rojos > 3 ) {
                        cambiarImagenesGemasPorTiempo(imagenGema04,drawableRojo,1200);
                    }
                }
                break;
        }
    }

    private void cambiarImagenesGemasPorTiempo(ImageView imagen, Drawable drawable, int milis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imagen.setImageDrawable(drawable);
            }
        }, milis);
    }

    public void mostrarMenuPausa(View v) {
        linearPausa.setVisibility(View.VISIBLE);
        imagenFondoPausa.setVisibility(View.VISIBLE);
    }

    public void ocultarMenuPausa(View v) {
        linearPausa.setVisibility(View.INVISIBLE);
        imagenFondoPausa.setVisibility(View.INVISIBLE);
    }

    private void ganador() {
        confetti.setVisibility(View.VISIBLE);
        textoGanador.setVisibility(View.VISIBLE);
        ee_monGan.setText(""+monedasGanadas);
        ee_monAnt.setText(""+progreso.getMonedas());
        progreso.setMonedas(progreso.getMonedas()+monedasGanadas);
        ee_monAct.setText(""+progreso.getMonedas());
        progreso.setMinijuego02Completado(true);
        animacionFinal();
    }

    public void animacionFinal(){
        // Escala
        ScaleAnimation animacion1 = new ScaleAnimation(0.5f, 1, 0.5f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animacion1.setDuration(2000);
        animacion1.setRepeatCount(1);

        AlphaAnimation animacion2 = new AlphaAnimation(0, 1);
        animacion2.setDuration(1000);
        animacion2.setStartOffset(500);

        textoGanador.startAnimation(animacion1);
        animacion1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Implementa este método si deseas realizar alguna acción al iniciar la animación
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                confetti.setVisibility(View.GONE);
                textoGanador.setVisibility(View.GONE);
                linearResultado.setVisibility(View.VISIBLE);
                linearResultado.startAnimation(animacion2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Implementa este método si deseas realizar alguna acción al repetir la animación
            }
        });
    }

    public void irAtras(View v) {
        atras();
    }

    public void cerrar(View v){
        atras();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        atras();
    }

    private void atras() {
        Intent intent = new Intent(this, Mundo.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void irAjustes(View v) {
        Intent intent = new Intent(this, Ajustes.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }
}