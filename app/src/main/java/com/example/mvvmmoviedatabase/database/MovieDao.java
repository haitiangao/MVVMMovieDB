package com.example.mvvmmoviedatabase.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mvvmmoviedatabase.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * from movies_table")
    LiveData<List<Movie>> getAllMovies();

    @Query("DELETE FROM movies_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);
}
