package com.feragusper.imdblite.movies.android

import android.annotation.SuppressLint
import com.feragusper.imdblite.movies.extension.MovieId
import android.os.Build
import android.os.Bundle
import android.view.View
import com.feragusper.imdblite.R
import com.feragusper.imdblite.common.android.BaseFragment
import com.feragusper.imdblite.common.exception.Failure
import com.feragusper.imdblite.common.extension.*
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.exception.MovieFailure
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment() {

    companion object {
        private const val ARGUMENT_MOVIE_ID = "com.feragusper.imdblite.ARGUMENT_MOVIE_ID"

        fun forMovie(movieId: MovieId): MovieDetailsFragment {
            val movieDetailsFragment = MovieDetailsFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_MOVIE_ID, movieId)
            movieDetailsFragment.arguments = arguments

            return movieDetailsFragment
        }
    }

    @Inject
    lateinit var movieDetailsAnimator: MovieDetailsAnimator

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun layoutId() = R.layout.fragment_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        activity?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                movieDetailsAnimator.postponeEnterTransition(it)
            }
        }

        movieDetailsViewModel = viewModel(viewModelFactory) {
            observe(movieDetails, ::renderMovieDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            movieDetailsViewModel.loadMovieDetails(arguments?.get(ARGUMENT_MOVIE_ID) as MovieId)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                movieDetailsAnimator.cancelTransition(moviePoster)
            }
            moviePoster.loadFromUrl((arguments!![ARGUMENT_MOVIE_ID] as Movie).poster)
        }
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            movieDetailsAnimator.fadeInvisible(scrollView, movieDetails)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderMovieDetails(movie: Movie?) {
        movie?.let {
            with(movie) {
                activity?.let {
                    moviePoster.loadUrlAndPostponeEnterTransition(poster, it)
                    it.toolbar.title = title
                }
                movieSynopsis.text = overview
                movieReleaseDate.text = releaseDate
                movieRating.text = "$voteAverage ($voteCount)"
                movieContentRating.text = contentRating
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            movieDetailsAnimator.fadeVisible(scrollView, movieDetails)
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is Failure.ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is MovieFailure.NonExistentMovie -> {
                notify(R.string.failure_movie_non_existent); close()
            }
        }
    }
}
