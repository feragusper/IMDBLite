package com.feragusper.imdblite.movies.data

data class DiscoverMoviesResultEntity(private val results: List<MovieEntity>) {
    fun toMovies() = results.map { movieEntity -> movieEntity.toMovie() }

    companion object {
        fun empty(): DiscoverMoviesResultEntity {
            return DiscoverMoviesResultEntity(emptyList())
        }
    }
}
