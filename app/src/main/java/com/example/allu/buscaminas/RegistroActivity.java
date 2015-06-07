package com.example.allu.buscaminas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Allu on 02/06/2015.
 */
public class RegistroActivity extends ActionBarActivity {

    private TextView detalle;
    private Button volver;
    private boolean atrasSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_frag);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.result);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5bc610")));

        detalle = (TextView)findViewById(R.id.registroText);
        volver = (Button)findViewById(R.id.volverReg);
        volver.setVisibility(View.VISIBLE);

        detalle.setText(getIntent().getStringExtra("value"));

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroActivity.this, HistoryHostActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("reg", detalle.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        detalle.setText(savedInstanceState.getString("reg"));
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
}
