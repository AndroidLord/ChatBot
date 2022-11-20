package com.example.chattalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chattalk.Adapter.ChatRVAdapter;
import com.example.chattalk.Data.RetrofitAPI;
import com.example.chattalk.Model.ChatViewModel;
import com.example.chattalk.Model.ChatsModel;
import com.example.chattalk.Model.MsgModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private FloatingActionButton sendButton;

    public static final String BOT_KEY = "bot";
    public static final String USER_KEY = "user";

    // CREATING ARRAYLIST
    private LiveData<List<ChatsModel>> chatsModelList;
    private ChatRVAdapter chatAdapter;
    private ChatViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chatRecyclerView = findViewById(R.id.recycleView);
        sendButton = findViewById(R.id.fab_send);
        messageEditText = findViewById(R.id.editMessage);




        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        chatViewModel =  new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ChatViewModel.class);

        // Obersering the Recycle View
        chatViewModel.getAllChat().observe(this,chatsModels -> {

            chatAdapter = new ChatRVAdapter(chatsModels,this);
            chatRecyclerView.setAdapter(chatAdapter);
            chatRecyclerView.scrollToPosition(chatsModels.size()-1);

        });

        sendButton.setOnClickListener(view -> {

            if(messageEditText.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this,"Please Enter a Message!",Toast.LENGTH_SHORT).show();
                return;
            }

            getResponse(messageEditText.getText().toString().trim());
            messageEditText.setText("");
        });


    }
    private void getResponse(String message){

        ChatViewModel.insert(new ChatsModel(message,USER_KEY));

        String url = "http://api.brainshop.ai/get?bid=170523&key=5kyRHNTHP9MeORyI&uid=[uid]&msg="+message;
        String BASE_URL = "http://api.brainshop.ai/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModel> call = retrofitAPI.getMessage(url);

        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if(response.isSuccessful()){
                    MsgModel newModal = response.body();
                    ChatViewModel.insert(new ChatsModel(newModal.getCnt(),BOT_KEY));

                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                ChatViewModel.insert(new ChatsModel("Sorry, I didn't hear u?",BOT_KEY));
             }

        });


    }
}