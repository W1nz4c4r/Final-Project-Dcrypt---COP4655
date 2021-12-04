package com.rmoralessolo2016.dcrypt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<cryptoMG> CRY_coins;

    public  Adapter(Context cont, List<cryptoMG> CRY_coins){
        this.inflater = LayoutInflater.from(cont);
        this.CRY_coins = CRY_coins;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bin the data
        holder.coinName.setText(CRY_coins.get(position).getCoin_Name());
        holder.coinNick.setText(CRY_coins.get(position).getCoin_short_name());
        holder.coinPrice.setText(CRY_coins.get(position).getCoin_price());
        Picasso.get().load(CRY_coins.get(position).getCoin_icon_url()).into(holder.coinIMG);
    }

    @Override
    public int getItemCount() {
        return CRY_coins.size() - 1;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView coinName, coinNick, coinPrice;
        ImageView coinIMG;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coinName = itemView.findViewById(R.id.CoinName);
            coinNick = itemView.findViewById(R.id.CoinSHORTname);
            coinPrice = itemView.findViewById(R.id.coinPrice);
            coinIMG = itemView.findViewById(R.id.coinImage);
        }

    }
} //end of adapter class

