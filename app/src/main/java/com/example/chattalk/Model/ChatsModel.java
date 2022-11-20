package com.example.chattalk.Model;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Chat_table")
public class ChatsModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String message;

    @NonNull
    private String sender;

    public ChatsModel() {
    }

    public ChatsModel(@NonNull String message,@NonNull String sender) {
        this.message = message;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    @NonNull
    public String getSender() {
        return sender;
    }

    public void setSender(@NonNull String sender) {
        this.sender = sender;
    }
}
