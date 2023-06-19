package com.example.aplicacionproyectofinal.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.menu.Menu;
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


public class Ajustes extends AppCompatActivity {
    private SeekBar volumeSeekBar;
    private AudioManager audioManager;
    private CheckBox cbMute;
    private int originalVolume;
    private boolean isMuted = false;
    private ImageView ivBorrarProgreso, ivGuardarProgreso;
    Progreso progreso;
    private FirebaseFirestore db;
    private CollectionReference progresoRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Objects.requireNonNull(getSupportActionBar()).hide();
        db = FirebaseFirestore.getInstance();
        progresoRef = db.collection("Progreso");
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);
        Intent intent = getIntent();
        progreso = (Progreso) intent.getSerializableExtra("progreso");
        ivGuardarProgreso = findViewById(R.id.ivGuardarProgreso);
        ivBorrarProgreso = findViewById(R.id.ivBorrarProgreso);
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (progreso == null){
            ivGuardarProgreso.setVisibility(View.INVISIBLE);
            ivBorrarProgreso.setVisibility(View.INVISIBLE);
        }
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                    cbMute.setChecked(false);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // poner a mute la app
        cbMute = findViewById(R.id.cbMute);
        cbMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !isMuted) {
                    isMuted = true;
                    originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                } else if (!isChecked && isMuted) {
                    isMuted = false;
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
                }
            }
        });
    }

    public void irAtras(View V) {
        finish();
    }
    public void guardar(View V) {
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
                }else{
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

    public void borrarProgreso(View V) {
        mostrarVentanaBorrarProgreso();
    }

    private void mostrarVentanaBorrarProgreso() {
        // Crear un AlertDialog.Builder para construir la ventana emergente
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Borrar progreso");
        builder.setMessage("¿Quieres borrar el progreso?");

        // Configurar el botón "Borrar partida"
        builder.setPositiveButton("Borrar partida", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Buscar el progreso en la base de datos y borrarlo
                borrarProgreso();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Salir sin guardar

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
                    //Borrar partida
                    String documentoId = document.getId();
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("Progreso").document(documentoId);
                    docRef.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Has borrado la partida " + progreso.getNombre(), Toast.LENGTH_SHORT).show();
                                    irAMenu();
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
                }else{
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
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al guardar el progreso", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void irAMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}