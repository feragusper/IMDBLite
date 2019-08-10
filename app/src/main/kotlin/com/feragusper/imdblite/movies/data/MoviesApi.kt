package com.feragusper.imdblite.movies.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MoviesApi {
    companion object {
        private const val PATH_DISCOVER_MOVIES = "discover/movie"
    }

    @GET(PATH_DISCOVER_MOVIES)
    fun movies(@Query("api_key") apiKey: String): Call<DiscoverMoviesResultEntity>
}
