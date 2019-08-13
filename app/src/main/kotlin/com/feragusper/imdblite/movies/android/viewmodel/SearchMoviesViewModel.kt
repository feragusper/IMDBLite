package com.feragusper.imdblite.movies.android.viewmodel

import androidx.lifecycle.MutableLiveData
import com.feragusper.imdblite.common.android.BaseViewModel
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.interactor.SearchMoviesUseCase
import javax.inject.Inject

class SearchMoviesViewModel
@Inject constructor(private val searchMoviesUseCase: SearchMoviesUseCase) : BaseViewModel() {

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun searchMovies(query: String?) = searchMoviesUseCase(SearchMoviesUseCase.Params(query)) { it.either(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        this.movies.value = movies
    }
}