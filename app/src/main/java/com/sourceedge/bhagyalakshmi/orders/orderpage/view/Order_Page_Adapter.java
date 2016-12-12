package com.sourceedge.bhagyalakshmi.orders.orderpage.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;

import java.util.ArrayList;

/**
 * Created by Centura on 08-12-2016.
 */

public class Order_Page_Adapter extends RecyclerView.Adapter<Order_Page_Adapter.ViewHolder> {
    Context mcontext;
    ArrayList<Order> data;

    public Order_Page_Adapter(Context context, ArrayList<Order> model) {
        this.mcontext = context;
        this.data = model;
    }

    @Override
    public Order_Page_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_page, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Order_Page_Adapter.ViewHolder holder, int position) {
        holder.orderId.setText(data.get(position).getOrderNumber());
        holder.clientName.setText(data.get(position).getClient().getName());
        holder.totalAmount.setText(data.get(position).getUser().getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, clientName, totalAmount;

        public ViewHolder(View v) {
            super(v);
            orderId = (TextView) v.findViewById(R.id.order_id);
            clientName = (TextView) v.findViewById(R.id.client_name);
            totalAmount = (TextView) v.findViewById(R.id.total_amount);
        }
    }
}
