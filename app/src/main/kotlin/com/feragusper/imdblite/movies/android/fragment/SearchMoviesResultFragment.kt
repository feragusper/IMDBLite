package com.feragusper.imdblite.movies.android.fragment

import android.app.SearchManager
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
import com.feragusper.imdblite.movies.android.viewmodel.SearchMoviesViewModel
import com.feragusper.imdblite.movies.domain.Movie
import com.feragusper.imdblite.movies.exception.MovieFailure
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class SearchMoviesResultFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private lateinit var searchMoviesViewModel: SearchMoviesViewModel

    override fun layoutId() = R.layout.fragment_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        searchMoviesViewModel = viewModel(viewModelFactory) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadSearchMoviesResult()
    }


    private fun initializeView() {
        movieList.layoutManager = StaggeredGridLayoutManager(resources.getInteger(R.integer.movie_list_columns), StaggeredGridLayoutManager.VERTICAL)
        movieList.adapter = moviesAdapter
        moviesAdapter.clickListener = { movie, navigationExtras ->
            navigator.showMovieDetails(activity!!, movie.id, navigationExtras)
        }
    }

    private fun loadSearchMoviesResult() {
        emptyView.invisible()
        movieList.visible()
        showProgress()
        searchMoviesViewModel.searchMovies(activity?.intent?.getStringExtra(SearchManager.QUERY))
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
        notifyWithAction(message, R.string.action_refresh, ::loadSearchMoviesResult)
    }
}
