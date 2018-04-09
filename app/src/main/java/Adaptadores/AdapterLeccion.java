package Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisr.duomayaapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Clases.Leccion;

/**
 * Created by danie on 02/04/2018.
 */

public class AdapterLeccion extends RecyclerView.Adapter<AdapterLeccion.LeccionViewHolder> implements View.OnClickListener {
    ArrayList<Leccion> ListaLecciones;
    private Context mcontext;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener ){
        this.listener=listener;
    }


    public void onClick(View view) {
        if (listener !=null){
            listener.onClick(view);

        }
    }


    public AdapterLeccion(Context context, ArrayList <Leccion> listaLeccion) {
        mcontext=context;
        ListaLecciones = listaLeccion;
    }

    @Override

    public AdapterLeccion.LeccionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.lecciones_item,parent,false);
        view.setOnClickListener(this);
        return new AdapterLeccion.LeccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterLeccion.LeccionViewHolder holder, int position) {
        holder.txtID.setText(""+ListaLecciones.get(position).CodigoLeccion);
        holder.txtTitulo.setText(ListaLecciones.get(position).Nombre);
        holder.txtDescripcion.setText(ListaLecciones.get(position).Decripcion);
        Picasso.with(mcontext)
                .load(ListaLecciones.get(position).Imagen)
                .into(holder.ImgAticulo);

    }


    @Override
    public int getItemCount() {
        return ListaLecciones.size();
    }


    public class LeccionViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo,txtDescripcion, txtID;
        ImageView ImgAticulo;


        public LeccionViewHolder(View itemView) {
            super(itemView);

            txtTitulo=(TextView)itemView.findViewById(R.id.txtTitulo);
            txtDescripcion=(TextView)itemView.findViewById(R.id.txtDescrip);
            ImgAticulo=(ImageView)itemView.findViewById(R.id.imgRecyclerArticulo);
            txtID = (TextView)itemView.findViewById(R.id.txtID);
        }
    }
}
