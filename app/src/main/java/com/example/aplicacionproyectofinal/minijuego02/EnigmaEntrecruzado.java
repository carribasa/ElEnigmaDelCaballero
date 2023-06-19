package com.example.aplicacionproyectofinal.minijuego02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.ajustes.Ajustes;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.mundo.Mundo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import pl.droidsonroids.gif.GifImageView;

public class EnigmaEntrecruzado extends AppCompatActivity {

    static final int NUMERO_PALABRAS = 10;
    static final int NUMERO_LETRAS = 6;
    String palabraClave = "";
    String sumaLetras = "";
    Set<String> claves;
    int contadorFilas = 0;
    int columna = 0;
    int contadorPalabrasOk = 0;
    Map<String, String> preguntas = new HashMap<>();
    Map<String, String> palabrasUtilizadas = new TreeMap<>();

    private String textoTutorial = "Debes descifrar el enigma que existe en las respuestas a las preguntas que te hacemos: \n " +
            "1- Si ya sabes la respuesta a la pregunta que tienes abajo, debes escribirla con el teclado de la derecha pulsando sobre las letras. \n " +
            "2- Si te equivocas solo debes pinchar en la palabra 'Borrar'. \n " +
            "3- Si la palabra que has escrito es correcta, te aparecerá un check marcado en la casilla gris y podrás seguir con la siguiente pregunta. \n " +
            "4- Si no sabes la respuesta de alguna pregunta, solo pincha en la la flecha que apunta a la derecha. \n " +
            "5- Cuando completes las 10 palabras enigmáticas, habrás ganado.  \n " +
            "¡Mucha suerte!";

    View view;
    ImageView ee_a, ee_b, ee_c, ee_d, ee_e, ee_f, ee_g, ee_h, ee_i, ee_j, ee_k, ee_l, ee_m
            , ee_n, ee_o, ee_p, ee_q, ee_r, ee_s, ee_t, ee_u, ee_v, ee_w, ee_x, ee_y, ee_z
            , ee_borrar, ee_cuadroabecedario, ee_flechader, fondoInicio, ee_icono, imagenFondoPausa
            , ee_cerrar, ee_resultado;

