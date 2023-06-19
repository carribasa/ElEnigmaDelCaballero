package com.example.aplicacionproyectofinal.mundo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.ajustes.Ajustes;
import com.example.aplicacionproyectofinal.batalla.Batalla;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.creditos.Creditos;
import com.example.aplicacionproyectofinal.menu.Menu;
import com.example.aplicacionproyectofinal.minijuego01.EnigmaDeLaForja;
import com.example.aplicacionproyectofinal.minijuego02.EnigmaEntrecruzado;
import com.example.aplicacionproyectofinal.minijuego03.CodigoEnigmatico;
import com.example.aplicacionproyectofinal.tienda.Tienda;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Mundo extends AppCompatActivity {
    ImageButton batalla01, batalla02, batalla03, tienda, enigmaEntreC, codigoEnig, enigmaDeLaF;

    TextView mun_nomHeroe;
    TextView mun_numMoneda;
    Progreso progreso;
    private FirebaseFirestore db;
    private CollectionReference progresoRef;
    LinearLayout linearPausa;
    ImageView imagenFondoPausa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mundo);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);
        // Inicialización de la base de datos Firebase Firestore
        db = FirebaseFirestore.getInstance();
        progresoRef = db.collection("Progreso");
        inicializador();
        //cargaDeBaseDeDatos();
        Intent intent = getIntent();
        progreso = (Progreso) intent.getSerializableExtra("progreso");
        mun_nomHeroe.setText(progreso.getNombre());
        mun_numMoneda.setText(progreso.getMonedas().toString());
        desactivarFasesSegúnProgreso();
    }

    private void inicializador() {
        batalla01 = findViewById(R.id.mun_botonBatalla01);
        batalla02 = findViewById(R.id.mun_botonBatalla02);
        batalla03 = findViewById(R.id.mun_botonBatalla03);
        tienda = findViewById(R.id.mun_botonTienda);
        enigmaEntreC = findViewById(R.id.mun_botonEnigEnt);
        codigoEnig = findViewById(R.id.mun_botonCodEnig);
        enigmaDeLaF = findViewById(R.id.mun_botonEnigmaForja);
        mun_nomHeroe = findViewById(R.id.mun_nomHeroe);
        mun_numMoneda = findViewById(R.id.mun_numMoneda);
        imagenFondoPausa = findViewById(R.id.imagenFondoPausa);
        linearPausa = findViewById(R.id.linearPausaMundo);
        /**
        //Comentar para presentacion
         enigmaEntreC.setEnabled(false);
         batalla01.setEnabled(false);
         codigoEnig.setEnabled(false);
         batalla02.setEnabled(false);
         enigmaDeLaF.setEnabled(false);
         batalla03.setEnabled(false);
         */
    }

    private void desactivarFasesSegúnProgreso() {
        Animation translacion = new TranslateAnimation(0, 0, 0, -20);
        translacion.setRepeatMode(Animation.REVERSE);
        translacion.setRepeatCount(Animation.INFINITE);
        translacion.setDuration(400);
        tienda.startAnimation(translacion);

        if (!progreso.getMinijuego01Completado()) {
            enigmaEntreC.setEnabled(true);
            enigmaEntreC.startAnimation(translacion);
        } else {
            if (!progreso.getBatalla01Completada()) {
                batalla01.setEnabled(true);
                batalla01.startAnimation(translacion);
            } else {
                if (!progreso.getMinijuego02Completado()) {
                    codigoEnig.setEnabled(true);
                    codigoEnig.startAnimation(translacion);
                } else {
                    if (!progreso.getBatalla02Completada()) {
                        batalla02.setEnabled(true);
                        batalla02.startAnimation(translacion);
                    } else {
                        if (!progreso.getMinijuego03Completado()) {
                            enigmaDeLaF.setEnabled(true);
                            enigmaDeLaF.startAnimation(translacion);
                        } else {
                            if (!progreso.getBatalla03Completada()) {
                                batalla03.setEnabled(true);
                                batalla03.startAnimation(translacion);
                            } else {
                                Intent cambiarVentana = new Intent(this, Creditos.class);
                                cambiarVentana.putExtra("creditos", "FIN :-)");
                                startActivity(cambiarVentana);
                            }
                        }
                    }
                }
            }
        }
        /**
         * DESCOMENTAR PARA PODER VER ANIMACIONES DE TODO
         enigmaEntreC.startAnimation(translacion);
         batalla01.startAnimation(translacion);
         codigoEnig.startAnimation(translacion);
         batalla02.startAnimation(translacion);
         enigmaDeLaF.startAnimation(translacion);
         batalla03.startAnimation(translacion);
         */
    }

    public void pasarPagina_MundoTienda(View v) {
        Intent intent = new Intent(this, Tienda.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void pasarPagina_MundoEnigmaEntrecruzado(View v) {
        Intent intent = new Intent(this, EnigmaEntrecruzado.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void pasarPagina_MundoEnigmaDeLaForja(View v) {
        Intent intent = new Intent(this, EnigmaDeLaForja.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void pasarPagina_MundoCodEnig(View v) {
        Intent intent = new Intent(this, CodigoEnigmatico.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void pasarPagina_MundoBatalla(View v) {
        String batalla = v.getTag().toString();
        Intent intent = new Intent(this, Batalla.class);
        intent.putExtra("batalla", batalla);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void irAtras(View V) {
        mostrarVentanaGuardarProgreso();
    }

    @Override
    public void onBackPressed() {
        mostrarVentanaGuardarProgreso();
    }

    private void mostrarVentanaGuardarProgreso() {
        // Crear un AlertDialog.Builder para construir la ventana emergente
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guardar progreso");
        builder.setMessage("¿Quieres guardar el progreso antes de salir?");

        // Configurar el botón "Guardar partida"
        builder.setPositiveButton("Guardar partida", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Buscar el progreso en la base de datos y borrarlo
                borrarProgreso();
            }
        });

        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Salir sin guardar
                salir();
            }
        });

        // Configurar el botón "Atrás"
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //cancelar
            }
        });

        // Crear y mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void borrarProgreso() {
        Query query = progresoRef.whereEqualTo("nombre", progreso.getNombre())
                .whereEqualTo("contrasenya", progreso.getContrasenya())
                .limit(1);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {

                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    //Borrar partida anterior
                    String documentoId = document.getId();
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("Progreso").document(documentoId);
                    docRef.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Se ha borrado el progreso
                                    //Guardar Partida
                                    guardarProgreso();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Ocurrió un error al intentar eliminar el documento
                                    // Puedes manejar el error según tus necesidades
                                    Toast.makeText(getApplicationContext(), "Error al conectar con la base de datos", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Error al conectar con la base de datos",
                            Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Ocurrió un error al consultar la base de datos
                Toast.makeText(getApplicationContext(), "Error al conectar con la base de datos",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void guardarProgreso() {
        //Guardar en la base de datos
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", progreso.getNombre());
        map.put("contrasenya", progreso.getContrasenya());
        map.put("minijuego01Completado", progreso.getMinijuego01Completado());
        map.put("minijuego02Completado", progreso.getMinijuego02Completado());
        map.put("minijuego03Completado", progreso.getMinijuego03Completado());
        map.put("batalla01Completada", progreso.getBatalla01Completada());
        map.put("batalla02Completada", progreso.getBatalla02Completada());
        map.put("batalla03Completada", progreso.getBatalla03Completada());
        map.put("monedas", progreso.getMonedas());
        map.put("vida", progreso.getHeroe().getVida());
        map.put("ataque", progreso.getHeroe().getAtaque());
        map.put("defensa", progreso.getHeroe().getDefensa());
        map.put("pocion", progreso.getHeroe().getPocion());

        db.collection("Progreso").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Has guardado la partida " + progreso.getNombre(), Toast.LENGTH_SHORT).show();
                //Volver al menu principal
                salir();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al guardar el progreso", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void salir() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void irAjustes(View view) {
        Intent intent = new Intent(this, Ajustes.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    public void ocultarMenuPausa(View view) {
        linearPausa.setVisibility(View.INVISIBLE);
        imagenFondoPausa.setVisibility(View.INVISIBLE);
    }

    public void mostrarMenuPausa(View v) {
        linearPausa.setVisibility(View.VISIBLE);
        imagenFondoPausa.setVisibility(View.VISIBLE);
    }

}