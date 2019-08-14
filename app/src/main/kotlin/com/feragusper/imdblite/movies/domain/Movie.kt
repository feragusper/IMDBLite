package com.feragusper.imdblite.movies.domain

import com.feragusper.imdblite.movies.extension.MovieId
import com.feragusper.imdblite.common.extension.empty

data class Movie(
    val id: MovieId,
    val poster: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: String,
    val voteCount: String,
    val contentRating: String,
    var favorite: Boolean = false,
    val latest: Boolean = false
) {
    enum class ContentRating {
        ADULT,
        EVERYBODY
    }

    companion object {
        fun empty() = Movie(
            id = 0,
            poster = String.empty(),
            title = String.empty(),
            overview = String.empty(),
            releaseDate = String.empty(),
            voteAverage = String.empty(),
            voteCount = String.empty(),
            contentRating = String.empty()
        )
    }
}
