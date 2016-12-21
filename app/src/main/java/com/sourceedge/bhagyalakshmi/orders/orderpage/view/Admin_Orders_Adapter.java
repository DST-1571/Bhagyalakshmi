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
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Admin_View_Order;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 13-12-2016.
 */

public class Admin_Orders_Adapter extends RecyclerView.Adapter<Admin_Orders_Adapter.ViewHolder> {
    Context mcontext;
    ArrayList<Order> data;
    Product prod;
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
    public void onBindViewHolder(Admin_Orders_Adapter.ViewHolder holder, final int position) {
        holder.orderId.setText(data.get(position).getOrderNumber());
        holder.clientName.setText(data.get(position).getClient().getName());
        holder.clientAddress.setText(data.get(position).getClient().getAddress());
        holder.userName.setText(data.get(position).getUser().getName());
        holder.userType.setText(data.get(position).getUser().getUserType());
        holder.adminOrderPane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Static.OrdredProducts=new Order();
                Class_Static.tempOrderingProduct=new ArrayList<Product>();
                Class_Static.OrdredProducts=data.get(position);
                for(int i=0;i<Class_Static.OrdredProducts.getProducts().size();i++){
                    for(int j = 0; j< Class_ModelDB.getProductList().size(); j++){
                        if(Class_Static.OrdredProducts.getProducts().get(i).getProductId().matches(Class_ModelDB.getProductList().get(j).getId())){
                            prod=new Product();
                            prod.setId(Class_ModelDB.getProductList().get(j).getId());
                            prod.setQuantity(new Double(Class_Static.OrdredProducts.getProducts().get(i).getQuantity()).intValue());
                            prod.setPrice(Class_Static.OrdredProducts.getProducts().get(i).getPrice());
                            prod.setAmount(prod.getQuantity()*prod.getPrice());
                            prod.setDescription(Class_ModelDB.getProductList().get(j).getDescription());
                            prod.setSectionName(Class_ModelDB.getProductList().get(j).getSectionName());
                            prod.setUnits(Class_Static.OrdredProducts.getProducts().get(i).getUnit());
                            prod.setCategoryId(Class_ModelDB.getProductList().get(j).getCategoryId());
                            prod.setSectionId(Class_ModelDB.getProductList().get(j).getSectionId());for (Catagories catagories:Class_ModelDB.getCatagoryList()) {
                                if(prod.getCategoryId().matches(catagories.getId()))
                                    prod.setCatagoryName(catagories.getName());
                            }
                            Class_Static.tempOrderingProduct.add(prod);
                        }
                    }
                }
                ((Activity)mcontext).startActivity(new Intent(mcontext, Admin_View_Order.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId,clientName,clientAddress,userName,userType;
        LinearLayout adminOrderPane;
        public ViewHolder(View v) {
            super(v);
            orderId=(TextView)v.findViewById(R.id.order_id);
            clientName=(TextView)v.findViewById(R.id.client_name);
            clientAddress=(TextView)v.findViewById(R.id.client_address);
            userName=(TextView)v.findViewById(R.id.user_name);
            userType=(TextView)v.findViewById(R.id.user_type);
            adminOrderPane=(LinearLayout)v.findViewById(R.id.admin_order_pane);
        }
    }
}
