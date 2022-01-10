package com.t.memesworld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t.memesworld.FullImageView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<com.t.memesworld.NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<com.t.memesworld.NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_feed,parent,false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, @SuppressLint("RecyclerView") int position) {


        final NoticeData currentItem = list.get(position);

        holder.deleteNoticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        try {
            if (currentItem.getImage() != null) {
             Glide.with(context).load(currentItem.getImage()).into(holder.deleteNoticeImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deleteNoticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImageView.class);
                intent.putExtra("image",currentItem.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class NoticeViewAdapter extends RecyclerView.ViewHolder {
        private TextView deleteNoticeTitle, date, time;
        private ImageView deleteNoticeImage;


        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);

            deleteNoticeTitle =  itemView.findViewById(R.id.deleteNoticeTitle);
            deleteNoticeImage =  itemView.findViewById(R.id.NoticeImage);
            date =  itemView.findViewById(R.id.date);
            time =  itemView.findViewById(R.id.time);


        }
    }
}
