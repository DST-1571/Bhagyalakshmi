package com.sourceedge.bhagyalakshmi.orders.distributorsales.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourceedge.bhagyalakshmi.orders.R;

/**
 * Created by Centura User1 on 10-12-2016.
 */

public class Order_Product_List_Adapter extends RecyclerView.Adapter<Order_Product_List_Adapter.ViewHolder> {
    Context mContext;
    public Order_Product_List_Adapter(Context context){
        this.mContext=context;
    }

    @Override
    public Order_Product_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_product_list,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Order_Product_List_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }
}
