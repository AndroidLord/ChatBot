package com.example.chattalk.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.chattalk.Data.ChatRepository;
import com.example.chattalk.Util.ChatRoomDatabase;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    public static ChatRepository repository;
    private final LiveData<List<ChatsModel>> allChat;

    public ChatViewModel(@NonNull Application application) {
        super(application);

        repository = new ChatRepository(application);
        allChat = repository.getAllChat();

    }

    public static void insert(ChatsModel chatsModel){
        repository.insert(chatsModel);
    }

    public LiveData<List<ChatsModel>> getAllChat(){return allChat;}
}
