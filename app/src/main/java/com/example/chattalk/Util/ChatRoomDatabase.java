package com.example.chattalk.Util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.chattalk.Data.ChatDao;
import com.example.chattalk.Model.ChatsModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {ChatsModel.class},version = 1,exportSchema = false)

public abstract class ChatRoomDatabase extends RoomDatabase {

    private static ChatRoomDatabase INSTANCE;
    public abstract ChatDao chatDao();

    public static final int NO_OF_THREADS = 4;
    public static final Executor databaseWriteExecutor =
            Executors.newFixedThreadPool(NO_OF_THREADS);

    public static ChatRoomDatabase getDatabase(final Context context){

        if (INSTANCE == null){
           synchronized (ChatRoomDatabase.class){
               if (INSTANCE==null){
                   INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                           ChatRoomDatabase.class,"Chat_Database")
                           .addCallback(sRoomDatabase)
                           .build();
               }
           }
        }

        return INSTANCE;
    }



    private static final RoomDatabase.Callback sRoomDatabase =
            new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(()->{

                        ChatDao chatDao = INSTANCE.chatDao();

                        chatDao.deleteAll();


                    });

                }
            };

}
