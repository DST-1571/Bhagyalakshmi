package com.sourceedge.bhagyalakshmi.orders.orderpage.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;

/**
 * Created by Centura on 08-12-2016.
 */

public class Order_Page_Adapter extends RecyclerView.Adapter<Order_Page_Adapter.ViewHolder> {
    Context mcontext;
    ArrayList<Order> data;
    Product prod;

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
    public void onBindViewHolder(Order_Page_Adapter.ViewHolder holder, final int position) {
        holder.orderId.setText(data.get(position).getOrderNumber());
        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
            case Class_Genric.ADMIN:
                break;
            case Class_Genric.DISTRIBUTORSALES:
                break;
            case Class_Genric.SALESMAN:
                holder.clientName.setText(data.get(position).getClient().getName());
                break;
            case Class_Genric.DISTRIBUTOR:
                holder.clientName.setText(data.get(position).getUser().getEmployee());
                break;
        }
        holder.totalAmount.setText(Class_Genric.rupee+data.get(position).getTotalAmount().toString());
        holder.orderStatus.setText(data.get(position).getStatus());
        holder.orderStatus.setSelected(true);
        holder.orderedPane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Static.viewOrderedProducts=true;
                Class_Static.OrdredProducts=new Order();
                Class_Static.tempOrderingProduct=new ArrayList<Product>();
                Class_Static.OrdredProducts=data.get(position);
                for(int i=0;i<Class_Static.OrdredProducts.getProducts().size();i++){
                            prod=new Product();
                            prod.setQuantity(new Double(Class_Static.OrdredProducts.getProducts().get(i).getQuantity()).intValue());
                            prod.setPrice(Class_Static.OrdredProducts.getProducts().get(i).getPrice());
                            prod.setAmount(prod.getQuantity()*prod.getPrice());
                            prod.setDescription(Class_Static.OrdredProducts.getProducts().get(i).getDescription());
                            prod.setUnits(Class_Static.OrdredProducts.getProducts().get(i).getUnit());
                            Class_Static.tempOrderingProduct.add(prod);
                }
                ((Activity)mcontext).startActivity(new Intent(mcontext, Product_Order_Lookup.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, clientName, totalAmount,orderStatus;
        LinearLayout orderedPane;
        public ViewHolder(View v) {
            super(v);
            orderId = (TextView) v.findViewById(R.id.order_id);
            clientName = (TextView) v.findViewById(R.id.client_name);
            totalAmount = (TextView) v.findViewById(R.id.total_amount);
            orderStatus=(TextView)v.findViewById(R.id.order_status);
            orderedPane=(LinearLayout)v.findViewById(R.id.ordered_pane);
        }
    }
}
