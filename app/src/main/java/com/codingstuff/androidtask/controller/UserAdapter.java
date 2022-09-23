package com.codingstuff.movielist.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingstuff.movielist.R;
import com.codingstuff.movielist.models.UserModel;
import com.codingstuff.movielist.views.PostsScreen;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MovieHolder> {

    private Context context;
    private List<UserModel> users;

    public UserAdapter(Context context , List<UserModel> movies){
        this.context = context;
        users = movies;
    }
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item , parent , false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

        UserModel user = users.get(position);

        holder.title.setText(user.getName());
        holder.overview.setText(user.getAlbumId());
        Glide.with(context).load(user.getUrl()).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , PostsScreen.class);

                Bundle bundle = new Bundle();
                bundle.putString("albumId" , user.getAlbumId());
                bundle.putString("userId" , user.getUserId());
                bundle.putString("name" , user.getName());
                bundle.putString("url" , user.getUrl());
                bundle.putString("thumbnailUrl" , user.getThumbnailUrl());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView title , overview;
        ConstraintLayout constraintLayout;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.title_tv);
            overview = itemView.findViewById(R.id.overview_tv);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
