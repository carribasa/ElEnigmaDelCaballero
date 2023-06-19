package com.example.aplicacionproyectofinal.batalla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.ajustes.Ajustes;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.modelo.Heroe;
import com.example.aplicacionproyectofinal.modelo.Monstruo;
import com.example.aplicacionproyectofinal.mundo.Mundo;

import java.util.Objects;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class Batalla extends AppCompatActivity {
    //Victoria
    boolean hasGanado = false;
    int monedasGanadas = 0;
    //Cartel
    TextView textoCartel;
    //Heroe
    GifImageView jugadorImagen;
    TextView nombrePersonaje, vidaPersonaje, ataquePersonaje, defensaPersonaje;
    ProgressBar barraVidaPersonaje;
    ImageView pocion;
    //Cartas
    ImageView cartaEspada, dadoCartaEspada, cartaEscudo, dadoCartaEscudo, cartaCura, dadoCartaCura;
    //Monstruo
    GifImageView monstruoImagen;
    TextView nombreMonstruo, vidaMonstruo, ataqueMonstruo, defensaMonstruo;
    ProgressBar barraVidaMonstruo;
    Heroe jugador;
    Monstruo monstruo;
    //dados
    GifImageView dado01IzqGif, dado02DerGif;
    ImageView dado01Izq, dado02Der;
    Drawable drawDado;
    //control de estados y elecciones
    int estado = 0;
    //Resultado dados
    int resultadoDado01Izq = 0;
    int resultadoDado02Der = 0;
    private int turno;
    Progreso progreso;
    private String batalla;
    // pausa
    private LinearLayout linearPausa;
    private ImageView imagenPausaBatalla;
    //tutorial
    private LinearLayout linearTutorial;
    TextView tvTextoTutorial;
    String textoTutorial = "¡Debemos derrotar a los enemigos que nos encontramos por el camino! \n \n" +
            " 1- La batalla se desarrolla por turnos \n" +
            " 2- Abajo de cada contrincante puedes ver el total de vida, ataque y defensa \n" +
            " 3- Tira los dados 2 veces y en cada tirada elige la carta para la que \n" +
            " quieres destinar ese número \n" +
            " 4- Después será el turno del enemigo, que hará lo mismo que tú  \n" +
            " 5- Y a luchaaarrrr !!!,  \n" +
            " Cuanto mejor lo hagamos, mayor será la recompensa que el herrero nos otorgue \n" +
            "              ¡Mucha suerte!";
    //animaciones
    GifImageView confetti;
    ImageView bat_icono, fondoInicio;
    TextView textoGanador, bat_titulo, ee_mongan, ee_monant, ee_monact;
    FrameLayout linearResultado;
    Integer monedasGanadas01 = 550;
    Integer monedasGanadas02 = 700;
    Integer monedasGanadas03 = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalla);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        Intent intent = getIntent();
        progreso = (Progreso) intent.getSerializableExtra("progreso");
        batalla = intent.getStringExtra("batalla");
        linearPausa = findViewById(R.id.linearPausa);
        linearPausa.setVisibility(View.INVISIBLE);
        imagenPausaBatalla = findViewById(R.id.imagenPausaBatalla);
        imagenPausaBatalla.setVisibility(View.INVISIBLE);
        linearTutorial = findViewById(R.id.linearTutorial);
        tvTextoTutorial = findViewById(R.id.tvTextoTutorial);
        inicializador();
        cargaDeDatos();
        estado = 1;
        turno = 1;
        animarTexto();
        animacionInicio();

    }

    private void inicializador() {
        //Cartel
        textoCartel = findViewById(R.id.bat_textoCartel);
        //Heroe
        jugadorImagen = findViewById(R.id.bat_player_gif);
        nombrePersonaje = findViewById(R.id.bat_textNombrePlayer);
        barraVidaPersonaje = findViewById(R.id.bat_progressBarHPPlayer);
        vidaPersonaje = findViewById(R.id.bat_textProgressPlayer);
        ataquePersonaje = findViewById(R.id.bat_ataquePersonaje);
        defensaPersonaje = findViewById(R.id.bat_defensaPersonaje);
        pocion = findViewById(R.id.bat_pocionPersonaje);
        //Cartas
        cartaEspada = findViewById(R.id.bat_carta01);
        dadoCartaEspada = findViewById(R.id.bat_dado_carta01);
        cartaEscudo = findViewById(R.id.bat_carta02);
        dadoCartaEscudo = findViewById(R.id.bat_dado_carta02);
        cartaCura = findViewById(R.id.bat_carta03);
        dadoCartaCura = findViewById(R.id.bat_dado_carta03);
        //Monstruo
        monstruoImagen = findViewById(R.id.bat_troll_gif);
        nombreMonstruo = findViewById(R.id.bat_textNombreMonstruo);
        barraVidaMonstruo = findViewById(R.id.bat_progressBarHPMonstruo);
        vidaMonstruo = findViewById(R.id.bat_textProgressMonstruo);
        ataqueMonstruo = findViewById(R.id.bat_ataqueMonstruo);
        defensaMonstruo = findViewById(R.id.bat_defensaMonstruo);
        //dados
        dado01IzqGif = findViewById(R.id.bat_dado01IzqGif);
        dado01Izq = findViewById(R.id.bat_dado01Izq);
        dado02DerGif = findViewById(R.id.bat_dado02DerGif);
        dado02DerGif.setEnabled(false);
        dado02Der = findViewById(R.id.bat_dado02Der);
        //animaciones
        bat_icono = findViewById(R.id.bat_icono);
        bat_titulo = findViewById(R.id.bat_titulo);
        fondoInicio = findViewById(R.id.fondoInicio);
        confetti = findViewById(R.id.confetti);
        textoGanador = findViewById(R.id.textoGanador);
        linearResultado = findViewById(R.id.linearResultado);
        ee_mongan = findViewById(R.id.ee_mongan);
        ee_monant = findViewById(R.id.ee_monant);
        ee_monact = findViewById(R.id.ee_monact);

    }

    private void cargaDeDatos() {
        cargaDeDatosHeroe();
        cargaDeDatosEnemigo();
    }

    private void cargaDeDatosEnemigo() {
        if (Objects.equals(batalla, "1")) {
            monstruo = new Monstruo("TROLL".toUpperCase(), 20, 5, 5, R.drawable.bat_troll);
        } else if (Objects.equals(batalla, "2")) {
            monstruo = new Monstruo("ESPINETE".toUpperCase(), 30, 6, 7, R.drawable.bat_monstruo01);
        } else {
            monstruo = new Monstruo("DRAGONAZO".toUpperCase(), 50, 11, 8, R.drawable.bat_dragon);
        }
        nombreMonstruo.setText(monstruo.getNombre());
        barraVidaMonstruo.setMax(monstruo.getVida());
        barraVidaMonstruo.setProgress(monstruo.getVidaActual());
        vidaMonstruo.setText("PS " + monstruo.getVidaActual() + "/" + monstruo.getVida());
        ataqueMonstruo.setText(monstruo.getAtaque() + "");
        defensaMonstruo.setText(monstruo.getDefensa() + "");
        monstruoImagen.setImageResource(monstruo.getImagen());
    }

    private void cargaDeDatosHeroe() {
        jugador = progreso.getHeroe();
        jugador.setVidaActual(jugador.getVida());
        nombrePersonaje.setText(jugador.getNombre());
        barraVidaPersonaje.setMax(jugador.getVida());
        barraVidaPersonaje.setProgress(jugador.getVidaActual());
        vidaPersonaje.setText("PS " + jugador.getVidaActual() + "/" + jugador.getVida());
        ataquePersonaje.setText(jugador.getAtaque() + "");
        defensaPersonaje.setText(jugador.getDefensa() + "");
        if (!jugador.getPocion()) {
            pocion.setVisibility(View.INVISIBLE);
        }
        if (jugador.getAtaque() == 7) {
            cartaEspada.setImageResource(R.drawable.bat_carta_esp02);
        } else if (jugador.getAtaque() == 10) {
            cartaEspada.setImageResource(R.drawable.bat_carta_esp03);
        } else {
            cartaEspada.setImageResource(R.drawable.bat_carta_esp01);
        }

        if (jugador.getDefensa() == 5) {
            cartaEscudo.setImageResource(R.drawable.bat_carta_esc02);
        } else if (jugador.getDefensa() == 8) {
            cartaEscudo.setImageResource(R.drawable.bat_carta_esc03);
        } else {
            cartaEscudo.setImageResource(R.drawable.bat_carta_esc01);
        }
    }

    public void tiradaDadoIzq(View view) {
        dado01IzqGif.setEnabled(false);
        dado01IzqGif.setVisibility(View.INVISIBLE);
        generarNumeroAleatorio(dado01Izq);
        dado01Izq.setVisibility(View.VISIBLE);
    }

    public void tiradaDadoDer(View view) {
        dado02DerGif.setVisibility(View.INVISIBLE);
        generarNumeroAleatorio(dado02Der);
        dado02Der.setVisibility(View.VISIBLE);
    }

    public void generarNumeroAleatorio(ImageView dado) {
        int resultado = 0;
        Random random = new Random();
        resultado = random.nextInt(6) + 1;
        if (dado.getTag().equals("dadoIzq")) {
            resultadoDado01Izq = resultado;
        } else {
            resultadoDado02Der = resultado;
        }
        switch (resultado) {
            case 1:
                dado.setImageResource(R.drawable.bat_dado01);
                drawDado = dado.getDrawable();
                break;
            case 2:
                dado.setImageResource(R.drawable.bat_dado02);
                drawDado = dado.getDrawable();
                break;
            case 3:
                dado.setImageResource(R.drawable.bat_dado03);
                drawDado = dado.getDrawable();
                break;
            case 4:
                dado.setImageResource(R.drawable.bat_dado04);
                drawDado = dado.getDrawable();
                break;
            case 5:
                dado.setImageResource(R.drawable.bat_dado05);
                drawDado = dado.getDrawable();
                break;
            default:
                dado.setImageResource(R.drawable.bat_dado06);
                drawDado = dado.getDrawable();
                break;
        }
    }

    public void eleccionCarta(View carta) {
        if (estado == 1) {
            if (drawDado != null) {
                if (carta == cartaEspada) {
                    cartaEspada.setEnabled(false);
                    dadoCartaEspada.setImageDrawable(drawDado);
                    jugador.setAtaqueActual(jugador.getAtaque() + resultadoDado01Izq);
                    //ataquePersonaje.setText(jugador.getAtaque()+"+" + resultadoDado01Izq);
                    ataquePersonaje.setText(jugador.getAtaque() + resultadoDado01Izq + "");
                }
                if (carta == cartaEscudo) {
                    cartaEscudo.setEnabled(false);
                    dadoCartaEscudo.setImageDrawable(drawDado);
                    jugador.setDefensaActual(jugador.getDefensa() + resultadoDado01Izq);
                    //defensaPersonaje.setText(jugador.getDefensa()+"+" + resultadoDado01Izq);
                    defensaPersonaje.setText(jugador.getDefensa() + resultadoDado01Izq + "");
                    //defensaPersonaje.setTextColor(Color.GREEN);
                }
                if (carta == cartaCura) {
                    cartaCura.setEnabled(false);
                    dadoCartaCura.setImageDrawable(drawDado);
                    //rellenar barra de vida
                    int vidaNueva = resultadoDado01Izq + jugador.getVidaActual();
                    if (vidaNueva <= jugador.getVida()) {
                        jugador.setVidaActual(vidaNueva);
                    } else {
                        jugador.setVidaActual(jugador.getVida());
                    }
                    barraVidaPersonaje.setProgress(jugador.getVidaActual());
                    vidaPersonaje.setText("PS " + jugador.getVidaActual() + "/" + jugador.getVida());
                }
                dado01Izq.setVisibility(View.INVISIBLE);
                dado02DerGif.setEnabled(true);
                drawDado = null;
                estado = 2;
            }
        } else {
            if (drawDado != null) {
                if (carta == cartaEspada) {
                    cartaEspada.setEnabled(false);
                    dadoCartaEspada.setImageDrawable(drawDado);
                    jugador.setAtaqueActual(jugador.getAtaque() + resultadoDado02Der);
                    //ataquePersonaje.setText(jugador.getAtaque()+"+" + resultadoDado02Der);
                    ataquePersonaje.setText(jugador.getAtaque() + resultadoDado02Der + "");
                    //ataquePersonaje.setTextColor(Color.GREEN);
                }
                if (carta == cartaEscudo) {
                    cartaEscudo.setEnabled(false);
                    dadoCartaEscudo.setImageDrawable(drawDado);
                    jugador.setDefensaActual(jugador.getDefensa() + resultadoDado02Der);
                    //defensaPersonaje.setText(jugador.getDefensa()+"+" + resultadoDado02Der);
                    defensaPersonaje.setText(jugador.getDefensa() + resultadoDado02Der + "");

                }
                if (carta == cartaCura) {
                    cartaCura.setEnabled(false);
                    dadoCartaCura.setImageDrawable(drawDado);
                    //rellenar barra de vida
                    int vidaNueva = resultadoDado02Der + jugador.getVidaActual();
                    if (vidaNueva <= jugador.getVida()) {
                        jugador.setVidaActual(vidaNueva);
                    } else {
                        jugador.setVidaActual(jugador.getVida());
                    }
                    barraVidaPersonaje.setProgress(jugador.getVidaActual());
                    vidaPersonaje.setText("PS " + jugador.getVidaActual() + "/" + jugador.getVida());
                }
                ataquePersonaje.setTextColor(Color.GREEN);
                defensaPersonaje.setTextColor(Color.GREEN);
                dado02Der.setVisibility(View.INVISIBLE);
                drawDado = null;
                estado = 1;
                turnoMonstruo();
            }
        }
    }

    private void turnoMonstruo() {
        cartaEspada.setVisibility(View.INVISIBLE);
        dadoCartaEspada.setVisibility(View.INVISIBLE);
        cartaEscudo.setVisibility(View.INVISIBLE);
        dadoCartaEscudo.setVisibility(View.INVISIBLE);
        cartaCura.setVisibility(View.INVISIBLE);
        dadoCartaCura.setVisibility(View.INVISIBLE);
        textoCartel.setText("MONSTRUO: TURNO " + turno);
        Random random = new Random();
        int resultadoAtaque = random.nextInt(6) + 1;
        monstruo.setAtaqueActual(resultadoAtaque + monstruo.getAtaque());
        //ataqueMonstruo.setText(monstruo.getAtaque()+"+" + resultadoAtaque);
        ataqueMonstruo.setText(monstruo.getAtaque() + resultadoAtaque + "");
        ataqueMonstruo.setTextColor(Color.GREEN);
        int resultadoDefensa = random.nextInt(6) + 1;
        monstruo.setDefensaActual(resultadoDefensa + monstruo.getDefensa());
        defensaMonstruo.setText(monstruo.getDefensa() + resultadoDefensa + "");
        //defensaMonstruo.setText(monstruo.getDefensa()+"+" + resultadoDefensa);
        defensaMonstruo.setTextColor(Color.GREEN);
        calculoDaño();
    }

    private void calculoDaño() {
        Animation translacion = new TranslateAnimation(0, 600, 0, 0);
        translacion.setRepeatMode(Animation.REVERSE);
        translacion.setRepeatCount(1);
        translacion.setDuration(3000);
        jugadorImagen.startAnimation(translacion);

        Animation translacion2 = new TranslateAnimation(0, -600, 0, 0);
        translacion2.setRepeatMode(Animation.REVERSE);
        translacion2.setRepeatCount(1);
        translacion2.setDuration(3000);
        monstruoImagen.startAnimation(translacion2);

        textoCartel.setText("CALCULO DAÑO: TURNO " + turno);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int resultadoAtaqueJugador = 0;
                int resultadoAtaqueMonstruo = 0;
                if (jugador.getAtaqueActual() > monstruo.getDefensaActual()) {
                    resultadoAtaqueJugador = jugador.getAtaqueActual() - monstruo.getDefensaActual();
                    monstruo.setVidaActual(monstruo.getVidaActual() - resultadoAtaqueJugador);
                    barraVidaMonstruo.setProgress(monstruo.getVidaActual());
                    vidaMonstruo.setText("PS " + monstruo.getVidaActual() + "/" + monstruo.getVida());
                }
                if (monstruo.getAtaqueActual() > jugador.getDefensaActual()) {
                    resultadoAtaqueMonstruo = monstruo.getAtaqueActual() - jugador.getDefensaActual();
                    jugador.setVidaActual(jugador.getVidaActual() - resultadoAtaqueMonstruo);
                    barraVidaPersonaje.setProgress(jugador.getVidaActual());
                    vidaPersonaje.setText("PS " + jugador.getVidaActual() + "/" + jugador.getVida());
                }

                if (jugador.getVidaActual() <= 0 || monstruo.getVidaActual() <= 0) {
                    if (jugador.getVidaActual() <= 0) {
                        jugador.setVidaActual(0);

                        if (monstruo.getVidaActual() <= 0) {
                            monstruo.setVidaActual(0);
                            //empate
                            Toast.makeText(getApplicationContext(), "HAS EMPATADO, Vuelve a intentarlo.", Toast.LENGTH_LONG).show();
                            atras();
                        } else {
                            Toast.makeText(getApplicationContext(), "HAS PERDIDO, Vuelve a intentarlo.", Toast.LENGTH_LONG).show();
                            atras();
                        }
                    } else if (monstruo.getVidaActual() <= 0) {
                        monstruo.setVidaActual(0);
                        if (jugador.getVidaActual() <= 0) {
                            jugador.setVidaActual(0);
                            //empate
                            Toast.makeText(getApplicationContext(), "HAS EMPATADO, Vuelve a intentarlo.", Toast.LENGTH_LONG).show();
                            atras();
                        } else {

                            hasGanado = true;
                            ee_monant.setText(""+progreso.getMonedas());
                            if (Objects.equals(batalla, "1")) {
                                progreso.setBatalla01Completada(hasGanado);
                                monedasGanadas = monedasGanadas01;
                            } else if (Objects.equals(batalla, "2")) {
                                progreso.setBatalla02Completada(hasGanado);
                                monedasGanadas = monedasGanadas02;
                            } else {
                                progreso.setBatalla03Completada(hasGanado);
                                monedasGanadas = monedasGanadas03;
                            }
                            progreso.setMonedas(progreso.getMonedas() + monedasGanadas);
                            confetti.setVisibility(View.VISIBLE);
                            textoGanador.setVisibility(View.VISIBLE);
                            ee_mongan.setText(""+monedasGanadas);
                            ee_monact.setText(""+progreso.getMonedas());
                            animacionFinal();
                        }
                    }
                    barraVidaPersonaje.setProgress(jugador.getVidaActual());
                    vidaPersonaje.setText("PS " + jugador.getVidaActual() + "/" + jugador.getVida());
                    barraVidaMonstruo.setProgress(monstruo.getVidaActual());
                    vidaMonstruo.setText("PS " + monstruo.getVidaActual() + "/" + monstruo.getVida());
                } else {
                    cambioDeTurno();
                }
            }
        }, 6000);

    }

    private void cambioDeTurno() {
        jugador.setAtaqueActual(jugador.getAtaque());
        jugador.setDefensaActual(jugador.getDefensa());
        monstruo.setAtaqueActual(monstruo.getAtaque());
        monstruo.setDefensaActual(monstruo.getDefensa());
        ataquePersonaje.setText(jugador.getAtaqueActual() + "");
        defensaPersonaje.setText(jugador.getDefensaActual() + "");
        ataqueMonstruo.setText(monstruo.getAtaqueActual() + "");
        defensaMonstruo.setText(monstruo.getDefensaActual() + "");
        ataquePersonaje.setTextColor(Color.WHITE);
        defensaPersonaje.setTextColor(Color.WHITE);
        ataqueMonstruo.setTextColor(Color.WHITE);
        defensaMonstruo.setTextColor(Color.WHITE);
        turno++;
        textoCartel.setText("JUGADOR: TURNO " + turno);
        dado01IzqGif.setVisibility(View.VISIBLE);
        dado02DerGif.setVisibility(View.VISIBLE);
        dado01IzqGif.setEnabled(true);
        dado02DerGif.setEnabled(false);
        cartaEspada.setVisibility(View.VISIBLE);
        dadoCartaEspada.setVisibility(View.VISIBLE);
        dadoCartaEspada.setImageResource(android.R.drawable.screen_background_light);
        cartaEscudo.setVisibility(View.VISIBLE);
        dadoCartaEscudo.setVisibility(View.VISIBLE);
        dadoCartaEscudo.setImageResource(android.R.drawable.screen_background_light);
        cartaCura.setVisibility(View.VISIBLE);
        dadoCartaCura.setVisibility(View.VISIBLE);
        dadoCartaCura.setImageResource(android.R.drawable.screen_background_light);
        cartaEspada.setEnabled(true);
        cartaEscudo.setEnabled(true);
        cartaCura.setEnabled(true);
    }

    public void usoPocion(View v) {
        if (jugador.getVidaActual() <= 0) {
            pocion.setEnabled(false);
        } else {
            //rellenar barra de vida
            int vidaNueva = 10 + jugador.getVidaActual();
            if (vidaNueva <= jugador.getVida()) {
                jugador.setVidaActual(vidaNueva);
            } else {
                jugador.setVidaActual(jugador.getVida());
            }
            barraVidaPersonaje.setProgress(jugador.getVidaActual());
            vidaPersonaje.setText("PS " + jugador.getVidaActual() + "/" + jugador.getVida());
            pocion.setEnabled(false);
            pocion.setVisibility(View.INVISIBLE);
            jugador.setPocion(false);
        }
    }
    //mostrar datos en el turno monstruo que es lo que ha sacado aunque sea por pantalla
    //calculo de daño

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        atras();
    }

    private void atras() {
        progreso.getHeroe().setVidaActual(progreso.getHeroe().getVida());
        Intent intent = new Intent(this, Mundo.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void mostrarMenuPausa(View V) {
        linearPausa.setVisibility(View.VISIBLE);
        imagenPausaBatalla.setVisibility(View.VISIBLE);
    }

    public void ocultarMenuPausa(View V) {
        linearPausa.setVisibility(View.INVISIBLE);
        imagenPausaBatalla.setVisibility(View.INVISIBLE);
    }

    public void mostrarTutorial(View V) {
        linearTutorial.setVisibility(View.VISIBLE);
    }

    public void ocultarTutorial(View V) {
        linearTutorial.setVisibility(View.INVISIBLE);
    }

    public void irAjustes(View v) {
        Intent intent = new Intent(this, Ajustes.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void irAtras(View V) {
        atras();
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

        RotateAnimation rotacionAnimacion = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotacionAnimacion.setDuration(2000);
        rotacionAnimacion.setRepeatCount(1);

        // Escala
        ScaleAnimation escalaAnimacion = new ScaleAnimation(1, 0.5f, 1, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        escalaAnimacion.setDuration(2000);
        escalaAnimacion.setRepeatCount(1);
        escalaAnimacion.setRepeatMode(Animation.REVERSE);

        AlphaAnimation alphaAnimacion = new AlphaAnimation(1, 0);
        alphaAnimacion.setDuration(2000);
        alphaAnimacion.setStartOffset(3000);

        // Animación combinada
        AnimationSet animacion = new AnimationSet(true);
        animacion.addAnimation(rotacionAnimacion);
        animacion.addAnimation(escalaAnimacion);
        animacion.addAnimation(alphaAnimacion);
        bat_icono.startAnimation(animacion);

        ScaleAnimation animacionEscala = new ScaleAnimation(1, 0.5f, 1, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1);
        animacionEscala.setDuration(2000);
        animacionEscala.setRepeatCount(1);
        animacionEscala.setRepeatMode(Animation.REVERSE);

        AnimationSet setAnimaciones = new AnimationSet(true);
        setAnimaciones.addAnimation(animacionEscala);
        setAnimaciones.addAnimation(alphaAnimacion);
        bat_titulo.startAnimation(setAnimaciones);

        fondoInicio.startAnimation(alphaAnimacion);

        animacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Este método se ejecuta cuando la animación comienza
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Este método se ejecuta cuando la animación termina
                bat_icono.setVisibility(View.GONE);
                bat_titulo.setVisibility(View.GONE);
                fondoInicio.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Este método se ejecuta cuando la animación se repite
            }
        });
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
}