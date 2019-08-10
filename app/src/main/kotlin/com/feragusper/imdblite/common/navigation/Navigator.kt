package com.feragusper.imdblite.common.navigation

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
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

    fun showMovieDetails(activity: FragmentActivity, movie: Any, navigationExtras: Any): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class Extras(val transitionSharedElement: View)
}


