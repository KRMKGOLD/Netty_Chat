package com.example.netty_chatting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context context;
    private ArrayList<Room> roomList = new ArrayList<>();

    MainAdapter(Context context, ArrayList<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(roomList.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    void setRoomList(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvRoomCount;

        MainViewHolder(View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tv_room_name);
            tvRoomCount = itemView.findViewById(R.id.tv_room_count);
        }

        void bind(Room room) {
            tvRoomName.setText(room.getName());
            tvRoomCount.setText(room.getCount());
        }
    }
}
