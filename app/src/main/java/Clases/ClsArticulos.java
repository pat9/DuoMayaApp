package Clases;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.Image;

/**
 * Created by Luis Ramirez on 05/03/2018.
 */

public class ClsArticulos {

    private String Titulo;
    private String Descripcion;
    private int FotoArticulo;

    public  ClsArticulos(){

    }

    public ClsArticulos(String titulo, String descripcion, int fotoArticulo) {
        Titulo = titulo;
        Descripcion = descripcion;
        FotoArticulo = fotoArticulo;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getFotoArticulo() {
        return FotoArticulo;
    }

    public void setFotoArticulo(int fotoArticulo) {
        FotoArticulo = fotoArticulo;
    }
}
