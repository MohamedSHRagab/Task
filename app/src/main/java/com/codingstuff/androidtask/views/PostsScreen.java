package com.codingstuff.movielist.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.codingstuff.movielist.R;
import com.codingstuff.movielist.VolleySingleton;
import com.codingstuff.movielist.controller.PostsAdapter;
import com.codingstuff.movielist.controller.UserAdapter;
import com.codingstuff.movielist.models.PostModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostsScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<PostModel> postslist;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        Bundle bundle = getIntent().getExtras();

        String url = bundle.getString("url");
        String userId = bundle.getString("userId");


        recyclerView = findViewById(R.id.recyclerview);
        imageView = findViewById(R.id.imageview);
        Glide.with(PostsScreen.this).load(url).into(imageView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        postslist = new ArrayList<>();
        fetchMovies(userId);


    }

    private void fetchMovies(String Id) {

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
                                    postslist.add(post);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            PostsAdapter adapter = new PostsAdapter(PostsScreen.this, postslist);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostsScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}