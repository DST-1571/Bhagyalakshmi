package com.sourceedge.bhagyalakshmi.orders.orderproduct.view;

/**
 * Created by Centura on 18-01-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.models.Sections;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Search_Customer;
import com.sourceedge.bhagyalakshmi.orders.support.Class_DBHelper;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Centura User1 on 12-12-2016.
 */

public class Sales_Person_List_Adapter extends RecyclerView.Adapter<Sales_Person_List_Adapter.ViewHolder> {
    Context mContext;
    ArrayList<Role> data;
    Class_DBHelper dbHelper;

    public Sales_Person_List_Adapter(Context context, ArrayList<Role> model) {
        this.mContext = context;
        this.data = model;
    }

    @Override
    public Sales_Person_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_retailer_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(Sales_Person_List_Adapter.ViewHolder holder, final int position) {
        holder.text.setText(data.get(position).getName());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Genric.hideKeyboard(mContext);
               Class_SyncApi.TrackLocationApi(mContext,data.get(position).getId());
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

