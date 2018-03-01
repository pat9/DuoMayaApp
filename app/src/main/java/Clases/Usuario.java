package Clases;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danie on 01/03/2018.
 */

public class Usuario implements Parcelable {
    public Integer Codigo;
    public String NickName;
    public String Password;
    public String FotoPerfil;
    public String Nombre;
    public String Apellido;
    public String Correo;
    public Integer Tipo;

    public Usuario()
    {

    }

    protected Usuario(Parcel in) {
        Codigo = in.readByte() == 0x00 ? null : in.readInt();
        NickName = in.readString();
        Password = in.readString();
        FotoPerfil = in.readString();
        Nombre = in.readString();
        Apellido = in.readString();
        Correo = in.readString();
        Tipo = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Codigo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(Codigo);
        }
        dest.writeString(NickName);
        dest.writeString(Password);
        dest.writeString(FotoPerfil);
        dest.writeString(Nombre);
        dest.writeString(Apellido);
        dest.writeString(Correo);
        if (Tipo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(Tipo);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}