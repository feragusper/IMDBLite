package com.feragusper.imdblite.movies.interactor

import com.feragusper.imdblite.movies.extension.MovieId
import com.feragusper.imdblite.common.interactor.UseCase
import com.feragusper.imdblite.movies.data.repository.MoviesRepository
import com.feragusper.imdblite.movies.domain.Movie
import javax.inject.Inject

class GetMovieDetailsUseCase
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<Movie, GetMovieDetailsUseCase.Params>() {

    override suspend fun run(params: Params) = moviesRepository.movieDetails(params.id)

    data class Params(val id: MovieId)
}
