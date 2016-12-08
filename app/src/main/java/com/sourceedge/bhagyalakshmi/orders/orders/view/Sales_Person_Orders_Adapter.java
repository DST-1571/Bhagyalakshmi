package com.sourceedge.bhagyalakshmi.orders.orders.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourceedge.bhagyalakshmi.orders.R;

/**
 * Created by Centura User1 on 06-12-2016.
 */

public class Sales_Person_Orders_Adapter extends RecyclerView.Adapter<Sales_Person_Orders_Adapter.ViewHolder> {
    Context mContext;
    public Sales_Person_Orders_Adapter(Context context){
        this.mContext=context;
    }
    @Override
    public Sales_Person_Orders_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Sales_Person_Orders_Adapter.ViewHolder holder, int position) {

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
