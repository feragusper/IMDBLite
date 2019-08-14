package com.feragusper.imdblite.movies.interactor

import com.feragusper.imdblite.common.interactor.UseCase
import com.feragusper.imdblite.movies.data.repository.MoviesRepository
import com.feragusper.imdblite.movies.domain.Movie
import javax.inject.Inject

class SaveFavoriteMovieUseCase
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<Unit, SaveFavoriteMovieUseCase.Params>() {

    override suspend fun run(params: Params) = moviesRepository.saveFavorite(params.movie, params.favorite)

    data class Params(val movie: Movie, val favorite: Boolean)
}
