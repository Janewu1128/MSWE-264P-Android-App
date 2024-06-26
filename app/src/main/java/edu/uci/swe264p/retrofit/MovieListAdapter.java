package edu.uci.swe264p.retrofit;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    //list to hold Movie objects
    private List<Movie> topRatedResponse;

    //initializes the adapter with a list of Movie objects
    MovieListAdapter(List<Movie> list) {
        this.topRatedResponse = list;
    }

    //holds the view for each item in the RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMovie;
        TextView tvTitle;
        TextView tvReleaseDate;
        TextView tvVote;
        TextView tvOverview;

        ViewHolder(View movieRow) {
            super(movieRow);
            ivMovie = movieRow.findViewById(R.id.ivMovie);
            tvTitle = movieRow.findViewById(R.id.tvTitle);
            tvReleaseDate = movieRow.findViewById(R.id.tvReleaseDate);
            tvVote = movieRow.findViewById(R.id.tvVote);
            tvOverview = movieRow.findViewById(R.id.tvOverview);
        }
    }

    //creates a ViewHolder for each item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view);
    }

    //binds the data from topRatedResponse list to the views in each ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = topRatedResponse.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(holder.ivMovie);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        holder.tvVote.setText(String.format(Locale.ENGLISH, "%.1f", movie.getVoteAverage()));
        holder.tvOverview.setText(movie.getOverview());
    }

    //returns the total number of items in the data list
    @Override
    public int getItemCount() {
        return topRatedResponse.size();
    }

}