package com.feragusper.imdblite.movies.android.activity

import com.feragusper.imdblite.movies.extension.MovieId
import android.content.Context
import android.content.Intent
import com.feragusper.imdblite.common.android.BaseActivity
import com.feragusper.imdblite.movies.android.fragment.MovieDetailsFragment

class MovieDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE_ID = "com.feragusper.imdblite.INTENT_EXTRA_PARAM_MOVIE_ID"

        fun callingIntent(context: Context, movieId: MovieId): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_MOVIE_ID, movieId)
            return intent
        }
    }

    override fun fragment() = MovieDetailsFragment.forMovie(
        intent.getIntExtra(
            INTENT_EXTRA_PARAM_MOVIE_ID,
            -1
        )
    )
}
