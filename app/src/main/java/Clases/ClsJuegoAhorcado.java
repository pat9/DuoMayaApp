package Clases;

import android.widget.ImageView;

/**
 * Created by Luis Ramirez on 22/03/2018.
 */

public class ClsJuegoAhorcado {


    private int Codigo;
    private String Titulo;
    private String TipoJuego;
    private int ImageViewJuego;

    public  ClsJuegoAhorcado(){

    }

    public ClsJuegoAhorcado(int codigo, String titulo, String tipoJuego, int imageViewJuego){
        Titulo =titulo;
        TipoJuego=tipoJuego;
        ImageViewJuego=imageViewJuego;
        Codigo = codigo;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getTipoJuego() {
        return TipoJuego;
    }

    public void setTipoJuego(String tipoJuego) {
        TipoJuego = tipoJuego;
    }

    public int getImageViewJuego() {
        return ImageViewJuego;
    }

    public void setImageViewJuego(int imageViewJuego) {
        this.ImageViewJuego = imageViewJuego;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }
}
