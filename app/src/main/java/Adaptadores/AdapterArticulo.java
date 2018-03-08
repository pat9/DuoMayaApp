package Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisr.duomayaapp.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import Clases.ClsArticulos;


/**
 * Created by Luis Ramirez on 05/03/2018.
 */

public class AdapterArticulo extends RecyclerView.Adapter<AdapterArticulo.ArticuloViewHolder> implements
View.OnClickListener{

    ArrayList<ClsArticulos> ListaArticulos;
    private View.OnClickListener listener;
    private Context mcontext;

    public AdapterArticulo(Context context, ArrayList <ClsArticulos> listaArticulos) {
        mcontext=context;
        ListaArticulos = listaArticulos;
    }

    @Override

    public ArticuloViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.items_recycler_articulos,parent,false);
            view.setOnClickListener(this);
        return new ArticuloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticuloViewHolder holder, int position) {
        holder.txtTitulo.setText(ListaArticulos.get(position).getTitulo());
        holder.txtDescripcion.setText(ListaArticulos.get(position).getDescripcion());
        Picasso.with(mcontext)
                .load(ListaArticulos.get(position).getFotoArticulo())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.ImgAticulo);
    }


    @Override
    public int getItemCount() {
        return ListaArticulos.size();
    }

    public void setOnclickLisener(View.OnClickListener lisener){
        this.listener = lisener;
    }

    @Override
    public void onClick(View view) {
        if (listener !=null){
            listener.onClick(view);
        }
    }


    public class ArticuloViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo,txtDescripcion;
        ImageView ImgAticulo;

        public ArticuloViewHolder(View itemView) {
            super(itemView);

            txtTitulo=(TextView)itemView.findViewById(R.id.txtTitulo);
            txtDescripcion=(TextView)itemView.findViewById(R.id.txtDescrip);
            ImgAticulo=(ImageView)itemView.findViewById(R.id.imgRecyclerArticulo);



        }
    }
}
