package com.feragusper.imdblite.movies.data.repository

import com.feragusper.imdblite.movies.extension.MovieId
import com.feragusper.imdblite.common.exception.Failure
import com.feragusper.imdblite.common.functional.Either
import com.feragusper.imdblite.movies.data.network.DiscoverMoviesResultEntity
import com.feragusper.imdblite.movies.data.network.MoviesService
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.exception.MovieFailure
import retrofit2.Call
import javax.inject.Inject

interface MoviesRepository {
    fun movies(): Either<Failure, List<Movie>>
    fun movieDetails(movieId: MovieId): Either<Failure, Movie>

    class MoviesRepositoryImpl
    @Inject constructor(
        private val service: MoviesService,
        private val moviesCache: MovieCache
    ) : MoviesRepository {

        override fun movies(): Either<Failure, List<Movie>> {
            val result = request(
                service.movies(), { it.toMovies() },
                DiscoverMoviesResultEntity.empty()
            )

            when (result) {
                is Either.Right -> moviesCache.putAll(result.b)
            }

            return result
        }

        override fun movieDetails(movieId: MovieId): Either<Failure, Movie> {
            val movie = moviesCache.get(movieId)
            return when (movie) {
                is Movie -> Either.Right(movie)
                null -> Either.Left(MovieFailure.NonExistentMovie)
                else -> Either.Left(MovieFailure.NonExistentMovie)
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }
    }
}
