package com.feragusper.imdblite.movies.android.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.feragusper.imdblite.R
import com.feragusper.imdblite.common.android.BaseFragment
import com.feragusper.imdblite.common.exception.Failure
import com.feragusper.imdblite.common.extension.*
import com.feragusper.imdblite.common.navigation.Navigator
import com.feragusper.imdblite.movies.android.MoviesAdapter
import com.feragusper.imdblite.movies.android.viewmodel.MoviesViewModel
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.exception.MovieFailure
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class FavoriteMoviesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private lateinit var favoriteMoviesViewModel: MoviesViewModel

    override fun layoutId() = R.layout.fragment_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        favoriteMoviesViewModel = viewModel(viewModelFactory) {
            observe(favoriteMovies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadMoviesList()
    }


    private fun initializeView() {
        movieList.layoutManager = StaggeredGridLayoutManager(resources.getInteger(R.integer.movie_list_columns), StaggeredGridLayoutManager.VERTICAL)
        movieList.adapter = moviesAdapter
        moviesAdapter.itemClickListener = { movie, navigationExtras ->
            navigator.showMovieDetails(activity!!, movie.id, navigationExtras)
        }
        moviesAdapter.favoriteButtonClickListener = { movie, favorite ->
            favoriteMoviesViewModel.saveFavorite(movie, favorite)
        }
    }

    private fun loadMoviesList() {
        emptyView.invisible()
        movieList.visible()
        showProgress()
        favoriteMoviesViewModel.loadMovies()
    }

    private fun renderMoviesList(movies: List<Movie>?) {
        moviesAdapter.collection = movies.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is MovieFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        movieList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}
