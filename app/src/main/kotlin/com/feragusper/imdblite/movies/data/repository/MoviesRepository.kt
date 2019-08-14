package com.feragusper.imdblite.movies.data.repository

import android.util.Log
import com.feragusper.imdblite.movies.extension.MovieId
import com.feragusper.imdblite.common.exception.Failure
import com.feragusper.imdblite.common.functional.Either
import com.feragusper.imdblite.movies.data.network.ListOfMoviesResultEntity
import com.feragusper.imdblite.movies.data.network.MoviesService
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.exception.MovieFailure
import retrofit2.Call
import javax.inject.Inject

interface MoviesRepository {
    fun movies(): Either<Failure, List<Movie>>
    fun movieDetails(movieId: MovieId): Either<Failure, Movie>
    fun searchMovies(query: String?): Either<Failure, List<Movie>>
    fun saveFavorite(movie: Movie, favorite: Boolean): Either<Failure, Unit>
    fun favoriteMovies(): Either<Failure, List<Movie>>

    class MoviesRepositoryImpl
    @Inject constructor(
        private val service: MoviesService,
        private val moviesCache: MovieCache
    ) : MoviesRepository {

        override fun saveFavorite(movie: Movie, favorite: Boolean): Either<Failure, Unit> {
            movie.favorite = favorite
            moviesCache.put(movie)
            Log.i("favorite repository", "movie is ${movie.title} and favorite is ${movie.favorite}")
            return Either.Right(Unit)
        }

        override fun movies(): Either<Failure, List<Movie>> {
            return when (moviesCache.hasMovies) {
                true -> Either.Right(moviesCache.getAll().filter { movie -> movie.latest })
                false -> {
                    val result = request(
                        service.movies(), { listOfMoviesResultEntity -> listOfMoviesResultEntity.toLatestMovies() },
                        ListOfMoviesResultEntity.empty()
                    )

                    when (result) {
                        is Either.Right -> moviesCache.putAll(result.b)
                    }

                    result
                }
            }
        }

        override fun searchMovies(query: String?): Either<Failure, List<Movie>> {
            val result = request(
                service.searchMovies(query), { listOfMoviesResultEntity -> listOfMoviesResultEntity.toMovies() },
                ListOfMoviesResultEntity.empty()
            )

            when (result) {
                is Either.Right -> moviesCache.putAll(result.b)
            }

            return result
        }

        override fun favoriteMovies(): Either<Failure, List<Movie>> {
            return Either.Right(moviesCache.getAll().filter { movie -> movie.favorite })
        }

        override fun movieDetails(movieId: MovieId): Either<Failure, Movie> {
            val movie = moviesCache.get(movieId)
            return when (movie) {
                is Movie -> Either.Right(movie)
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
