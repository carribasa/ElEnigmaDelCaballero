package com.example.aplicacionproyectofinal.minijuego01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.ajustes.Ajustes;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.mundo.Mundo;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class EnigmaDeLaForja extends AppCompatActivity {
    // componentes
    private ImageView ivInstrucciones, ivMetal, ivMadera, ivAceptar, iconoInicioForja, letrasInicioForja,
            imagenFondoPausa, ivFondoCuentaAtras, btnMenuJuego, ivMaterial4, ivMaterial5, ivMaterial6, fondoInicioForja,
            ivFondoCuentaAtrasInicio, ivMaterial1, ivMaterial2, ivMaterial3, ivFondoTiempo, fondoPuntuacion1, fondoPuntuacion2, fondoPuntuacion3,
            ee_resultado, hacha, martillo, arco, colgante, armadura, hachaDoble, fondoPuntuacionFinal;
    private TextView tvInstrucciones, tvMetal, tvMadera, textoGanador;
    private int countInstrucciones = 0, countMetal = 0, countMadera = 0;
    private TextView tvTiempo, tvPuntuacion, tvTextoTutorial, tvCuentaAtrasInicio, ee_mongan, ee_monant, ee_monact;
    private GifImageView confetti;
    private LinearLayout linearTutorial, linearPausa;
    private FrameLayout linearResultado;
    private long tiempoRestante;
    private boolean juegoIniciado = false;
    private TableLayout tableLayoutForja;

    // atributos
    private CountDownTimer cuentaAtras;
    private int puntuacion = 0;
    Progreso progreso;
    private String textoTutorial = "Debemos ayudar al herrero a reunir tantas armas como podamos para poder combatir a los monstruos que habitan este mundo. Estas son las instrucciones: \n" +
            " 1- Al pulsar aceptar comenzara la cuenta atrás. \n" +
            " 2- Dentro del tiempo estipulado debemos tocar tantas veces el pergamino, el metal y la madera como se nos indique en la ventana de 'Material' (Si te pasas, se reiniciará el contador). \n" +
            " 3- Al terminar la cuenta antras, si hemos conseguido los suficientes materiales aparecera una nueva arma a la izquierda de la pantalla. \n" +
            " 4- Hay 6 armas para crear. Tienes que crearlas todas para ganar. \n" +
            "¡Mucha suerte!";

    Integer monedasGanadas = 400;
    Integer turno;
    private Integer segundosRestantes = 0;

    // ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigma_de_la_forja);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        Intent intent = getIntent();
        progreso = (Progreso) intent.getSerializableExtra("progreso");

        inicializador();

        animacionInicio();

        // OnClickListener para ivInstrucciones
        ivInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countInstrucciones++;
            }
        });

        // OnClickListener para ivMetal
        ivMetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countMetal++;
            }
        });

        // OnClickListener para ivMadera
        ivMadera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countMadera++;
            }
        });
    }

    private void inicializador() {
        // declaracion de componentes
        ivInstrucciones = findViewById(R.id.ivInstrucciones);
        ivInstrucciones.setVisibility(View.INVISIBLE);
        ivMetal = findViewById(R.id.ivMetal);
        ivMetal.setVisibility(View.INVISIBLE);
        ivMadera = findViewById(R.id.ivMadera);
        ivMadera.setVisibility(View.INVISIBLE);
        iconoInicioForja = findViewById(R.id.iconoInicioForja);
        letrasInicioForja = findViewById(R.id.letrasInicioForja);
        fondoInicioForja = findViewById(R.id.fondoInicioForja);
        btnMenuJuego = findViewById(R.id.btnMenuJuego);
        ivFondoCuentaAtrasInicio = findViewById(R.id.ivFondoCuentaAtrasInicio);
        ivMaterial1 = findViewById(R.id.ivMaterial1);
        ivMaterial1.setVisibility(View.INVISIBLE);
        ivMaterial2 = findViewById(R.id.ivMaterial2);
        ivMaterial2.setVisibility(View.INVISIBLE);
        ivMaterial3 = findViewById(R.id.ivMaterial3);
        ivMaterial3.setVisibility(View.INVISIBLE);
        ivMaterial4 = findViewById(R.id.ivMaterial4);
        ivMaterial4.setVisibility(View.INVISIBLE);
        ivMaterial5 = findViewById(R.id.ivMaterial5);
        ivMaterial5.setVisibility(View.INVISIBLE);
        ivMaterial6 = findViewById(R.id.ivMaterial6);
        ivMaterial6.setVisibility(View.INVISIBLE);
        ivFondoTiempo = findViewById(R.id.ivFondoTiempo);
        ivFondoTiempo.setVisibility(View.INVISIBLE);
        ivFondoCuentaAtrasInicio.setVisibility(View.INVISIBLE);
        tvCuentaAtrasInicio = findViewById(R.id.tvCuentaAtrasInicio);
        tvCuentaAtrasInicio.setVisibility(View.INVISIBLE);
        ivFondoCuentaAtras = findViewById(R.id.ivFondoCuentaAtras);
        imagenFondoPausa = findViewById(R.id.imagenFondoPausa);
        imagenFondoPausa.setVisibility(View.INVISIBLE);
        ivAceptar = findViewById(R.id.ivAceptar);
        tvPuntuacion = findViewById(R.id.tvPuntuacion);
        tvPuntuacion.setVisibility(View.INVISIBLE);
        tvMadera = findViewById(R.id.udsMadera);
        tvMadera.setVisibility(View.INVISIBLE);
        tvMetal = findViewById(R.id.udsMetal);
        tvMetal.setVisibility(View.INVISIBLE);
        tvInstrucciones = findViewById(R.id.udsInstrucciones);
        tvInstrucciones.setVisibility(View.INVISIBLE);
        tvTiempo = findViewById(R.id.tvTiempo);
        tvTiempo.setVisibility(View.INVISIBLE);
        linearTutorial = findViewById(R.id.linearTutorial);
        linearPausa = findViewById(R.id.linearPausa);
        tvTextoTutorial = findViewById(R.id.tvTextoTutorial);
        linearPausa.setVisibility(View.INVISIBLE);
        fondoPuntuacion1 = findViewById(R.id.fondoPuntuacion1);
        fondoPuntuacion1.setVisibility(View.INVISIBLE);
        fondoPuntuacion2 = findViewById(R.id.fondoPuntuacion2);
        fondoPuntuacion2.setVisibility(View.INVISIBLE);
        fondoPuntuacion3 = findViewById(R.id.fondoPuntuacion3);
        fondoPuntuacion3.setVisibility(View.INVISIBLE);
        textoGanador = findViewById(R.id.textoGanador);
        confetti = findViewById(R.id.confetti);
        linearResultado = findViewById(R.id.linearResultado);
        ee_resultado = findViewById(R.id.ee_resultado);
        ee_mongan = findViewById(R.id.ee_mongan);
        ee_monant = findViewById(R.id.ee_monant);
        ee_monact = findViewById(R.id.ee_monact);
        hacha = findViewById(R.id.forja_hacha01);
        martillo = findViewById(R.id.forja_martillo);
        arco = findViewById(R.id.forja_arco);
        colgante = findViewById(R.id.forja_colgante);
        armadura = findViewById(R.id.forja_armadura);
        hachaDoble = findViewById(R.id.forja_hacha02);
        tableLayoutForja = findViewById(R.id.tableLayoutForja);
        tableLayoutForja.setVisibility(View.INVISIBLE);
        fondoPuntuacionFinal = findViewById(R.id.fondoPuntuacionFinal);
        fondoPuntuacionFinal.setVisibility(View.INVISIBLE);
        turno = 0;
    }

    // CUENTA ATRAS
    public void cuentaAtrasInicio(long seconds) {
        cuentaAtras = new CountDownTimer(seconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                btnMenuJuego.setVisibility(View.INVISIBLE);
                tiempoRestante = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvCuentaAtrasInicio.setText(String.valueOf(secondsLeft + 1));
            }

            public void onFinish() {
                tvCuentaAtrasInicio.setVisibility(View.GONE);
                ivFondoCuentaAtrasInicio.setVisibility(View.GONE);
                ivFondoCuentaAtras.setVisibility(View.GONE);
                juegoIniciado = true;
                tvTiempo.setVisibility(View.VISIBLE);
                ivFondoTiempo.setVisibility(View.VISIBLE);
                ivMaterial1.setVisibility(View.VISIBLE);
                cuentaAtrasTurno1(10000);
            }
        }.start();
    }

    public void cuentaAtrasTurno1(long miliseconds) {
        cuentaAtras = new CountDownTimer(miliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                btnMenuJuego.setVisibility(View.VISIBLE);
                tvMadera.setVisibility(View.VISIBLE);
                tvMetal.setVisibility(View.VISIBLE);
                tvInstrucciones.setVisibility(View.VISIBLE);
                ivInstrucciones.setVisibility(View.VISIBLE);
                ivMetal.setVisibility(View.VISIBLE);
                ivMadera.setVisibility(View.VISIBLE);
                fondoPuntuacion1.setVisibility(View.VISIBLE);
                fondoPuntuacion2.setVisibility(View.VISIBLE);
                fondoPuntuacion3.setVisibility(View.VISIBLE);
                tableLayoutForja.setVisibility(View.VISIBLE);
                tvPuntuacion.setVisibility(View.VISIBLE);
                fondoPuntuacionFinal.setVisibility(View.VISIBLE);
                tiempoRestante = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTiempo.setText(String.valueOf(secondsLeft + 1));
                turno = 1;

                ivInstrucciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countInstrucciones++;
                        if (countInstrucciones > 10) {
                            countInstrucciones = 0;
                        }
                        tvInstrucciones.setText(String.valueOf(countInstrucciones));
                    }
                });
                ivMetal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMetal++;
                        if (countMetal > 12) {
                            countMetal = 0;
                        }
                        tvMetal.setText(String.valueOf(countMetal));
                    }
                });

                ivMadera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMadera++;
                        if (countMadera > 12) {
                            countMadera = 0;
                        }
                        tvMadera.setText(String.valueOf(countMadera));
                    }
                });
            }

            public void onFinish() {
                if (countInstrucciones >= 10 && countMetal >= 12 && countMadera >= 12) {
                    //ivArma1.setVisibility(View.VISIBLE);
                    puntuacion++;
                    tvPuntuacion.setText(String.valueOf(puntuacion));
                    hacha.setImageResource(R.drawable.forja_hacha);
                    reiniciarContadores();
                    cuentaAtrasTurno2(11000);
                } else {
                    Toast.makeText(EnigmaDeLaForja.this, "No has conseguido suficientes materiales.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EnigmaDeLaForja.this, "HAS PERDIDO", Toast.LENGTH_SHORT).show();
                    atras();
                }
            }
        }.start();
    }

    public void cuentaAtrasTurno2(long miliseconds) {
        cuentaAtras = new CountDownTimer(miliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                ivMaterial1.setVisibility(View.GONE);
                ivMaterial2.setVisibility(View.VISIBLE);
                tiempoRestante = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTiempo.setText(String.valueOf(secondsLeft + 1));
                turno = 2;

                ivInstrucciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countInstrucciones++;
                        if (countInstrucciones > 15) {
                            countInstrucciones = 0;
                        }
                        tvInstrucciones.setText(String.valueOf(countInstrucciones));
                    }
                });
                ivMetal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMetal++;
                        if (countMetal > 14) {
                            countMetal = 0;
                        }
                        tvMetal.setText(String.valueOf(countMetal));
                    }
                });

                ivMadera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMadera++;
                        if (countMadera > 14) {
                            countMadera = 0;
                        }
                        tvMadera.setText(String.valueOf(countMadera));
                    }
                });
            }

            public void onFinish() {
                if (countInstrucciones >= 15 && countMetal >= 14 && countMadera >= 14) {
                    puntuacion++;
                    tvPuntuacion.setText(String.valueOf(puntuacion));
                    martillo.setImageResource(R.drawable.forja_martillo);
                    cuentaAtrasTurno3(12000);
                    reiniciarContadores();
                } else {
                    Toast.makeText(EnigmaDeLaForja.this, "No has conseguido suficientes materiales.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EnigmaDeLaForja.this, "HAS PERDIDO", Toast.LENGTH_SHORT).show();
                    atras();
                }
            }
        }.start();
    }

    public void cuentaAtrasTurno3(long miliseconds) {
        cuentaAtras = new CountDownTimer(miliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                ivMaterial2.setVisibility(View.GONE);
                ivMaterial3.setVisibility(View.VISIBLE);
                tiempoRestante = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTiempo.setText(String.valueOf(secondsLeft + 1));
                turno = 3;
                ivInstrucciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countInstrucciones++;
                        if (countInstrucciones > 16) {
                            countInstrucciones = 0;
                        }
                        tvInstrucciones.setText(String.valueOf(countInstrucciones));
                    }
                });
                ivMetal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMetal++;
                        if (countMetal > 18) {
                            countMetal = 0;
                        }
                        tvMetal.setText(String.valueOf(countMetal));
                    }
                });

                ivMadera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMadera++;
                        if (countMadera > 17) {
                            countMadera = 0;
                        }
                        tvMadera.setText(String.valueOf(countMadera));
                    }
                });
            }

            public void onFinish() {
                if (countInstrucciones >= 16 && countMetal >= 18 && countMadera >= 17) {
                    puntuacion++;
                    tvPuntuacion.setText(String.valueOf(puntuacion));
                    arco.setImageResource(R.drawable.forja_arco);
                    reiniciarContadores();
                    cuentaAtrasTurno4(13000);
                } else {
                    Toast.makeText(EnigmaDeLaForja.this, "No has conseguido suficientes materiales.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EnigmaDeLaForja.this, "HAS PERDIDO", Toast.LENGTH_SHORT).show();
                    atras();
                }
            }
        }.start();
    }

    public void cuentaAtrasTurno4(long miliseconds) {
        cuentaAtras = new CountDownTimer(miliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                ivMaterial3.setVisibility(View.GONE);
                ivMaterial4.setVisibility(View.VISIBLE);
                tiempoRestante = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTiempo.setText(String.valueOf(secondsLeft + 1));
                turno = 4;
                ivInstrucciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countInstrucciones++;
                        if (countInstrucciones > 17) {
                            countInstrucciones = 0;
                        }
                        tvInstrucciones.setText(String.valueOf(countInstrucciones));
                    }
                });
                ivMetal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMetal++;
                        if (countMetal > 18) {
                            countMetal = 0;
                        }
                        tvMetal.setText(String.valueOf(countMetal));
                    }
                });

                ivMadera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMadera++;
                        if (countMadera > 18) {
                            countMadera = 0;
                        }
                        tvMadera.setText(String.valueOf(countMadera));
                    }
                });
            }

            public void onFinish() {
                if (countInstrucciones >= 17 && countMetal >= 18 && countMadera >= 18) {
                    puntuacion++;
                    tvPuntuacion.setText(String.valueOf(puntuacion));
                    colgante.setImageResource(R.drawable.forja_colgante);
                    reiniciarContadores();
                    cuentaAtrasTurno5(14000);
                } else {
                    Toast.makeText(EnigmaDeLaForja.this, "No has conseguido suficientes materiales.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EnigmaDeLaForja.this, "HAS PERDIDO", Toast.LENGTH_SHORT).show();
                    atras();
                }
            }
        }.start();
    }

    public void cuentaAtrasTurno5(long miliseconds) {
        cuentaAtras = new CountDownTimer(miliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                ivMaterial4.setVisibility(View.GONE);
                ivMaterial5.setVisibility(View.VISIBLE);
                tiempoRestante = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTiempo.setText(String.valueOf(secondsLeft + 1));
                turno = 5;
                ivInstrucciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countInstrucciones++;
                        if (countInstrucciones > 18) {
                            countInstrucciones = 0;
                        }
                        tvInstrucciones.setText(String.valueOf(countInstrucciones));
                    }
                });
                ivMetal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMetal++;
                        if (countMetal > 18) {
                            countMetal = 0;
                        }
                        tvMetal.setText(String.valueOf(countMetal));
                    }
                });

                ivMadera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMadera++;
                        if (countMadera > 19) {
                            countMadera = 0;
                        }
                        tvMadera.setText(String.valueOf(countMadera));
                    }
                });
            }

            public void onFinish() {
                if (countInstrucciones >= 18 && countMetal >= 18 && countMadera >= 19) {
                    puntuacion++;
                    tvPuntuacion.setText(String.valueOf(puntuacion));
                    armadura.setImageResource(R.drawable.forja_torso);
                    reiniciarContadores();
                    cuentaAtrasTurno6(15000);
                } else {
                    Toast.makeText(EnigmaDeLaForja.this, "No has conseguido suficientes materiales.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EnigmaDeLaForja.this, "HAS PERDIDO", Toast.LENGTH_SHORT).show();
                    atras();
                }
            }
        }.start();
    }

    public void cuentaAtrasTurno6(long miliseconds) {
        cuentaAtras = new CountDownTimer(miliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                ivMaterial5.setVisibility(View.GONE);
                ivMaterial6.setVisibility(View.VISIBLE);
                tiempoRestante = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTiempo.setText(String.valueOf(secondsLeft + 1));
                turno = 6;
                ivInstrucciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countInstrucciones++;
                        if (countInstrucciones > 20) {
                            countInstrucciones = 0;
                        }
                        tvInstrucciones.setText(String.valueOf(countInstrucciones));
                    }
                });
                ivMetal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMetal++;
                        if (countMetal > 20) {
                            countMetal = 0;
                        }
                        tvMetal.setText(String.valueOf(countMetal));
                    }
                });

                ivMadera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countMadera++;
                        if (countMadera > 20) {
                            countMadera = 0;
                        }
                        tvMadera.setText(String.valueOf(countMadera));
                    }
                });
            }

            public void onFinish() {
                if (countInstrucciones >= 20 && countMetal >= 20 && countMadera >= 20) {
                    puntuacion++;
                    tvPuntuacion.setText(String.valueOf(puntuacion));
                    hachaDoble.setImageResource(R.drawable.forja_hacha2);
                    ganador();
                } else {
                    Toast.makeText(EnigmaDeLaForja.this, "No has conseguido suficientes materiales.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EnigmaDeLaForja.this, "HAS PERDIDO", Toast.LENGTH_SHORT).show();
                    atras();
                }
            }
        }.start();
    }

    public void pausarCuentaAtras() {
        if (cuentaAtras != null) {
            cuentaAtras.cancel();
        }
    }

    public void reanudarCuentaAtras() {
        switch (turno){
            case 1:
                cuentaAtrasTurno1(tiempoRestante);
            case 2:
                cuentaAtrasTurno2(tiempoRestante);
            case 3:
                cuentaAtrasTurno3(tiempoRestante);
            case 4:
                cuentaAtrasTurno4(tiempoRestante);
            case 5:
                cuentaAtrasTurno5(tiempoRestante);
            case 6:
                cuentaAtrasTurno6(tiempoRestante);
        }
    }

    public void reiniciarContadores() {
        this.countInstrucciones = 0;
        this.tvInstrucciones.setText(String.valueOf(countInstrucciones));
        this.countMadera = 0;
        this.tvMadera.setText(String.valueOf(countMadera));
        this.countMetal = 0;
        this.tvMetal.setText(String.valueOf(countMetal));
    }

    // ANIMACION
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

    public void ocultarTutorial(View V) {
        if (juegoIniciado) {
            linearTutorial.setVisibility(View.INVISIBLE);
        } else {
            linearTutorial.setVisibility(View.INVISIBLE);
            cuentaAtrasInicio(3);
            tvCuentaAtrasInicio.setVisibility(View.VISIBLE);
            ivFondoCuentaAtrasInicio.setVisibility(View.VISIBLE);
            ivFondoCuentaAtras.setVisibility(View.VISIBLE);
        }
    }

    // BOTONES
    public void mostrarTutorial(View V) {
        linearTutorial.setVisibility(View.VISIBLE);
    }

    public void mostrarMenuPausa(View V) {
        linearPausa.setVisibility(View.VISIBLE);
        imagenFondoPausa.setVisibility(View.VISIBLE);
        tvTiempo.setText("");
        pausarCuentaAtras();
    }

    public void ocultarMenuPausa(View V) {
        linearPausa.setVisibility(View.INVISIBLE);
        imagenFondoPausa.setVisibility(View.INVISIBLE);
        reanudarCuentaAtras();
    }

    public void irAtras(View V) {
        atras();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        atras();
    }

    private void atras() {
        cuentaAtras.cancel();
        Intent intent = new Intent(this, Mundo.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
        finish();
    }

    private void ganador() {
        confetti.setVisibility(View.VISIBLE);
        textoGanador.setVisibility(View.VISIBLE);
        ee_mongan.setText("" + monedasGanadas);
        ee_monant.setText("" + progreso.getMonedas());
        progreso.setMonedas(progreso.getMonedas() + monedasGanadas);
        ee_monact.setText("" + progreso.getMonedas());
        progreso.setMinijuego03Completado(true);
        animacionFinal();
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
        iconoInicioForja.startAnimation(animacion);

        ScaleAnimation animacionEscala = new ScaleAnimation(1, 0.5f, 1, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1);
        animacionEscala.setDuration(2000);
        animacionEscala.setRepeatCount(1);
        animacionEscala.setRepeatMode(Animation.REVERSE);

        AnimationSet setAnimaciones = new AnimationSet(true);
        setAnimaciones.addAnimation(animacionEscala);
        setAnimaciones.addAnimation(alphaAnimacion);
        letrasInicioForja.startAnimation(setAnimaciones);

        fondoInicioForja.startAnimation(alphaAnimacion);

        animacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Este método se ejecuta cuando la animación comienza
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Este método se ejecuta cuando la animación termina
                iconoInicioForja.setVisibility(View.GONE);
                letrasInicioForja.setVisibility(View.GONE);
                fondoInicioForja.setVisibility(View.GONE);
                animarTexto();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Este método se ejecuta cuando la animación se repite
            }
        });

    }

    public void animacionFinal() {
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
