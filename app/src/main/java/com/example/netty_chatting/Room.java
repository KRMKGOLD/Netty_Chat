package com.example.netty_chatting;

import androidx.annotation.NonNull;

public class Room {
    private String name;
    private String count;

    Room(String name, String count) {
        this.name = name;
        this.count = count;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + name + ", " + count + "]";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }
}
