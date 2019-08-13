package com.feragusper.imdblite.movies.data.repository

import com.feragusper.imdblite.movies.extension.MovieId
import android.content.Context
import android.util.LruCache
import com.feragusper.imdblite.common.exception.Failure
import com.feragusper.imdblite.common.functional.Either
import com.feragusper.imdblite.movies.domain.Movie
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A cache for holding [Movie]s between Activities/Fragments.
 *
 * In order to get a cleaner architecture, we pass around as little data as possible,
 * in the case of a [Movie], that's a [MovieId].
 *
 * Then, the data is provided to components based on the [MovieId].
 *
 */
@Singleton
class MovieCache @Inject constructor(val context: Context) {

    private val cache = LruCache<MovieId, Movie>(1000)

    val hasMovies: Boolean
        get() = cache.size() != 0

    fun put(movie: Movie) {
        cache.put(movie.id, movie)
    }

    operator fun get(movieId: MovieId) = cache.get(movieId)

    fun wipeAll() {
        cache.evictAll()
    }

    fun putAll(movies: List<Movie>) {
        movies.forEach { movie ->
            put(movie)
        }
    }

    fun getAll(): Either<Failure, List<Movie>> {
        val movies = mutableListOf<Movie>()
        cache.snapshot().keys.forEach { movieId ->
            movies.add(cache.get(movieId))
        }
        return Either.Right(movies)
    }
}