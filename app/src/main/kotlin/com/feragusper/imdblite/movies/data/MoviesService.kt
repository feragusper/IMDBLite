package com.feragusper.imdblite.movies.data

import com.feragusper.imdblite.BuildConfig
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService
@Inject constructor(retrofit: Retrofit) {
    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

    fun movies() = moviesApi.movies(BuildConfig.TMDB_API_KEY)
}
