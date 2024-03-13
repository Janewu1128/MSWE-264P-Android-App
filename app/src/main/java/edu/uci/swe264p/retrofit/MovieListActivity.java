package edu.uci.swe264p.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private static final String TAG = MovieListActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        recyclerView = findViewById(R.id.rvMovieList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch the movie list
        getMovieList();
    }

    private void getMovieList() {
        MovieApiService movieApiService = MainActivity.retrofit.create(MovieApiService.class);
        Call<TopRatedResponse> call = movieApiService.getMovieList(MainActivity.API_KEY);

        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                if(response.body() == null){
                    Log.e(TAG,"NULL!!!!");
                }
                if (response.isSuccessful()) {
                    List<Movie> movieList = new ArrayList<>();
                    recyclerView.setAdapter(new MovieListAdapter(movieList));
                    movieList.clear();
                    movieList.addAll(response.body().getMovieList());
                    Log.e(TAG, String.valueOf(response.body()));
                } else {
                    Log.e(TAG, "Response not successful");
                    Toast.makeText(MovieListActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopRatedResponse> call, Throwable throwable) {
                Log.e(TAG, "API call failed: " + throwable.getMessage());
                Toast.makeText(MovieListActivity.this, "Failed to load movies. Please check your connection.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
