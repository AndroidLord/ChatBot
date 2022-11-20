package com.example.chattalk.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.chattalk.Model.ChatsModel;
import com.example.chattalk.Util.ChatRoomDatabase;

import java.util.List;

public class ChatRepository {

    private ChatDao chatDao;

    private LiveData<List<ChatsModel>> allChat;

    public ChatRepository(Application application){

        ChatRoomDatabase db = ChatRoomDatabase.getDatabase(application);
        chatDao = db.chatDao();
        allChat = chatDao.getAllData();
    }


    public void insert(ChatsModel chatsModel){
        ChatRoomDatabase.databaseWriteExecutor.execute(()->{
            chatDao.insert(chatsModel);
        });
    }

    public LiveData<List<ChatsModel>> getAllChat(){return allChat;}




}
