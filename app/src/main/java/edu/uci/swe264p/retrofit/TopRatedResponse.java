package edu.uci.swe264p.retrofit;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TopRatedResponse {
    @SerializedName("results")
    private List<Movie> topRatedResponse;

    public TopRatedResponse(List<Movie> topRatedResponse) {
        this.topRatedResponse = topRatedResponse;
    }

    public List<Movie> getMovieList() {
        return topRatedResponse;
    }
}