package com.feragusper.imdblite.movies.android

import androidx.lifecycle.MutableLiveData
import com.feragusper.imdblite.common.android.BaseViewModel
import com.feragusper.imdblite.common.interactor.UseCase
import com.feragusper.imdblite.movies.MovieView
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.interactor.GetMoviesUseCase
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) : BaseViewModel() {

    var movies: MutableLiveData<List<MovieView>> = MutableLiveData()

    fun loadMovies() = getMoviesUseCase(UseCase.None()) { it.either(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        this.movies.value = movies.map { MovieView(it.id, it.poster) }
    }
}