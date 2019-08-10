package com.feragusper.imdblite.common.di

import com.feragusper.imdblite.AndroidApplication
import com.feragusper.imdblite.common.di.viewmodel.ViewModelModule
import com.feragusper.imdblite.common.navigation.RouteActivity
import com.feragusper.imdblite.movies.android.MoviesFragment
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
    fun inject(moviesFragment: MoviesFragment)
}
