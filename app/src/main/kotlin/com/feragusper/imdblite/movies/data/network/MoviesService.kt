package com.feragusper.imdblite.movies.data.network

import android.annotation.SuppressLint
import com.feragusper.imdblite.BuildConfig
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService
@Inject constructor(retrofit: Retrofit) {
    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

    @SuppressLint("SimpleDateFormat")
    fun movies() = moviesApi.moviesOfCurrentYear(BuildConfig.TMDB_API_KEY, SimpleDateFormat("yyyy").format(Date()))
}
