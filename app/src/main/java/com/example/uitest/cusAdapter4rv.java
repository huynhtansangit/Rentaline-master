package com.example.uitest;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cusAdapter4rv extends RecyclerView.Adapter<cusAdapter4rv.ViewHolder> {
    ArrayList<ItemObj> Items;
    Context context;

    public cusAdapter4rv(ArrayList<ItemObj> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(viewGroup.getContext());
        View item= inflater.inflate(R.layout.h_recycleview_item,viewGroup,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text1.setText(Items.get(i).getTitle());
        viewHolder.text1.setTag(Items.get(i).getPostUrl());
        viewHolder.text2.setText(Items.get(i).getDetail());
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text1, text2;

        public ViewHolder(View view){
            super(view);
            text1=view.findViewById(R.id.itemTitle);
            text2=view.findViewById(R.id.itemPrice);
        }
    }
}
