package com.sourceedge.bhagyalakshmi.orders.orderproduct.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 12-12-2016.
 */

public class Product_List_Adapter extends RecyclerView.Adapter<Product_List_Adapter.ViewHolder> {
    Context mContext;
    ArrayList<Product> data;

    public Product_List_Adapter(Context context, ArrayList<Product> model) {
        this.mContext = context;
        this.data = model;
    }

    @Override
    public Product_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_retailer_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Product_List_Adapter.ViewHolder holder, final int position) {
        holder.text.setText(data.get(position).getDescription().toString());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Genric.hideKeyboard(mContext);
                switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                    case Class_Genric.DISTRIBUTORSALES:
                        boolean found=false;
                        int foundAt=-1;
                        for(int i=0;i<Class_Static.tempOrderingProduct.size();i++){
                            if(Class_Static.tempOrderingProduct.get(i).getCode().matches(data.get(position).getCode())){
                                found=true;
                                foundAt=i;
                                break;
                            }
                        }
                        if(!found){
                            Class_Static.tempProduct.setProductDetais(data.get(position));
                            Class_Static.tempProduct.setQuantity(1);
                            Add_Product.productSearch.setText(Class_Static.tempProduct.getDescription());
                            Add_Product.productUnit.setText(Class_Static.tempProduct.getUnits());
                            Add_Product.productPrice.setText(Class_Static.tempProduct.getPrice() + "");
                            Class_SyncApi.StockApi(mContext, data.get(position).getCode(),0);
                        }else {
                            Class_Static.tempProduct.setProductDetais(data.get(position));
                            Add_Product.productSearch.setText(Class_Static.tempProduct.getDescription());
                            Add_Product.productUnit.setText(Class_Static.tempProduct.getUnits());
                            Add_Product.productPrice.setText(Class_Static.tempProduct.getPrice() + "");
                            Class_SyncApi.StockApi(mContext, data.get(position).getCode(),Class_Static.tempOrderingProduct.get(foundAt).getQuantity());
                        }
                        break;
                    case Class_Genric.DISTRIBUTOR:
                    case Class_Genric.SALESMAN:
                        if (Class_Genric.NetAvailable(mContext)) {
                            Class_SyncApi.ProductPriceApi(mContext,data.get(position),Class_Static.tempRole.getId());
                        }
                        else {
                            if(data.get(position).getPrice()>0)
                            {
                                Class_Static.tempProduct.setProductDetais(data.get(position));
                                Class_Static.tempProduct.setQuantity(1);
                                Add_Product.productSearch.setText(Class_Static.tempProduct.getDescription());
                                Add_Product.productUnit.setText(Class_Static.tempProduct.getUnits());
                                Add_Product.productQuantity.setText(Class_Static.tempProduct.getQuantity() + "");
                                Add_Product.productPrice.setText(Class_Static.tempProduct.getPrice() + "");
                                ((Activity) mContext).finish();
                            }
                            else {
                                Toast.makeText(mContext, "This Product is UnAvailable", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
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
