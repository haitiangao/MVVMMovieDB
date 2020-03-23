package com.example.mvvmmoviedatabase.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvvmmoviedatabase.R;
import com.example.mvvmmoviedatabase.adapter.MovieListAdapter;
import com.example.mvvmmoviedatabase.model.Movie;
import com.example.mvvmmoviedatabase.network.MovieRetrofitInstance;
import com.example.mvvmmoviedatabase.util.Constants;
import com.example.mvvmmoviedatabase.viewmodel.MovieViewModel;
import com.example.mvvmmoviedatabase.receiver.InternetReciever;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.UserClickListener{

    //private MovieRetrofitInstance retrofitInstance = new MovieRetrofitInstance();
    private List<Movie> movieList = new ArrayList<Movie>();
    private MovieViewModel movieViewModel;
    private InternetReciever internetReciever = new InternetReciever();

    @BindView(R.id.queryEditText)
    EditText queryEditText;
    @BindView(R.id.searchButton)
    Button searchButton;
    @BindView(R.id.movieRecycleView)
    RecyclerView movieRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        final MovieListAdapter movieListAdapter = new MovieListAdapter(this,movieList,this);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        movieRecycleView.setLayoutManager(new LinearLayoutManager(this));
        movieRecycleView.setAdapter(movieListAdapter);
        movieRecycleView.addItemDecoration(itemDecoration);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        registerReceiver(internetReciever,new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        movieViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable final List<Movie> movies) {
                movieListAdapter.setMovie(movies);
            }
        });
    }

    public void setUpMovies(List<Movie> movieList){
        MovieListAdapter movieListAdapter = new MovieListAdapter(this, movieList,this);
        movieRecycleView.setAdapter(null);
        movieRecycleView.setAdapter(movieListAdapter);
        movieListAdapter.notifyDataSetChanged();

    }

    @Override
    public void openMovie(Movie movie) {
        Intent openMovieIntent= new Intent(this, DisplayMovieActivity.class);
        openMovieIntent.putExtra(Constants.MOVIE_KEY, movie);
        startActivity(openMovieIntent);
    }

    @OnClick(R.id.searchButton)
    public void getQuery(View view) {
        String query = queryEditText.getText().toString().trim();
        if(!query.isEmpty())
            movieViewModel.getMovies(query);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(internetReciever);

    }
}

