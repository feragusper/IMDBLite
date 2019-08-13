package com.feragusper.imdblite.movies.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MoviesApi {
    companion object {
        private const val PATH_DISCOVER_MOVIES = "discover/movie"
        private const val PATH_SEARCH_MOVIES = "search/movie"
    }

    @GET(PATH_DISCOVER_MOVIES)
    fun moviesByYear(
        @Query("api_key") apiKey: String,
        @Query("primary_release_year") primaryReleaseYear: String): Call<ListOfMoviesResultEntity>

    @GET(PATH_SEARCH_MOVIES)
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String?): Call<ListOfMoviesResultEntity>
}
