package com.mohamedragab.androidtask.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mohamedragab.androidtask.R;
import com.mohamedragab.androidtask.VolleySingleton;
import com.mohamedragab.androidtask.controller.UserAdapter;
import com.mohamedragab.androidtask.models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<UserModel> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        movieList = new ArrayList<>();
        fetchUsers();


    }

    private void fetchUsers() {

        String url = "https://my-json-server.typicode.com/SharminSirajudeen/test_resources/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String albumId = jsonObject.getString("albumId");
                                String userId = jsonObject.getString("userId");
                                String name = jsonObject.getString("name");
                                String url = jsonObject.getString("url");
                                String thumbnailUrl = jsonObject.getString("thumbnailUrl");

                                UserModel user = new UserModel(albumId, userId, name, url, thumbnailUrl);
                                movieList.add(user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            UserAdapter adapter = new UserAdapter(MainActivity.this, movieList);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}