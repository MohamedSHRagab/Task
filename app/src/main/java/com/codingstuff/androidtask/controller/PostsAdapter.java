package com.codingstuff.movielist.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.movielist.R;
import com.codingstuff.movielist.models.PostModel;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MovieHolder> {

    private Context context;
    private List<PostModel> posts;

    public PostsAdapter(Context context , List<PostModel> movies){
        this.context = context;
        posts = movies;
    }
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.postitem , parent , false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

        PostModel post = posts.get(position);

        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{

        TextView title , body;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
        }
    }
}
