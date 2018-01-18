package com.studio.sameer.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sameer on 05/10/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<card_item> card_Items;
    private Context context;

    public MyAdapter(List<card_item> card_Items, Context context) {
        this.card_Items = card_Items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        card_item card_item = card_Items.get(position);

        holder.currencyName.setText(card_item.getCurrencyName());
        holder.rate.setText(card_item.getRate());
        holder.date.setText(card_item.getDate());
    }

    @Override
    public int getItemCount() {
        return card_Items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView currencyName, rate, date;

        public ViewHolder(View itemView) {
            super(itemView);

            currencyName = (TextView)itemView.findViewById(R.id.currency);
            rate = (TextView)itemView.findViewById(R.id.rate);
            date = (TextView)itemView.findViewById(R.id.date);

        }
    }
}