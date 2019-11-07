package com.example.netty_chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private EditText nameEditText;
    private Button sendDataButton;

    private BufferedReader in;
    private OutputStream out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        sendDataButton = findViewById(R.id.sendDataButton);

        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mSocket = new Socket("10.156.145.149", 5000);
                            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                            out = mSocket.getOutputStream();
                            sendMsg("100|");
                            sendMsg("150|Testing계정입니다");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        thread.start();

        sendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoomList();
            }
        });
    }

    private void sendMsg(final String msg) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            out.write((msg + "\n").getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    private void getRoomList() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String[] msgs = in.readLine().split("\\|");
                            Log.d("msgs", Arrays.toString(msgs));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}
