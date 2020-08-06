package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ExampleViewHolder> {


    private ArrayList<Item> mItemList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView titleName;
        public TextView amountName;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleName = itemView.findViewById(R.id.nameofExpense);
            amountName = itemView.findViewById(R.id.sumofExpense);
        }
    }

    public Adapter(ArrayList<Item> itemList) {
        mItemList = itemList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        Item currentItem = mItemList.get(position);
        holder.titleName.setText(currentItem.getItemName());
        holder.amountName.setText(currentItem.getAmountName());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


}
