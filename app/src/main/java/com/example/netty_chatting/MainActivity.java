package com.example.netty_chatting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private EditText nameEditText;
    private Button sendDataButton;

    private RecyclerView recyclerView;
    private ArrayList<Room> roomList;
    private MainAdapter adapter;

    private BufferedReader in;
    private OutputStream out;


    @Override
    protected void onStart() {
        super.onStart();
        startSocket();
        run();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roomList = new ArrayList<>();

        nameEditText = findViewById(R.id.nameEditText);
        sendDataButton = findViewById(R.id.sendDataButton);
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new MainAdapter(this, roomList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        sendDataButton.setOnClickListener((view) -> {
            if (!nameEditText.getText().toString().isEmpty()) {
                sendMsg("100|");
                sendMsg("150|" + nameEditText.getText().toString());
            }
        });
    }

    private void startSocket() {
        new Thread(() -> {
            try {
                mSocket = new Socket("10.156.145.149", 5000);
                in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                out = mSocket.getOutputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void run() {
        new Thread(() -> {
            Looper.prepare();
            try {
                while (true) {
                    if (in == null || out == null || mSocket == null) {
                        Log.d("null", "null");
                    } else {
                        String msg = in.readLine();
                        String[] msgs = msg.split("\\|");
                        String protocol = msgs[0];

                        switch (protocol) {
                            case "160":
                                if (msgs.length > 1) {
                                    String[] roomLists = msgs[1].split(",");
                                    roomList.clear();

                                    for (String roomString : roomLists) {
                                        String[] roomData = roomString.split("--");
                                        Room room = new Room(roomData[0], roomData[1]);
                                        roomList.add(room);
                                    }

                                    Log.d("roomList", roomList.toString());
                                    adapter.setRoomList(roomList);
                                    new Handler().post(() -> adapter.notifyItemRangeChanged(0, roomList.size()));
                                }
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        sendMsg("500|");
        super.onDestroy();
    }

    private void sendMsg(final String msg) {
        new Thread(() -> {
            try {
                out.write((msg + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}