    GifImageView confetti;
    TextView ee_textopregunta, tvTextoTutorial, textoGanador, ee_titulo, ee_monGan, ee_monAnt,ee_monAct;
    TableLayout tablacrucigrama;
    LinearLayout linearTutorial, linearPausa;
    FrameLayout linearResultado;
    Progreso progreso;
    Integer monedasGanadas = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigma_entrecruzado);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        Intent intent = getIntent();
        progreso = (Progreso) intent.getSerializableExtra("progreso");
        inicializador();
        lecturaFichero(view);
        generadorPalabra();
        obtenerFoco(contadorFilas);
        animacionInicio();

    }

    //ASIGNACION DE VIEWS
    public void inicializador() {
        ee_borrar = findViewById(R.id.ee_borrar);
        ee_a = findViewById(R.id.ee_a);
        ee_b = findViewById(R.id.ee_b);
        ee_c = findViewById(R.id.ee_c);
        ee_d = findViewById(R.id.ee_d);
        ee_e = findViewById(R.id.ee_e);
        ee_f = findViewById(R.id.ee_f);
        ee_g = findViewById(R.id.ee_g);
        ee_h = findViewById(R.id.ee_h);
        ee_i = findViewById(R.id.ee_i);
        ee_j = findViewById(R.id.ee_j);
        ee_k = findViewById(R.id.ee_k);
        ee_l = findViewById(R.id.ee_l);
        ee_m = findViewById(R.id.ee_m);
        ee_n = findViewById(R.id.ee_n);
        ee_o = findViewById(R.id.ee_o);
        ee_p = findViewById(R.id.ee_p);
        ee_q = findViewById(R.id.ee_q);
        ee_r = findViewById(R.id.ee_r);
        ee_s = findViewById(R.id.ee_s);
        ee_t = findViewById(R.id.ee_t);
        ee_u = findViewById(R.id.ee_u);
        ee_v = findViewById(R.id.ee_v);
        ee_w = findViewById(R.id.ee_w);
        ee_x = findViewById(R.id.ee_x);
        ee_y = findViewById(R.id.ee_y);
        ee_z = findViewById(R.id.ee_z);
        ee_cuadroabecedario = findViewById(R.id.ee_cuadroabecedario);
        tablacrucigrama = findViewById(R.id.tablacrucigrama);
        ee_textopregunta = findViewById(R.id.ee_textopregunta);
        ee_flechader = findViewById(R.id.ee_flechader);
        tvTextoTutorial = findViewById(R.id.tvTextoTutorial);
        linearTutorial = findViewById(R.id.linearTutorial);
        fondoInicio = findViewById(R.id.fondoInicio);
        ee_icono = findViewById(R.id.ee_icono);
        ee_titulo = findViewById(R.id.ee_titulo);
        imagenFondoPausa = findViewById(R.id.imagenFondoPausa);
        linearPausa = findViewById(R.id.linearPausa);
        linearResultado = findViewById(R.id.linearResultado);
        confetti = findViewById(R.id.confetti);
        textoGanador = findViewById(R.id.textoGanador);
        ee_cerrar = findViewById(R.id.ee_cerrar);
        ee_resultado = findViewById(R.id.ee_resultado);
        ee_monGan = findViewById(R.id.ee_mongan);
        ee_monAnt = findViewById(R.id.ee_monant);
        ee_monAct = findViewById(R.id.ee_monact);
    }

    public void lecturaFichero(View view) {

        AssetManager assetManager = getAssets();
        InputStream is = null;
        BufferedReader br = null;

        try {
            is = assetManager.open("preguntasyrespuestas.txt");
            br = new BufferedReader(new InputStreamReader(is));
            String lineaLeida;
            while ((lineaLeida = br.readLine()) != null) {
                String[] pyr = lineaLeida.split(":");
                preguntas.put(pyr[1], pyr[0]);
            }
            claves = preguntas.keySet();
        } catch (FileNotFoundException e) {
            System.out.println("El fichero no existe");
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void asignacionLetrasAImageView(View v) {

        if (v == ee_a) {
            llamarLetraView(ee_a, 'A');
        }
        if (v == ee_b) {
            llamarLetraView(ee_b, 'B');
        }
        if (v == ee_c) {
            llamarLetraView(ee_c, 'C');
        }
        if (v == ee_d) {
            llamarLetraView(ee_d, 'D');
        }
        if (v == ee_e) {
            llamarLetraView(ee_e, 'E');
        }
        if (v == ee_f) {
            llamarLetraView(ee_f, 'F');
        }
        if (v == ee_g) {
            llamarLetraView(ee_g, 'G');
        }
        if (v == ee_h) {
            llamarLetraView(ee_h, 'H');
        }
        if (v == ee_i) {
            llamarLetraView(ee_i, 'I');
        }
        if (v == ee_j) {
            llamarLetraView(ee_j, 'J');
        }
        if (v == ee_k) {
            llamarLetraView(ee_k, 'K');
        }
        if (v == ee_l) {
            llamarLetraView(ee_l, 'L');
        }
        if (v == ee_m) {
            llamarLetraView(ee_m, 'M');
        }
        if (v == ee_n) {
            llamarLetraView(ee_n, 'N');
        }
        if (v == ee_o) {
            llamarLetraView(ee_o, 'O');
        }
        if (v == ee_p) {
            llamarLetraView(ee_p, 'P');
        }
        if (v == ee_q) {
            llamarLetraView(ee_q, 'Q');
        }
        if (v == ee_r) {
            llamarLetraView(ee_r, 'R');
        }
        if (v == ee_s) {
            llamarLetraView(ee_s, 'S');
        }
        if (v == ee_t) {
            llamarLetraView(ee_t, 'T');
        }
        if (v == ee_u) {
            llamarLetraView(ee_u, 'U');
        }
        if (v == ee_v) {
            llamarLetraView(ee_v, 'V');
        }
        if (v == ee_w) {
            llamarLetraView(ee_w, 'W');
        }
        if (v == ee_x) {
            llamarLetraView(ee_x, 'X');
        }
        if (v == ee_y) {
            llamarLetraView(ee_y, 'Y');
        }
        if (v == ee_z) {
            llamarLetraView(ee_z, 'Z');
        }
    }

    private void llamarLetraView(View v, Character letra) {
        if (sumaLetras.length() != NUMERO_LETRAS) {
            sumaLetras = sumaLetras + letra;
            comprobarCasillaSig((ImageView) v, contadorFilas);
            comprobarPalabraOk();
        }
    }

    public String generadorPalabra() {

        if (contadorPalabrasOk != NUMERO_PALABRAS) {
            Random random = new Random();
            int numAleatorio = random.nextInt(105) + 1;
            int contador = 1;

            for (String clave : claves) {
                if (contador == numAleatorio) {
                    if (palabrasUtilizadas.size() == 0) {
                        palabrasUtilizadas.put(clave, preguntas.get(clave));
                        palabraClave = clave;
                        ee_textopregunta.setText(preguntas.get(clave));
                        break;
                    } else {
                        if (!palabrasUtilizadas.containsKey(clave)) {
                            palabrasUtilizadas.put(clave, preguntas.get(clave));
                            palabraClave = clave;
                            ee_textopregunta.setText(preguntas.get(clave));
                            break;
                        } else {
                            numAleatorio = random.nextInt(105) + 1;
                            contador = 0;
                        }
                    }
                }
                contador++;
            }
        }
        return palabraClave;
    }

    public void borrarLetra(View v) {
        int longitud = sumaLetras.length();
        try {
            if (longitud != 1) {
                sumaLetras = sumaLetras.substring(0, longitud - 1);
            } else {
                sumaLetras = "";
            }
            volverCasillaAnt();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void obtenerFoco(int fila) {

        if (contadorFilas != 10) {
            if (fila > 0) {
                TableRow row = (TableRow) tablacrucigrama.getChildAt(fila - 1);
                View celda = row.getChildAt(0);
                celda.setBackgroundColor(Color.TRANSPARENT);
            }
            TableRow row = (TableRow) tablacrucigrama.getChildAt(fila);
            View celda = row.getChildAt(0);
            celda.setBackgroundColor(Color.YELLOW);
            columna = 0;
        } else {
            if (contadorPalabrasOk == 10) {
                ganador();
            }
        }
    }

    public void comprobarPalabraOk() {

        if (contadorPalabrasOk != 10) {
            if (sumaLetras.length() == NUMERO_LETRAS) {
                if (palabraClave.equals(sumaLetras)) {
                    devolverCheckBoxFila();
                    contadorFilas++;
                    contadorPalabrasOk++;
                    sumaLetras = "";
                    generadorPalabra();
                    obtenerFoco(contadorFilas);
                }
            }
        } else {
            if (palabraClave.equals(sumaLetras) && contadorPalabrasOk == 10) {
                ganador();
            }
        }
    }

    private void ganador() {
        confetti.setVisibility(View.VISIBLE);
        textoGanador.setVisibility(View.VISIBLE);
        ee_monGan.setText(""+monedasGanadas);
        ee_monAnt.setText(""+progreso.getMonedas());
        progreso.setMonedas(progreso.getMonedas()+monedasGanadas);
        ee_monAct.setText(""+progreso.getMonedas());
        progreso.setMinijuego01Completado(true);
        animacionFinal();
    }

    public void comprobarCasillaSig(ImageView iv, int filas) {

        if (contadorFilas != 10 || contadorPalabrasOk != 10) {
            TableRow tableRow = (TableRow) tablacrucigrama.getChildAt(filas);
            View view = tableRow.getChildAt(columna);
            if (view instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) view;
                if (relativeLayout.getChildCount() > 0) {
                    View posicionIV = relativeLayout.getChildAt(1);
                    if (posicionIV instanceof ImageView) {
                        ImageView segundoImageView = (ImageView) posicionIV;
                        segundoImageView.setImageDrawable(iv.getDrawable());
                        if (columna < 6) {
                            columna++;
                        }
                    }
                }
            }
        }
    }

    public void volverCasillaAnt() {

        TableRow tableRow = (TableRow) tablacrucigrama.getChildAt(contadorFilas);
        if (sumaLetras.length() != NUMERO_LETRAS) {
            View view = tableRow.getChildAt(columna - 1);
            if (view instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) view;
                if (relativeLayout.getChildCount() > 0) {
                    View posicionIV = relativeLayout.getChildAt(1);
                    if (posicionIV instanceof ImageView) {
                        ImageView segundoImageView = (ImageView) posicionIV;
                        segundoImageView.setImageDrawable(null);
                        if (columna > 0) {
                            columna--;
                        } else if (columna == 0) {
                            columna = 0;
                        } else {
                            columna = 0;
                        }
                    }
                }
            }
        } else {
            View view = tableRow.getChildAt(columna - 1); //5
            if (view instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) view;
                if (relativeLayout.getChildCount() > 0) {
                    View posicionIV = relativeLayout.getChildAt(1);
                    if (posicionIV instanceof ImageView) {
                        ImageView segundoImageView = (ImageView) posicionIV;
                        segundoImageView.setImageDrawable(null);
                        columna = columna - 1; //5
                    }
                }
            }
        }
    }

    public void devolverCheckBoxFila() {

        CheckBox miCheck = null;
        TableRow fila = (TableRow) tablacrucigrama.getChildAt(contadorFilas);
        View celda = fila.getChildAt(NUMERO_LETRAS);
        if (celda instanceof CheckBox) {
            miCheck = (CheckBox) celda;
            miCheck.setEnabled(true);
            miCheck.setChecked(true);
            miCheck.setEnabled(false);
        }
    }

    public void palabraPosterior(View iv) {
        generadorPalabra();
        borrarFila();
    }

    public void borrarFila() {

        columna = 0;
        sumaLetras = "";

        for (int i = 0; i < NUMERO_LETRAS; i++) {
            TableRow tableRow = (TableRow) tablacrucigrama.getChildAt(contadorFilas);
            View view = tableRow.getChildAt(i);
            if (view instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) view;
                if (relativeLayout.getChildCount() > 0) {
                    View posicionIV = relativeLayout.getChildAt(1);
                    if (posicionIV instanceof ImageView) {
                        ImageView segundoImageView = (ImageView) posicionIV;
                        segundoImageView.setImageDrawable(null);
                    }
                }
            }
        }
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
        ee_icono.startAnimation(animacion);

        ScaleAnimation animacionEscala = new ScaleAnimation(1, 0.5f, 1, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1);
        animacionEscala.setDuration(2000);
        animacionEscala.setRepeatCount(1);
        animacionEscala.setRepeatMode(Animation.REVERSE);

        AnimationSet setAnimaciones = new AnimationSet(true);
        setAnimaciones.addAnimation(animacionEscala);
        setAnimaciones.addAnimation(alphaAnimacion);
        ee_titulo.startAnimation(setAnimaciones);

        fondoInicio.startAnimation(alphaAnimacion);

        animacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Este método se ejecuta cuando la animación comienza
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Este método se ejecuta cuando la animación termina
                ee_icono.setVisibility(View.GONE);
                ee_titulo.setVisibility(View.GONE);
                fondoInicio.setVisibility(View.GONE);
                animarTexto();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Este método se ejecuta cuando la animación se repite
            }
        });
    }

    public void mostrarMenuPausa(View v) {
        linearPausa.setVisibility(View.VISIBLE);
        imagenFondoPausa.setVisibility(View.VISIBLE);
    }

    public void ocultarMenuPausa(View v) {
        linearPausa.setVisibility(View.INVISIBLE);
        imagenFondoPausa.setVisibility(View.INVISIBLE);
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

    public void irAjustes(View v) {
        Intent intent = new Intent(this, Ajustes.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }
}