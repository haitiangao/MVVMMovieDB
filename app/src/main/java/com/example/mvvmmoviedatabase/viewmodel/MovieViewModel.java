package com.example.mvvmmoviedatabase.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmmoviedatabase.database.MovieRepository;
import com.example.mvvmmoviedatabase.model.Movie;
import com.example.mvvmmoviedatabase.model.Responses;
import com.example.mvvmmoviedatabase.network.MovieRetrofitInstance;
import com.example.mvvmmoviedatabase.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends AndroidViewModel {

    private MovieRetrofitInstance retrofitInstance = new MovieRetrofitInstance();
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> allMovies;

    public MovieViewModel(Application application){
        super(application);
        movieRepository = new MovieRepository(application);
        allMovies = movieRepository.getAllMovie();
    }

    public LiveData<List<Movie>> getAllMovies() { return allMovies; }


    public void getMovies(String query){
        Log.d("TAG_H", "getMovies called!");

        retrofitInstance.getMovie(Constants.API_KEY,query)
                .enqueue(new Callback<Responses>() {
                    @Override
                    public void onResponse(Call<Responses> call, Response<Responses> response) {
                        Log.d("TAG_H", "onResponse called!");
                        //setUpMovies(response.body().results);
                        movieRepository.deleteAllMovies();
                        assert response.body() != null;
                        for(int i = 0; i<response.body().results.size(); i++)
                            movieRepository.insert(response.body().results.get(i));
                    }

                    @Override
                    public void onFailure(Call<Responses> call, Throwable t) {
                        Log.d("TAG_H", "Error on getMovie: " + t);

                    }
                });

    }

}
