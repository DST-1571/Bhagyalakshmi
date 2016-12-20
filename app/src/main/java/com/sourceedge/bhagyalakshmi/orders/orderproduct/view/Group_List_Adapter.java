package com.sourceedge.bhagyalakshmi.orders.orderproduct.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Sections;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * Created by Centura User1 on 12-12-2016.
 */

public class Group_List_Adapter extends RecyclerView.Adapter<Group_List_Adapter.ViewHolder> {
    Context mContext;
    ArrayList<Sections> data;

    public Group_List_Adapter(Context context, ArrayList<Sections> model) {
        this.mContext = context;
        this.data = model;
    }

    @Override
    public Group_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_retailer_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Group_List_Adapter.ViewHolder holder, final int position) {
        holder.text.setText(data.get(position).getName().toString());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Genric.hideKeyboard(mContext);
                Class_Static.tempProduct = new Product();
                Class_Static.tempProduct.setSectionId(data.get(position).Id);
                Class_Static.tempProduct.setSectionName(data.get(position).Name);
                Add_Product.productGroup.setText(Class_Static.tempProduct.getSectionName());
                Add_Product.productSearch.setText(Class_Static.tempProduct.getDescription());
                Add_Product.productCategory.setText(Class_Static.tempProduct.getCatagoryName());
                Add_Product.productUnit.setText(Class_Static.tempProduct.getUnits());
                Add_Product.productQuantity.setText("");
                Add_Product.productPrice.setText("");
                ((Activity)mContext).finish();
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
