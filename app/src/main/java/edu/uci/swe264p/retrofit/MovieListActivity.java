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

    //onCreate method is called when the activity starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        //find the RecyclerView by ID and set up RecyclerView
        recyclerView = findViewById(R.id.rvMovieList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //fetch movie data from the API
        getMovieList();
    }

    private void getMovieList() {
        //create an instance of MovieApiService using Retrofit
        MovieApiService movieApiService = MainActivity.retrofit.create(MovieApiService.class);
        //start an asynchronous call to fetch the top-rated movies
        Call<TopRatedResponse> call = movieApiService.getMovieList(MainActivity.API_KEY);

        call.enqueue(new Callback<TopRatedResponse>() {
            //handles the response from the API call
            @Override
            public void onResponse(@NonNull Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                if(response.body() == null){
                    Log.e(TAG,"NULL!!!!");
                }
                //updates the RecyclerView with the fetched movie data
                if (response.isSuccessful()) {
                    //create list and set to the adapter of the RecyclerView
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

            //handles failures in the API call
            @Override
            public void onFailure(@NonNull Call<TopRatedResponse> call, Throwable throwable) {
                Log.e(TAG, "API call failed: " + throwable.getMessage());
                Toast.makeText(MovieListActivity.this, "Failed to load movies. Please check your connection.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
