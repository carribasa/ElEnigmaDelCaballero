package com.example.aplicacionproyectofinal.historia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplicacionproyectofinal.R;
import com.example.aplicacionproyectofinal.bbdd.Progreso;
import com.example.aplicacionproyectofinal.menu.Menu;
import com.example.aplicacionproyectofinal.mundo.Mundo;

import java.util.Objects;

public class Historia extends AppCompatActivity {

    //DECLARACION VIEWS
    ImageView his_flechapost, his_img1, his_imgIcono, his_caballero1;
    TextView his_texto;
    private String nombre;
    private String contrasenya;
    Progreso progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        //Quitar ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Quitar botones de navegacion
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        asignacionViews();
        ocultarImagenes();
        animarTexto();
        Intent intent = getIntent();

        progreso = (Progreso) intent.getSerializableExtra("progreso");
    }

    public void pasarPagina_HistoriaMundo (){
        Intent intent = new Intent(this, Mundo.class);
        intent.putExtra("progreso",progreso);
        startActivity(intent);
    }

    public void irAtras(View v) {
        finish();
    }

    private void asignacionViews(){
        his_flechapost = findViewById(R.id.his_flechapost);
        his_img1 = findViewById(R.id.his_img1);
        his_texto = findViewById(R.id.his_texto);
        his_imgIcono = findViewById(R.id.his_imgIcono);
        his_caballero1 = findViewById(R.id.his_caballero1);
    }

    public void cambiarTextoEImagen(View view){

        String tag = his_texto.getTag().toString();
        switch(tag){
            case "parte1":
                his_imgIcono.setVisibility(View.GONE);
                mostrarImagenes();
                his_img1.setImageResource(R.drawable.his_img1);
                his_texto.setText("Trata de un misterioso caballero que empieza un viaje en" +
                        " búsqueda de un tesoro legendario: ¡La Corona de la Luz!");
                his_texto.setTag("parte2");
                animarTexto();
                break;
            case "parte2":
                his_img1.setImageResource(R.drawable.his_corona);
                his_caballero1.setVisibility(View.GONE);
                his_texto.setText("La Corona de la Luz es una reliquia sagrada que se decía que " +
                        "tenía el poder de conceder la sabiduría e iluminación a quien la poseía.");
                his_texto.setTag("parte3");
                animarTexto();
                break;
            case "parte3":
                his_img1.setImageResource(R.drawable.his_img4);
                his_texto.setText("El caballero atraviesa bosques peligrosos, enfrentándose a trampas," +
                        " acertijos, pruebas y monstruos malvados en su camino.");
                his_texto.setTag("parte4");
                animarTexto();
                break;
            case "parte4":
                his_img1.setImageResource(R.drawable.his_img3);
                his_texto.setText("En una de sus aventuras, descubre un templo abandonado en las " +
                        "profundidades de una cueva donde encuentra un enigma que nadie había resuelto.");
                his_texto.setTag("parte5");
                animarTexto();
                break;
            case "parte5":
                his_img1.setImageResource(R.drawable.his_img6);
                his_caballero1.setVisibility(View.VISIBLE);
                his_caballero1.setImageResource(R.drawable.hist_caballero4);
                his_texto.setText("Después de adivinar el enigma, consigue la llave para abrir las" +
                        " puertas de la cueva, encontrándose con su tan anhelado tesoro!!!...");
                his_texto.setTag("parte6");
                animarTexto();
                break;
            case "parte6":
                pasarPagina_HistoriaMundo();
                break;
        }
    }

    private void ocultarImagenes(){
        his_img1.setVisibility(View.INVISIBLE);
        his_caballero1.setVisibility(View.INVISIBLE);
    }

    private void mostrarImagenes(){
        his_img1.setVisibility(View.VISIBLE);
        his_caballero1.setVisibility(View.VISIBLE);
    }

    /**
     * Animacion letras
     */
    public void animarTexto() {

        his_flechapost.setClickable(false);
        final String texto = his_texto.getText().toString();
        his_texto.setText("");
        final int tiempoEntreLetras = 20; // Tiempo en milisegundos entre la aparición de cada letra
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            int index = 0;
            @Override
            public void run() {

                if (index < texto.length()) {
                    char letra = texto.charAt(index);
                    String textoActual = his_texto.getText().toString();
                    textoActual += letra;
                    his_texto.setText(textoActual);
                    index++;
                    handler.postDelayed(this, tiempoEntreLetras);
                    if(index == (texto.length())){
                        his_flechapost.setClickable(true);
                    }
                }
            }
        };
        handler.postDelayed(runnable, tiempoEntreLetras);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}