package com.example.chattalk.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chattalk.Model.ChatsModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ChatDao {

    // C-creation, R-read, U-update, D-delete

    @Insert
    public void insert(ChatsModel chatsModel);

    @Query("Select * from chat_table")
    public LiveData<List<ChatsModel>> getAllData();

    @Query("DELETE FROM CHAT_TABLE")
    public void deleteAll();

}
