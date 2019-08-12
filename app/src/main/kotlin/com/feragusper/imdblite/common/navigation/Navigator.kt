package com.feragusper.imdblite.common.navigation

import com.feragusper.imdblite.movies.extension.MovieId
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.feragusper.imdblite.movies.android.MovieDetailsActivity
import com.feragusper.imdblite.movies.android.MoviesActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper that handles the navigation/transitions between screens within the application
 */
@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showMovies(context)
    }

    private fun showMovies(context: Context) = context.startActivity(MoviesActivity.callingIntent(context))

    fun showMovieDetails(activity: FragmentActivity, movieId: MovieId, navigationExtras: Extras) {
        val intent = MovieDetailsActivity.callingIntent(activity, movieId)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val activityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
            activity.startActivity(intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
        }
    }

    class Extras(val transitionSharedElement: View)
}


