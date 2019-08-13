package com.feragusper.imdblite.movies.data.network

data class ListOfMoviesResultEntity(private val results: List<MovieEntity>) {
    fun toMovies() = results.map { movieEntity -> movieEntity.toMovie() }

    companion object {
        fun empty(): ListOfMoviesResultEntity {
            return ListOfMoviesResultEntity(emptyList())
        }
    }
}
