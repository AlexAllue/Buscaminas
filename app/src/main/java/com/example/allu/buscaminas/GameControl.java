package com.example.allu.buscaminas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allu on 27/05/2015.
 */
public class GameControl{
    public String tiempoMaximo;
    public String inicioTirada;
    public String logInicial;
    public Long inicioPartida;
    public int longitud,nBombas;
    public ArrayList<String> tablero;

    public GameControl() {
    }

    public GameControl(String tiempoMaximo,String inicioTirada,String logInicial,int longitud,int nBombas,ArrayList<String> tablero) {
        this.tiempoMaximo=tiempoMaximo;
        this.inicioTirada = inicioTirada;
        this.logInicial=logInicial;
        this.longitud=longitud;
        this.nBombas=nBombas;
        this.tablero=tablero;
    }

    public int getnBombas() {
        return nBombas;
    }

    public int getLongitud() {
        return longitud;
    }

    public ArrayList<String> getTablero() {
        return tablero;
    }

    public String getInicioTirada() {
        return inicioTirada;
    }

    public void setInicioTirada(String inicioTirada) {
        this.inicioTirada = inicioTirada;
    }

    public void setInicioPartida(Long inicioPartida){
        this.inicioPartida=inicioPartida;
    }

    public boolean calcularFinalPartida(){
        Calendar calFechaFinal = Calendar.getInstance();
        calFechaFinal.setTime(new Date());
        if((calFechaFinal.getTimeInMillis()-inicioPartida)/1000<Integer.parseInt(tiempoMaximo))return false;
        else return true;
    }

    public String getLogInicial() {
        return logInicial;
    }

}
