package com.feragusper.imdblite.movies.interactor

import com.feragusper.imdblite.common.interactor.UseCase
import com.feragusper.imdblite.common.interactor.UseCase.None
import com.feragusper.imdblite.movies.data.MoviesRepository
import com.feragusper.imdblite.movies.domain.Movie
import javax.inject.Inject

class GetMoviesUseCase
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<List<Movie>, None>() {

    override suspend fun run(params: None) = moviesRepository.movies()
}
