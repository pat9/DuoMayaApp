package Clases;

import android.widget.ImageView;

/**
 * Created by Luis Ramirez on 22/03/2018.
 */

public class ClsJuegoAhorcado {
    private String Titulo;
    private String TipoJuego;
    private int ImageViewJuego;

    public  ClsJuegoAhorcado(){

    }

    public ClsJuegoAhorcado(String titulo, String tipoJuego, int imageViewJuego){
        Titulo =titulo;
        TipoJuego=tipoJuego;
        ImageViewJuego=imageViewJuego;
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
}
