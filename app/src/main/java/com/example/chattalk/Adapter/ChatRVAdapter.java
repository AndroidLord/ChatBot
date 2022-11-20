package com.example.chattalk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.chattalk.Model.ChatsModel;
import com.example.chattalk.R;

import java.util.ArrayList;
import java.util.List;

public class ChatRVAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ChatsModel> chatlist;

    public ChatRVAdapter(List<ChatsModel> list, Context context) {
        this.context = context;
        this.chatlist = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view;

    switch (viewType){
        case 0:
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_item,parent,false);
            return new UserViewHolder(view);
        case 1:
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.bot_item,parent,false);
            return new botViewHolder(view);
    }

    return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch(chatlist.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatsModel chatModel = chatlist.get(position);
        if(chatModel.getSender().equals("user"))
            ((UserViewHolder)holder).userTextView.setText(chatModel.getMessage());

        else if(chatModel.getSender().equals("bot"))
            ((botViewHolder)holder).botTextView.setText(chatModel.getMessage());

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView userTextView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userTextView = itemView.findViewById(R.id.userTextView);
        }

    }

    public static class botViewHolder extends RecyclerView.ViewHolder{
        public TextView botTextView;
        public botViewHolder(@NonNull View itemView) {
            super(itemView);

            botTextView = itemView.findViewById(R.id.botTextView);

        }

    }


}
