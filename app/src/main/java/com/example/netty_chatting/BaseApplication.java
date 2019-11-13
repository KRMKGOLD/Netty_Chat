package com.example.netty_chatting;

import android.app.Application;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.Socket;

public class BaseApplication extends Application {
    public static Socket socket;
    private static BufferedReader in;
    private static OutputStream out;

    public static void setSocket(Socket socket) {
        BaseApplication.socket = socket;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setIn(BufferedReader in) {
        BaseApplication.in = in;
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static void setOut(OutputStream out) {
        BaseApplication.out = out;
    }

    public static OutputStream getOut() {
        return out;
    }
}
