package com.example.aplicacionproyectofinal.tienda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.mundo.Mundo;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class Tienda extends AppCompatActivity {

    //DECLARACION VIEWS
    ImageView tien_espada01, tien_espada02, tien_espada03, tien_pocion01, tien_pocion02, tien_escudo01,
            tien_escudo02, tien_escudo03, tien_pergamino, tien_btn_cerrar, tien_imgObjeto, tien_atras;
    Button tien_btn_comprar;
    TextView tien_infoAtaque2, tien_infoAtaque, tien_infoPrecio, tien_infoPrecio2, tien_pociones,
            tien_info_monedas;
    Progreso progreso;

    RelativeLayout tien_compra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        asignacionViews();
        ocultarComponentesPergamino();
        Intent intent = getIntent();
        progreso = (Progreso) intent.getSerializableExtra("progreso");
        tien_info_monedas.setText(progreso.getMonedas().toString());
    }

    private void asignacionViews(){

        tien_espada01 = findViewById(R.id.tien_espada01);
        tien_espada02 = findViewById(R.id.tien_espada02);
        tien_espada03 = findViewById(R.id.tien_espada03);
        tien_pocion01 = findViewById(R.id.tien_pocion01);
        tien_pocion02 = findViewById(R.id.tien_pocion02);
        tien_escudo01 = findViewById(R.id.tien_escudo01);
        tien_escudo02 = findViewById(R.id.tien_escudo02);
        tien_escudo03 = findViewById(R.id.tien_escudo03);
        tien_pergamino = findViewById(R.id.tien_pergamino);
        tien_btn_cerrar = findViewById(R.id.tien_btn_cerrar);
        tien_btn_comprar = findViewById(R.id.tien_btn_comprar);
        tien_imgObjeto = findViewById(R.id.tien_imgObjeto);
        tien_infoAtaque = findViewById(R.id.tien_infoAtaque);
        tien_infoAtaque2 = findViewById(R.id.tien_infoAtaque2);
        tien_infoPrecio = findViewById(R.id.tien_infoPrecio);
        tien_infoPrecio2 = findViewById(R.id.tien_infoPrecio2);
        tien_info_monedas = findViewById(R.id.tien_info_monedas);
        tien_atras = findViewById(R.id.tien_atras);
        tien_pociones = findViewById(R.id.tien_pociones);
        tien_compra = findViewById(R.id.tien_compra);
    }

    public void infoEspada1(View view){
        mostrarComponentesPergamino();
        tien_pociones.setVisibility(View.INVISIBLE);
        tien_infoAtaque.setText("Ataque:");
        tien_infoAtaque2.setText("+ 2");
        tien_infoPrecio.setText("Precio:");
        tien_infoPrecio2.setText("100");
        tien_imgObjeto.setImageResource(R.drawable.obj_espada_mad);
        tien_imgObjeto.setTag("espada1");
    }

    public void infoEspada2(View view){
        mostrarComponentesPergamino();
        tien_pociones.setVisibility(View.INVISIBLE);
        tien_infoAtaque.setText("Ataque:");
        tien_infoAtaque2.setText("+ 4");
        tien_infoPrecio.setText("Precio:");
        tien_infoPrecio2.setText("300");
        tien_imgObjeto.setImageResource(R.drawable.obj_daga);
        tien_imgObjeto.setTag("espada2");
    }

    public void infoEspada3(View view){
        mostrarComponentesPergamino();
        tien_pociones.setVisibility(View.INVISIBLE);
        tien_infoAtaque.setText("Ataque:");
        tien_infoAtaque2.setText("+ 7");
        tien_infoPrecio.setText("Precio:");
        tien_infoPrecio2.setText("600");
        tien_imgObjeto.setImageResource(R.drawable.obj_espada_buena);
        tien_imgObjeto.setTag("espada3");
    }

    public void infoEscudo1(View view){
        mostrarComponentesPergamino();
        tien_pociones.setVisibility(View.INVISIBLE);
        tien_infoAtaque.setText("Defensa:");
        tien_infoAtaque2.setText("+ 2");
        tien_infoPrecio.setText("Precio:");
        tien_infoPrecio2.setText("100");
        tien_imgObjeto.setImageResource(R.drawable.obj_escudo_madera);
        tien_imgObjeto.setTag("escudo1");
    }

    public void infoEscudo2(View view){
        mostrarComponentesPergamino();
        tien_pociones.setVisibility(View.INVISIBLE);
        tien_infoAtaque.setText("Defensa:");
        tien_infoAtaque2.setText("+ 4");
        tien_infoPrecio.setText("Precio:");
        tien_infoPrecio2.setText("300");
        tien_imgObjeto.setImageResource(R.drawable.obj_escudo_semi);
        tien_imgObjeto.setTag("escudo2");
    }

    public void infoEscudo3(View view){
        mostrarComponentesPergamino();
        tien_pociones.setVisibility(View.INVISIBLE);
        tien_infoAtaque.setText("Defensa:");
        tien_infoAtaque2.setText("+ 7");
        tien_infoPrecio.setText("Precio:");
        tien_infoPrecio2.setText("600");
        tien_imgObjeto.setImageResource(R.drawable.obj_escudo_bueno);
        tien_imgObjeto.setTag("escudo3");
    }

    public void infoPocion1(View view){
        mostrarComponentesPergamino();
        tien_infoAtaque.setText("");
        tien_infoAtaque2.setText("");
        tien_pociones.setVisibility(View.VISIBLE);
        tien_pociones.setText("Regenera 10 de vida");
        tien_infoPrecio.setText("Precio:");
        tien_infoPrecio2.setText("50");
        tien_imgObjeto.setImageResource(R.drawable.obj_pocion01);
        tien_imgObjeto.setTag("pocionv");
    }

    public void infoPocion2(View view){
        mostrarComponentesPergamino();
        tien_infoAtaque.setText("");
        tien_infoAtaque2.setText("");
        tien_pociones.setVisibility(View.VISIBLE);
        tien_pociones.setText("+5 de vida sobre el máximo");
        tien_infoPrecio.setText("Precio:");
        tien_infoPrecio2.setText("300");
        tien_imgObjeto.setImageResource(R.drawable.obj_pocion02);
        tien_imgObjeto.setTag("pocionr");
    }

    public void cerrar(View view){
        ocultarComponentesPergamino();
    }

    public void ocultarComponentesPergamino(){
        tien_compra.setVisibility(View.GONE);
        tien_pociones.setVisibility(View.INVISIBLE);
    }

    public void mostrarComponentesPergamino(){
        tien_compra.setVisibility(View.VISIBLE);
    }

    public void irAtras(View v) {
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

    public void comprar(View v){

        Integer precio = Integer.parseInt(tien_infoPrecio2.getText().toString());
        Progreso nuevoProgreso = progreso;

        switch (tien_imgObjeto.getTag().toString()){
            case "espada1":
            case "escudo1":
                Toast.makeText(getApplicationContext(), "Ya dispones de este objeto, compra cancelada", Toast.LENGTH_LONG).show();
                ocultarComponentesPergamino();
                tien_pociones.setVisibility(View.INVISIBLE);
                break;
            case "espada2":
                if (progreso.getHeroe().getAtaque() >= 7){
                    Toast.makeText(getApplicationContext(), "Ya dispones de este objeto, compra cancelada", Toast.LENGTH_LONG).show();
                    ocultarComponentesPergamino();
                    tien_pociones.setVisibility(View.INVISIBLE);
                }else{
                    nuevoProgreso.getHeroe().setAtaque(7);
                    realizarCompra(precio, nuevoProgreso);
                }
                break;
            case "espada3":
                if (progreso.getHeroe().getAtaque() == 10){
                    Toast.makeText(getApplicationContext(), "Ya dispones de este objeto, compra cancelada", Toast.LENGTH_LONG).show();
                    ocultarComponentesPergamino();
                    tien_pociones.setVisibility(View.INVISIBLE);
                }else{
                    nuevoProgreso.getHeroe().setAtaque(10);
                    realizarCompra(precio, nuevoProgreso);
                }
                break;
            case "escudo2":
                if (progreso.getHeroe().getDefensa() >= 5){
                    Toast.makeText(getApplicationContext(), "Ya dispones de este objeto, compra cancelada", Toast.LENGTH_LONG).show();
                    ocultarComponentesPergamino();
                    tien_pociones.setVisibility(View.INVISIBLE);
                }else{
                    nuevoProgreso.getHeroe().setDefensa(5);
                    realizarCompra(precio,nuevoProgreso);
                }
                break;
            case "escudo3":
                if (progreso.getHeroe().getDefensa() == 8){
                    Toast.makeText(getApplicationContext(), "Ya dispones de este objeto, compra cancelada", Toast.LENGTH_LONG).show();
                    ocultarComponentesPergamino();
                    tien_pociones.setVisibility(View.INVISIBLE);
                }else{
                    nuevoProgreso.getHeroe().setDefensa(8);
                    realizarCompra(precio,nuevoProgreso);
                }
                break;
            case "pocionv":
                if (progreso.getHeroe().getPocion()){
                    Toast.makeText(getApplicationContext(), "Ya dispones de este objeto, compra cancelada", Toast.LENGTH_LONG).show();
                    ocultarComponentesPergamino();
                    tien_pociones.setVisibility(View.INVISIBLE);
                }else{
                    nuevoProgreso.getHeroe().setPocion(true);
                    realizarCompra(precio,nuevoProgreso);
                }
                break;
            case "pocionr":
                    nuevoProgreso.getHeroe().setVida(nuevoProgreso.getHeroe().getVida()+5);
                    realizarCompra(precio,nuevoProgreso);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error en la compra", Toast.LENGTH_LONG).show();
                break;
        }
    }
    private void realizarCompra(Integer precio, Progreso nuevoProgreso) {
        if(progreso.getMonedas() >= precio){
            progreso.setMonedas(progreso.getMonedas() - precio);
            Toast.makeText(getApplicationContext(), "Compra Realizada", Toast.LENGTH_LONG).show();
            ocultarComponentesPergamino();
            tien_info_monedas.setText(progreso.getMonedas().toString());
            progreso = nuevoProgreso;
        }else{
            Toast.makeText(getApplicationContext(), "Saldo insuficiente", Toast.LENGTH_LONG).show();
            ocultarComponentesPergamino();
        }
    }

    public void confirmacionCompra(View v) {

        // Crear un AlertDialog.Builder para construir la ventana emergente
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación de compra");
        builder.setMessage("¿Deseas continuar con la compra?");

        // Configurar el botón "No"
        builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar cuando se selecciona "Atrás"
                ocultarComponentesPergamino();
            }
        });

        // Configurar el botón "Si"
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comprar(v);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}