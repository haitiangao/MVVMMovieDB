package com.example.mvvmmoviedatabase.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mvvmmoviedatabase.model.Movie;

import java.util.List;

public class MovieRepository {

    private MovieDao movieDao;
    private LiveData<List<Movie>> movieList;



    public MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        movieDao = db.movieDao();
        movieList = movieDao.getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovie() {
        return movieList;
    }
    public void deleteAllMovies(){
        //movieDao.deleteAll();
        new deleteAsyncTask(movieDao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieDao asyncTaskDao;

        deleteAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }

        public void insert (Movie movie) {
        new insertAsyncTask(movieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao asyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
