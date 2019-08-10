package com.feragusper.imdblite.movies.domain

import com.feragusper.imdblite.common.extension.empty

data class Movie(val id: Int, val poster: String) {

    companion object {
        fun empty() = Movie(0, String.empty())
    }
}
