package com.example.allu.buscaminas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allu on 27/05/2015.
 */
public class GameControl implements Parcelable {
    public String tiempoMaximo;
    public String inicioTirada;
    public String logInicial;
    public Long inicioPartida;
    public int longitud;
    public int nBombas;
    public ArrayList<String> tablero;
    public ArrayList<String> apretado;
    public int casillas;
    public boolean end=false;
    public int progreso;
    public String alias;

    public GameControl() {
    }

    public GameControl(String alias,String tiempoMaximo,String inicioTirada,String logInicial,int longitud,int nBombas,ArrayList<String> tablero,ArrayList<String> apretado) {
        this.alias=alias;
        this.tiempoMaximo=tiempoMaximo;
        this.inicioTirada = inicioTirada;
        this.logInicial=logInicial;
        this.longitud=longitud;
        this.nBombas=nBombas;
        this.tablero=tablero;
        this.apretado=apretado;
        casillas=longitud*longitud;
    }

    public int getnBombas() {
        return nBombas;
    }

    public String getAlias() {
        return alias;
    }

    public int getCasillas() {
        return casillas;
    }

    public int restarCasilla(){
        casillas=casillas-1;
        progreso=progreso+1;
        return casillas;
    }


    public boolean haTerminado() {
        return end;
    }

    public void terminar(){
        end=true;
    }

    public int getLongitud() {
        return longitud;
    }

    public int getProgreso() {
        return progreso;
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

    public ArrayList<String> getApretado() {
        return apretado;
    }

    public boolean calcularFinalPartida(){
        Calendar calFechaFinal = Calendar.getInstance();
        calFechaFinal.setTime(new Date());
        if((calFechaFinal.getTimeInMillis()-inicioPartida)/1000<Integer.parseInt(tiempoMaximo))return false;
        else return true;
    }

    public long calcularTiempoRestante(){
        Calendar calFechaFinal = Calendar.getInstance();
        calFechaFinal.setTime(new Date());
        return Integer.parseInt(tiempoMaximo)-((calFechaFinal.getTimeInMillis()-inicioPartida)/1000);
    }

    public String getLogInicial() {
        return logInicial;
    }


    protected GameControl(Parcel in) {
        tiempoMaximo = in.readString();
        inicioTirada = in.readString();
        logInicial = in.readString();
        inicioPartida = in.readByte() == 0x00 ? null : in.readLong();
        longitud = in.readInt();
        nBombas = in.readInt();
        if (in.readByte() == 0x01) {
            tablero = new ArrayList<String>();
            in.readList(tablero, String.class.getClassLoader());
        } else {
            tablero = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tiempoMaximo);
        dest.writeString(inicioTirada);
        dest.writeString(logInicial);
        if (inicioPartida == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(inicioPartida);
        }
        dest.writeInt(longitud);
        dest.writeInt(nBombas);
        if (tablero == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tablero);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GameControl> CREATOR = new Parcelable.Creator<GameControl>() {
        @Override
        public GameControl createFromParcel(Parcel in) {
            return new GameControl(in);
        }

        @Override
        public GameControl[] newArray(int size) {
            return new GameControl[size];
        }
    };
}
