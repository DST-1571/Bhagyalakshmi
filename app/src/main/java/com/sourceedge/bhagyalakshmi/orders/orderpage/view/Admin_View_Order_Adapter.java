package com.sourceedge.bhagyalakshmi.orders.orderpage.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Admin_View_Order;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 16-12-2016.
 */

public class Admin_View_Order_Adapter extends RecyclerView.Adapter<Admin_View_Order_Adapter.ViewHolder> {
    Context mContext;
    ArrayList<Product> data;
    Double amount = 0.0;

    public Admin_View_Order_Adapter(Context context, ArrayList<Product> model) {
        this.mContext = context;
        this.data = model;
        for (Product prod : data) {
            amount += prod.getAmount();
        }
        Admin_View_Order.total.setText("Total : " +String.format("%.2f", amount)+ "");

    }

    @Override
    public Admin_View_Order_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_view_order, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Admin_View_Order_Adapter.ViewHolder holder, int position) {
        holder.category.setText(data.get(position).getCatagoryName());
        holder.category.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.category.setSelected(true);
        holder.category.setSingleLine(true);
        holder.description.setText(data.get(position).getDescription());
        holder.quantity.setText(data.get(position).getQuantity() + "");
        holder.unit.setText(data.get(position).getUnits());
        holder.price.setText(data.get(position).getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category, description, quantity, unit, price;

        public ViewHolder(View v) {
            super(v);
            category = (TextView) v.findViewById(R.id.admin_category);
            description = (TextView) v.findViewById(R.id.admin_description);
            quantity = (TextView) v.findViewById(R.id.admin_quantity);
            unit = (TextView) v.findViewById(R.id.admin_unit);
            price = (TextView) v.findViewById(R.id.admin_price);
        }
    }
}
