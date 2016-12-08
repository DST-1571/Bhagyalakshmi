package com.sourceedge.bhagyalakshmi.orders.distributorsales.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourceedge.bhagyalakshmi.orders.R;

/**
 * Created by Centura on 08-12-2016.
 */

public class Retailer_LookUp_Adapter extends RecyclerView.Adapter<Retailer_LookUp_Adapter.ViewHolder> {
    Context mcontext;
    public Retailer_LookUp_Adapter(Context context){
        this.mcontext=context;
    }
    @Override
    public Retailer_LookUp_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_retailer_lookup,parent,false);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Retailer_LookUp_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
