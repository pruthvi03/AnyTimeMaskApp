package com.example.asus.surakshakavach;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    Context context;
    ArrayList<Order> orders;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item,viewGroup,false));
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.dateTime.setText(orders.get(i).getTime_stamp());
        viewHolder.bill.setText(orders.get(i).getBill()+" Rs.");
        viewHolder.qty.setText(orders.get(i).getQty());
        viewHolder.status.setText(orders.get(i).getFlag());

        if(viewHolder.status.getText().toString().equals("Completed")) {
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.right));
        }else {viewHolder.status.setTextColor(context.getResources().getColor(R.color.wrong));}
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView dateTime,bill,qty,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTime = itemView.findViewById(R.id.date_time);
            bill = itemView.findViewById(R.id.bill_amount);
            qty = itemView.findViewById(R.id.quantity);
            status = itemView.findViewById(R.id.status);
        }

        

    }

}
