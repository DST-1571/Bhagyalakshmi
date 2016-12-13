package com.sourceedge.bhagyalakshmi.orders.orderpage.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Order;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 13-12-2016.
 */

public class Admin_Orders_Adapter extends RecyclerView.Adapter<Admin_Orders_Adapter.ViewHolder> {
    Context mcontext;
    ArrayList<Order> data;
    public Admin_Orders_Adapter(Context context, ArrayList<Order> model) {
        this.mcontext = context;
        this.data = model;
    }
    @Override
    public Admin_Orders_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_order_page, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Admin_Orders_Adapter.ViewHolder holder, int position) {
        holder.orderId.setText(data.get(position).getOrderNumber());
        holder.clientName.setText(data.get(position).getClient().getName());
        holder.clientAddress.setText(data.get(position).getClient().getAddress());
        holder.userName.setText(data.get(position).getUser().getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId,clientName,clientAddress,userName,userType;
        public ViewHolder(View v) {
            super(v);
            orderId=(TextView)v.findViewById(R.id.order_id);
            clientName=(TextView)v.findViewById(R.id.client_name);
            clientAddress=(TextView)v.findViewById(R.id.client_address);
            userName=(TextView)v.findViewById(R.id.user_name);
            userType=(TextView)v.findViewById(R.id.user_type);
        }
    }
}
