package com.sourceedge.bhagyalakshmi.orders.orderpage.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Order;

import java.util.ArrayList;

/**
 * Created by Centura on 08-12-2016.
 */

public class Order_Page_Adapter extends RecyclerView.Adapter<Order_Page_Adapter.ViewHolder> {
    Context mcontext;
    ArrayList<Order> data;
    public Order_Page_Adapter(Context context, ArrayList<Order> model){
        this.mcontext= context;
        this.data=model;
    }

    @Override
    public Order_Page_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_page,parent,false);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Order_Page_Adapter.ViewHolder holder, int position) {

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
