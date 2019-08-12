package com.feragusper.imdblite.movies.android

import com.feragusper.imdblite.movies.extension.MovieId
import androidx.lifecycle.MutableLiveData
import com.feragusper.imdblite.common.android.BaseViewModel
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.interactor.GetMovieDetailsUseCase
import javax.inject.Inject

class MovieDetailsViewModel
@Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) : BaseViewModel() {

    var movieDetails: MutableLiveData<Movie> = MutableLiveData()

    fun loadMovieDetails(movieId: MovieId) =
        getMovieDetailsUseCase(GetMovieDetailsUseCase.Params(movieId)) {
            it.either(
                ::handleFailure,
                ::handleMovieDetails
            )
        }

    private fun handleMovieDetails(movie: Movie) {
        this.movieDetails.value = movie
    }
}