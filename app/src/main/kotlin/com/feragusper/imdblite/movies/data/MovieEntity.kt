package com.feragusper.imdblite.movies.data

import com.feragusper.imdblite.BuildConfig
import com.feragusper.imdblite.movies.domain.Movie

data class MovieEntity(private val id: Int, private val poster_path: String) {
    fun toMovie() = Movie(id, BuildConfig.TMDB_API_HOST_IMAGES + poster_path)
}
