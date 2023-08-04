package com.example.smartflowerpot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

    private ArrayList<Data> listData = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);


        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void additem(Data data){
        listData.add(data);
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle, txtContent, txtUserID;
        ItemViewHolder(View itemView){
            super(itemView);
            txtTitle = itemView.findViewById(R.id.TxtTitle);
            txtContent = itemView.findViewById(R.id.TxtContent);
            txtUserID = itemView.findViewById(R.id.TxtUserID);
        }
        void onBind(Data data){
            txtTitle.setText(data.getTitle());
            txtContent.setText(data.getContent());
            txtUserID.setText(data.getEmail());
        }
    }
}