package com.example.allu.buscaminas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Allu on 15/04/2015.
 */
public class ResultActivity extends ActionBarActivity {

    private Button enviar,nueva,salir;
    private EditText email,asunto,mensaje;
    private boolean atrasSalir;
    private String fechaFin,log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);


        getSupportActionBar().setIcon(R.drawable.result);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5bc610")));

        Intent intent = getIntent();
        log=intent.getStringExtra("log");

        enviar = (Button)findViewById(R.id.enviar);
        nueva = (Button)findViewById(R.id.nueva);
        salir = (Button)findViewById(R.id.salir);

        email = (EditText)findViewById(R.id.email);
        asunto = (EditText)findViewById(R.id.fecha);
        mensaje = (EditText)findViewById(R.id.log);

        SimpleDateFormat fecha = new SimpleDateFormat("EEE d, yyyy HH:mm:ss a");
        fechaFin = fecha.format(new Date());

        asunto.setText(fechaFin);
        mensaje.setText(log);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().matches("")) Toast.makeText(ResultActivity.this, R.string.falloDestinatario, Toast.LENGTH_SHORT).show();
                else{
                    String[] to ={email.getText().toString()};
                    enviar(to,"Log - "+asunto.getText().toString(),mensaje.getText().toString());
                }
            }
        });

        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ConfigActivity.class);
                startActivity(intent);
                finish();
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void enviar(String[] to,String asunto, String mensaje) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }

    @Override
    public void onBackPressed() {
        if (atrasSalir) {
            super.onBackPressed();
            return;
        }
        this.atrasSalir = true;
        Toast.makeText(this, R.string.atrasToast, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("email", email.getText().toString());
        outState.putString("asunto", asunto.getText().toString());
        outState.putString("mensaje", mensaje.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        email.setText(savedInstanceState.getString("email"));
        asunto.setText(savedInstanceState.getString("asunto"));
        mensaje.setText(savedInstanceState.getString("mensaje"));

    }

}