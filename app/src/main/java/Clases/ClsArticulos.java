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
    private String FotoArticulo;

    public  ClsArticulos(){

    }

    public ClsArticulos(String titulo, String descripcion, String fotoArticulo) {
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

    public String getFotoArticulo() {
        return FotoArticulo;
    }

    public void setFotoArticulo(String fotoArticulo) {
        FotoArticulo = fotoArticulo;
    }
}
