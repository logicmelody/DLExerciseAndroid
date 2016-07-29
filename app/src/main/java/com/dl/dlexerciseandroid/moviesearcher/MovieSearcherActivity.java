package com.dl.dlexerciseandroid.moviesearcher;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.background.service.MovieInfoService;

public class MovieSearcherActivity extends AppCompatActivity {

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_searcher);
        initialize();
        startService(MovieInfoService.generateSearchMoviesByTextIntent(this, "ghost"));
    }

    private void initialize() {
        findViews();
        setupAction();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
    }

    private void setupAction() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
