package com.feragusper.imdblite.movies.interactor

import com.feragusper.imdblite.common.interactor.UseCase
import com.feragusper.imdblite.common.interactor.UseCase.None
import com.feragusper.imdblite.movies.data.repository.MoviesRepository
import com.feragusper.imdblite.movies.domain.Movie
import javax.inject.Inject

class SearchMoviesUseCase
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<List<Movie>, SearchMoviesUseCase.Params>() {

    override suspend fun run(params: Params) = moviesRepository.searchMovies(params.query)

    data class Params(val query: String?)
}
