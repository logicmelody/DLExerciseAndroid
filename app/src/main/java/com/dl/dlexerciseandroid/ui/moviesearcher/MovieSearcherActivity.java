package com.dl.dlexerciseandroid.ui.moviesearcher;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.HttpUtils;
import com.dl.dlexerciseandroid.utility.utils.TmdbApiUtils;

import java.io.IOException;

public class MovieSearcherActivity extends AppCompatActivity {

    private static final int PAUSE_FOR_SEARCH_TEXT_CHANGED = 1000;

    private Toolbar mToolbar;
    private EditText mSearchBox;

    private Handler mHandler;

    private SearchMovieAsyncTask mSearchMovieAsyncTask;

    private Runnable mSearchMovieRunnable;

    private TextWatcher mSearchBoxTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            triggerSearchMovieAsyncTask(String.valueOf(charSequence));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void triggerSearchMovieAsyncTask(String queryText) {
        if (mSearchMovieRunnable != null) {
            mHandler.removeCallbacks(mSearchMovieRunnable);
            mSearchMovieRunnable = null;
        }

        if (TextUtils.isEmpty(queryText)) {
            return;
        }

        mSearchMovieRunnable = new SearchMovieRunnable(queryText);
        mHandler.postDelayed(mSearchMovieRunnable, PAUSE_FOR_SEARCH_TEXT_CHANGED);
    }


    private class SearchMovieRunnable implements Runnable {

        private String mQueryText;


        public SearchMovieRunnable(String queryText) {
            mQueryText = queryText;
        }

        @Override
        public void run() {
            if (mSearchMovieAsyncTask != null) {
                if (mQueryText.equals(mSearchMovieAsyncTask.getSearchText())) {
                    return;
                }

                mSearchMovieAsyncTask.cancel(true);
                mSearchMovieAsyncTask = null;
            }

            mSearchMovieAsyncTask = new SearchMovieAsyncTask(mQueryText);
            mSearchMovieAsyncTask.execute();
        }
    }

    private class SearchMovieAsyncTask extends AsyncTask<Void, Void, String> {

        private String mSearchText;


        public SearchMovieAsyncTask(String searchText) {
            mSearchText = searchText;
        }

        public void setSearchText(String searchText) {
            mSearchText = searchText;
        }

        public String getSearchText() {
            return mSearchText;
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d("danny", "SearchMovieAsyncTask doInBackground()");
            Log.d("danny", "Search movie text = " + mSearchText);

            // 2. Parse Json string to object list

            try {
                return HttpUtils.getJsonStringFromUrl(TmdbApiUtils.getSearchMoviesByTextUrl(mSearchText));

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Log.d("danny", "SearchMovieAsyncTask onCancelled()");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (isCancelled()) {
                Log.d("danny", "SearchMovieAsyncTask onPostExecute() isCancelled()");

                return;
            }

            if (TextUtils.isEmpty(s)) {
                return;
            }

            // Put object list to adapter
            Log.d("danny", "Movie search json = " + s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_searcher);
        initialize();
    }

    private void initialize() {
        mHandler = new Handler();

        findViews();
        setupActionBar();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mSearchBox = (EditText) findViewById(R.id.edit_text_movie_searcher_search_box);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mSearchBox.addTextChangedListener(mSearchBoxTextWatcher);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mSearchBox.removeTextChangedListener(mSearchBoxTextWatcher);
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
