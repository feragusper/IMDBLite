package com.feragusper.imdblite.common.di

import com.feragusper.imdblite.AndroidApplication
import com.feragusper.imdblite.common.di.viewmodel.ViewModelModule
import com.feragusper.imdblite.common.navigation.RouteActivity
import com.feragusper.imdblite.movies.android.fragment.FavoriteMoviesFragment
import com.feragusper.imdblite.movies.android.fragment.MovieDetailsFragment
import com.feragusper.imdblite.movies.android.fragment.LatestMoviesFragment
import com.feragusper.imdblite.movies.android.fragment.SearchMoviesResultFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Component to inject Application, Activities and Fragments
 */
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)
    fun inject(latestMoviesFragment: LatestMoviesFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
    fun inject(searchMoviesResultFragment: SearchMoviesResultFragment)
    fun inject(favoriteMoviesFragment: FavoriteMoviesFragment)
}
