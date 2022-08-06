package com.wizeline.academy.hangman.feature.game.data.datasource.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesClient {

    @GET(ImdbApi.Endpoint.POPULAR_MOVIES)
    fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null
    ): Single<MovieListDto>

}
