package com.example.allu.buscaminas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Allu on 15/04/2015.
 */
public class ConfigActivity extends ActionBarActivity {

    private Button jugar;
    private EditText alias,parrilla;
    private TextView tiempo;
    private SeekBar seekBar;
    private Spinner spinner;
    public String opcion;
    private boolean atrasSalir;
    private int seleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        getSupportActionBar().setIcon(R.drawable.config);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5bc610")));

        tiempo = (TextView)findViewById(R.id.tiempo);
        seekBar = (SeekBar)findViewById(R.id.seekBar);

        alias = (EditText)findViewById(R.id.alias);
        parrilla = (EditText)findViewById(R.id.parrilla);

        jugar = (Button)findViewById(R.id.jugar);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new SpinnerInfo());
        spinner.setSelection(1);

        tiempo.setText(seekBar.getProgress()+"s");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress=progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(progress==0)tiempo.setText(R.string.limite);
                else tiempo.setText(progress+"s");
            }
        });

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alias.getText().toString().matches("")) Toast.makeText(ConfigActivity.this, R.string.falloAlias, Toast.LENGTH_SHORT).show();
                else if(parrilla.getText().toString().matches("")) Toast.makeText(ConfigActivity.this, R.string.falloParrilla, Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(ConfigActivity.this, GameHostActivity.class);
                    intent.putExtra("alias",alias.getText().toString());
                    intent.putExtra("parrilla",parrilla.getText().toString());
                    intent.putExtra("bombas",opcion);
                    intent.putExtra("tiempo",seekBar.getProgress()+"");
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private class SpinnerInfo implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> spinner, View selectedView,
                                   int selectedIndex, long id) {
            seleccion=selectedIndex;
            opcion=spinner.getItemAtPosition(selectedIndex).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> spinner) {

        }
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
        outState.putString("alias", alias.getText().toString());
        outState.putString("parrilla", parrilla.getText().toString());
        outState.putInt("bombas", seleccion);
        outState.putInt("tiempo", seekBar.getProgress());
        outState.putString("segundos", tiempo.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        alias.setText(savedInstanceState.getString("alias"));
        parrilla.setText(savedInstanceState.getString("parrilla"));
        spinner.setSelection(savedInstanceState.getInt("bombas"));
        seekBar.setVerticalScrollbarPosition(savedInstanceState.getInt("tiempo"));
        tiempo.setText(savedInstanceState.getString("segundos"));
    }
}
