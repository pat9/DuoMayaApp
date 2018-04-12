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

import Clases.ClsArticulos;
import Clases.Ranking;

/**
 * Created by danie on 11/04/2018.
 */

public class AdapterRanking  extends RecyclerView.Adapter<AdapterRanking.RankingViewHolder> implements View.OnClickListener{

    ArrayList<Ranking> ListaRanking;
    private Context mcontext;
    private View.OnClickListener listener;

    public AdapterRanking(Context context, ArrayList<Ranking> listaArticulos) {
        mcontext=context;
        ListaRanking = listaArticulos;
    }

    public void setOnClickListener(View.OnClickListener listener ){
        this.listener=listener;
    }


    public void onClick(View view) {
        if (listener !=null){
            listener.onClick(view);

        }
    }

    @Override
    public AdapterRanking.RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.item_ranking,parent,false);
        view.setOnClickListener(this);
        return new AdapterRanking.RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRanking.RankingViewHolder  holder, int position) {
        holder.txtNumero.setText(ListaRanking.get(position).Position+"");
//        holder.txtNomnbre.setText(ListaRanking.get(position).Nombre);
        holder.txtUserName.setText(ListaRanking.get(position).NickName);
        holder.txtPuntos.setText(ListaRanking.get(position).Puntos + "");
        Picasso.with(mcontext)
                .load(ListaRanking.get(position).FotoPerfil)
                .into(holder.imgPerfl);

    }


    @Override
    public int getItemCount() {
        return ListaRanking.size();
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {

        TextView txtNumero,txtNomnbre,txtUserName, txtPuntos;
        ImageView imgPerfl;


        public RankingViewHolder(View itemView) {
            super(itemView);

            txtNumero=(TextView)itemView.findViewById(R.id.txtNumero);
            txtNomnbre=(TextView)itemView.findViewById(R.id.txtNombre);
            txtUserName=(TextView)itemView.findViewById(R.id.txtNombreUsu);
            txtPuntos=(TextView)itemView.findViewById(R.id.txtPuntos);
            imgPerfl=(ImageView)itemView.findViewById(R.id.imgperfil);

        }
    }
}
