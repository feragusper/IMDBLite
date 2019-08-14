package com.feragusper.imdblite.movies.android.viewmodel

import androidx.lifecycle.MutableLiveData
import com.feragusper.imdblite.common.android.BaseViewModel
import com.feragusper.imdblite.common.interactor.UseCase
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.interactor.GetFavoriteMoviesUseCase
import com.feragusper.imdblite.movies.interactor.GetMoviesUseCase
import com.feragusper.imdblite.movies.interactor.SaveFavoriteMovieUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesViewModel
@Inject constructor(private val getMoviesUseCase: GetMoviesUseCase,
                    private val saveFavoriteUseCase: SaveFavoriteMovieUseCase,
                    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase) : BaseViewModel() {

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()
    var favoriteMovies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun loadMovies() = getMoviesUseCase(UseCase.None()) { it.either(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        this.movies.value = movies
    }

    private fun handleFavoriteMovieList(movies: List<Movie>) {
        this.favoriteMovies.value = movies
    }

    fun saveFavorite(movie: Movie, favorite: Boolean) {
        saveFavoriteUseCase(SaveFavoriteMovieUseCase.Params(movie, favorite)) { it.either({
            // TODO Do something with the error?
        }, {
            getFavoriteMoviesUseCase(UseCase.None()) { it.either(::handleFailure, ::handleFavoriteMovieList) }
        })}
    }

}