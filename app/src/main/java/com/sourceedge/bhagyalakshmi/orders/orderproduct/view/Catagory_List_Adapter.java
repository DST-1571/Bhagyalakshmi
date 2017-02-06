package com.sourceedge.bhagyalakshmi.orders.orderproduct.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 12-12-2016.
 */

public class Catagory_List_Adapter extends RecyclerView.Adapter<Catagory_List_Adapter.ViewHolder> {
    Context mContext;
    ArrayList<Catagories> data;

    public Catagory_List_Adapter(Context context, ArrayList<Catagories> model) {
        this.mContext = context;
        this.data = model;
    }

    @Override
    public Catagory_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_retailer_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Catagory_List_Adapter.ViewHolder holder, final int position) {
        holder.text.setText(data.get(position).getName().toString());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Genric.hideKeyboard(mContext);
                Class_Static.tempProduct.setCategoryId(data.get(position).getId());
                Class_Static.tempProduct.setCatagoryName(data.get(position).getName());
                Class_Static.tempProduct.setSectionName("");
                Class_Static.tempProduct.setCode("");
                Class_Static.tempProduct.setAmount(0.0);
                Class_Static.tempProduct.setUnits("");
                Class_Static.tempProduct.setQuantity(0);
                Class_Static.tempProduct.setDescription("");
                Class_Static.tempProduct.setPrice(0.0);

                Add_Product.productSearch.setText(Class_Static.tempProduct.getDescription());
                Add_Product.productCategory.setText(Class_Static.tempProduct.getCatagoryName());
                Add_Product.productUnit.setText(Class_Static.tempProduct.getUnits());
                Add_Product.productQuantity.setText("");
                Add_Product.productPrice.setText("");
                if (Class_Genric.NetAvailable(mContext)) {
                    Class_SyncApi.ProductApi(mContext,Class_Static.tempRole.getId(),data.get(position).getName());
                }
                else {
                    Class_SyncApi.LoadOfflineProducts(mContext,Class_Static.tempRole.getId(),data.get(position).getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text1);

        }
    }
}
