package com.feragusper.imdblite.movies.data.network

import com.feragusper.imdblite.movies.extension.MovieId
import com.feragusper.imdblite.BuildConfig
import com.feragusper.imdblite.movies.domain.Movie

data class MovieEntity(
    private val id: MovieId,
    private val poster_path: String,
    private val title: String,
    private val overview: String,
    private val release_date: String,
    private val vote_average: String,
    private val vote_count: String,
    private val adult: Boolean
) {
    fun toLatestMovie() = Movie(
        id = id,
        poster = BuildConfig.TMDB_API_HOST_IMAGES + poster_path,
        title = title,
        overview = overview,
        releaseDate = release_date,
        voteAverage = vote_average,
        voteCount = vote_count,
        contentRating = adult.toContentRating(),
        latest = true
    )

    fun toMovie() = Movie(
        id = id,
        poster = BuildConfig.TMDB_API_HOST_IMAGES + poster_path,
        title = title,
        overview = overview,
        releaseDate = release_date,
        voteAverage = vote_average,
        voteCount = vote_count,
        contentRating = adult.toContentRating()
    )
}

private fun Boolean.toContentRating(): String {
    return when (this) {
        true -> Movie.ContentRating.ADULT
        false -> Movie.ContentRating.EVERYBODY
    }.name
}

