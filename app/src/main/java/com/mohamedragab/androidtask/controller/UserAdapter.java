package com.mohamedragab.androidtask.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.mohamedragab.androidtask.R;
import com.mohamedragab.androidtask.models.PostModel;
import com.mohamedragab.androidtask.models.UserModel;
import com.mohamedragab.androidtask.views.PostsScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    int total_posts=0;

    private void fetchPosts(String Id) {

        String url = "https://my-json-server.typicode.com/SharminSirajudeen/test_resources/posts";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String userId = jsonObject.getString("userId");
                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                String body = jsonObject.getString("body");

                                if (userId.equals(Id)){
                                    PostModel post = new PostModel(userId, id, title,body);
                                    total_posts+=1;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
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
