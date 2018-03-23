package Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisr.duomayaapp.R;

import java.util.ArrayList;

import Clases.ClsJuegoAhorcado;

/**
 * Created by Luis Ramirez on 22/03/2018.
 */

public class AdapterJuegoAhorcado extends RecyclerView.Adapter<AdapterJuegoAhorcado.JuegoAhorcadoViewHolder> implements View.OnClickListener{


    ArrayList<ClsJuegoAhorcado> ListaJuego;
    private Context Mcontext;
    private View.OnClickListener listener;

    public AdapterJuegoAhorcado(Context context,ArrayList<ClsJuegoAhorcado> listaJuego){
        Mcontext=context;
        ListaJuego=listaJuego;
    }

    @Override
    public JuegoAhorcadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.item_juegos_layout,parent,false);
        return new AdapterJuegoAhorcado.JuegoAhorcadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JuegoAhorcadoViewHolder holder, int position) {
        holder.imageViewJ.setImageResource(ListaJuego.get(position).getImageViewJuego());
        holder.textViewJDesc.setText(ListaJuego.get(position).getTipoJuego());
        holder.textViewJ.setText(ListaJuego.get(position).getTitulo());

    }

    @Override
    public int getItemCount() {
        return ListaJuego.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class JuegoAhorcadoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewJ;
        private TextView textViewJ;
        private TextView textViewJDesc;

        public JuegoAhorcadoViewHolder(View itemView) {
            super(itemView);
            imageViewJ=(ImageView)itemView.findViewById(R.id.ImgJuego);
            textViewJ=(TextView)itemView.findViewById(R.id.txtTituloAhorcado);
            textViewJDesc=(TextView)itemView.findViewById(R.id.txtTipoAhorcado);


        }
    }
}
