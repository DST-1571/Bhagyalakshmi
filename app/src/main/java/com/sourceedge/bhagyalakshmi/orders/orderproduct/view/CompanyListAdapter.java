package com.sourceedge.bhagyalakshmi.orders.orderproduct.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.CompanyModel;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 09-02-2017.
 */

public class CompanyListAdapter  extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<CompanyModel> data;

    public CompanyListAdapter(Context context) {
        this.mContext = context;
        this.data = new ArrayList<CompanyModel>();
        this.data= Class_ModelDB.CompanyList;
    }


    @Override
    public CompanyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_list, parent, false);
        CompanyListAdapter.ViewHolder vh = new CompanyListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.text.setText(data.get(position).getCompany());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product_Order_Lookup.PlaceOrder(mContext,data.get(position).getCompany());
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
