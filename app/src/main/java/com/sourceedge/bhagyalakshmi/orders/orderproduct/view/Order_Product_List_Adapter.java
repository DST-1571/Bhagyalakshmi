package com.sourceedge.bhagyalakshmi.orders.orderproduct.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 10-12-2016.
 */

public class Order_Product_List_Adapter extends RecyclerView.Adapter<Order_Product_List_Adapter.ViewHolder> {
    Context mContext;
    ArrayList<Product> data;
    Double amount=0.0;
    public Order_Product_List_Adapter(Context context, ArrayList<Product> model){
        this.mContext=context;
        this.data=model;
        amount=0.0;
        for (Product prod : data) {
            amount+=prod.getAmount();
        }
            Product_Order_Lookup.grandTotal.setText("Total : "+amount+"");


    }

    @Override
    public Order_Product_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Order_Product_List_Adapter.ViewHolder holder, int position) {
            holder.category.setText(data.get(position).getCatagoryName());
            holder.description.setText(data.get(position).getDescription());
            holder.quantity.setText(data.get(position).getQuantity()+"");
            holder.unit.setText(data.get(position).getUnits());
            holder.price.setText(Class_Genric.rupee+data.get(position).getPrice());
            OnClicks(holder,position);
    }

    private void OnClicks(Order_Product_List_Adapter.ViewHolder holder, final int pos) {
        holder.productEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Static.editProductOrder=true;
                Class_Static.tempProduct=data.get(pos);
                ((Activity)mContext).startActivity(new Intent(mContext,Add_Product.class));
            }
        });

        holder.productDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(pos);
                Product_Order_Lookup.InitializeAdapter(mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category,description,quantity,unit,price;
        ImageView productEdit,productDelete;
        public ViewHolder(View v) {
            super(v);
            category=(TextView)v.findViewById(R.id.category);
            description=(TextView)v.findViewById(R.id.description);
            quantity=(TextView)v.findViewById(R.id.quantity);
            unit=(TextView)v.findViewById(R.id.unit);
            price=(TextView)v.findViewById(R.id.price);
            productEdit=(ImageView)v.findViewById(R.id.product_edit);
            productDelete=(ImageView)v.findViewById(R.id.product_delete);
        }
    }
}
