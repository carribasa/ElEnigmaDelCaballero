package com.example.aplicacionproyectofinal.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.ajustes.Ajustes;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.creditos.Creditos;
import com.example.aplicacionproyectofinal.historia.Historia;
import com.example.aplicacionproyectofinal.modelo.Heroe;
import com.example.aplicacionproyectofinal.mundo.Mundo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Menu extends AppCompatActivity {

    // Declaración de variables miembro
    ImageView btnEntrar, btnAtras_login;
    LinearLayout menu_linLayout, layout_login;
    EditText etNombre_login, etContrasenya_login;

    Progreso progreso;
    Heroe heroe;

    private FirebaseFirestore db;
    private CollectionReference progresoRef;
    Integer monedasIniciales = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);
        // Inicialización de variables miembro y obtención de referencias a elementos de la interfaz de usuario
        menu_linLayout = findViewById(R.id.linearPausa);
        etNombre_login = findViewById(R.id.etNombre_login);
        btnAtras_login = findViewById(R.id.btnAtras_login);
        btnEntrar = findViewById(R.id.btnNuevaPartida_login);
        etContrasenya_login = findViewById(R.id.etContrasenya_login);
        layout_login = findViewById(R.id.layout_login);
        layout_login.setVisibility(View.INVISIBLE);

        // Inicialización de la base de datos Firebase Firestore
        db = FirebaseFirestore.getInstance();
        progresoRef = db.collection("Progreso");
    }

    // Método para pasar a la actividad "Historia"
    public void pasarPagina_MenuHistoria() {
        Intent intent = new Intent(this, Historia.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    // Método para pasar a la actividad "Ajustes"
    public void pasarPagina_MenuAjustes(View v) {
        Intent cambiarVentana = new Intent(this, Ajustes.class);
        startActivity(cambiarVentana);
    }

    // Método para pasar a la actividad "Creditos"
    public void pasarPagina_MenuCreditos(View V) {
        Intent cambiarVentana = new Intent(this, Creditos.class);
        cambiarVentana.putExtra("creditos", "CRÉDITOS");
        startActivity(cambiarVentana);
    }

    public void btnNuevaPartidaMenu(View V) {
        menu_linLayout.setVisibility(View.INVISIBLE);
        layout_login.setVisibility(View.VISIBLE);
    }

    public void btnAtras(View V) {
        atras();
    }

    private void atras() {
        etNombre_login.setText("");
        etContrasenya_login.setText("");
        menu_linLayout.setVisibility(View.VISIBLE);
        layout_login.setVisibility(View.INVISIBLE);
    }

    public void comenzarPartida(View V) {
        String nombre = etNombre_login.getText().toString();
        String contrasenya = etContrasenya_login.getText().toString();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(contrasenya)) {
            Toast.makeText(getApplicationContext(), "Rellena los campos", Toast.LENGTH_LONG).show();
        } else {
            btnAtras_login.setEnabled(false);
            Query query = progresoRef.whereEqualTo("nombre", nombre)
                    .whereEqualTo("contrasenya", contrasenya)
                    .limit(1);
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot querySnapshot) {

                    if (!querySnapshot.isEmpty()) {
                        //crear dialog
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        mostrarVentanaEmergenteContinuarNuevaPartida(nombre, contrasenya, document);
                    } else {
                        //si no lo encuentra, crea nueva partida directamente
                        nuevaPartida(nombre, contrasenya);
                        //Toast.makeText(getApplicationContext(), "Nueva partida", Toast.LENGTH_LONG).show();
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
    }

    private void nuevaPartida(String nombre, String contrasenya) {
        progreso = new Progreso(nombre, contrasenya, false, false,
                false, false, false, false,
                monedasIniciales, 20, 5, 3, false);
        heroe = progreso.getHeroe();
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
                etNombre_login.setText("");
                etContrasenya_login.setText("");
                Toast.makeText(getApplicationContext(), "Bienvenid@ " + nombre, Toast.LENGTH_SHORT).show();
                pasarPagina_MenuHistoria();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al introducir lo datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void continuar(DocumentSnapshot document) {
        // Obtener los valores del documento cargado en variables locales
        String nombreCargado = document.getString("nombre");
        String contrasenyaCargada = document.getString("contrasenya");
        Boolean minijuego01Completado = document.getBoolean("minijuego01Completado");
        Boolean minijuego02Completado = document.getBoolean("minijuego02Completado");
        Boolean minijuego03Completado = document.getBoolean("minijuego03Completado");
        Boolean batalla01Completada = document.getBoolean("batalla01Completada");
        Boolean batalla02Completada = document.getBoolean("batalla02Completada");
        Boolean batalla03Completada = document.getBoolean("batalla03Completada");
        Integer monedas = document.getLong("monedas").intValue();
        Integer vida = document.getLong("vida").intValue();
        Integer ataque = document.getLong("ataque").intValue();
        Integer defensa = document.getLong("defensa").intValue();
        Boolean pocion = document.getBoolean("pocion");

        // Crear una instancia de Progreso con los valores obtenidos
        progreso = new Progreso(nombreCargado, contrasenyaCargada, minijuego01Completado,
                minijuego02Completado, minijuego03Completado, batalla01Completada, batalla02Completada,
                batalla03Completada, monedas, vida, ataque, defensa, pocion);

        // Pasar a la actividad "Mundo" y enviar el objeto "progreso" como extra
        Intent intent = new Intent(this, Mundo.class);
        intent.putExtra("progreso", progreso);
        startActivity(intent);
    }

    private void mostrarVentanaEmergenteContinuarNuevaPartida(String nombre, String contrasenya, DocumentSnapshot document) {
        // Crear un AlertDialog.Builder para construir la ventana emergente
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nueva Partida");
        builder.setMessage("¿Deseas continuar la partida, o comenzar una nueva (La partida actual se sobreescribirá)?");

        // Configurar el botón "Continuar partida"
        builder.setPositiveButton("Continuar partida", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar cuando se selecciona "Continuar partida"
                // Puedes agregar aquí la lógica correspondiente
                //Toast.makeText(getApplicationContext(), "Continuar partida", Toast.LENGTH_LONG).show();
                continuar(document);
            }
        });

        // Configurar el botón "Nueva partida"
        builder.setNegativeButton("Nueva partida", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar cuando se selecciona "Nueva partida"
                // Puedes agregar aquí la lógica correspondiente
                // Borrar el documento anterior
                String documentoId = document.getId();
                DocumentReference docRef = FirebaseFirestore.getInstance().collection("Progreso").document(documentoId);
                docRef.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // El documento se eliminó exitosamente
                                // Puedes realizar acciones adicionales si es necesario
                                //Toast.makeText(getApplicationContext(), "HA BORRADO", Toast.LENGTH_LONG).show();
                                nuevaPartida(nombre, contrasenya);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Ocurrió un error al intentar eliminar el documento
                                // Puedes manejar el error según tus necesidades
                                Toast.makeText(getApplicationContext(), "Error al acceder en la base de datos", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        // Configurar el botón "Atrás"
        builder.setNeutralButton("Atrás", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar cuando se selecciona "Atrás"
                // Puedes agregar aquí la lógica correspondiente
                atras();
            }
        });

        // Crear y mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}