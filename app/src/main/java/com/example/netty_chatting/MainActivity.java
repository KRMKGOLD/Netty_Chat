package com.example.netty_chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private EditText nameEditText;
    private Button sendDataButton;

    private BufferedReader in;
    private OutputStream out;

    @Override
    protected void onStart() {
        super.onStart();
        startSocket();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        sendDataButton = findViewById(R.id.sendDataButton);

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