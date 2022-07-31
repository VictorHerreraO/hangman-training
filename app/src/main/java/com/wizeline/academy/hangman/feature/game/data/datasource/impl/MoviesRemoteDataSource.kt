package com.wizeline.academy.hangman.feature.game.data.datasource.impl

import com.wizeline.academy.hangman.feature.game.data.datasource.MoviesRemoteDataSourceContract
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieDto
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MoviesClient
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val client: MoviesClient
) : MoviesRemoteDataSourceContract {

    private val pageRange = 1..500

    override fun getRandomPopularMovies(): Single<List<MovieDto>> {
        return getPopularMovies(page = pageRange.random())
    }

    override fun getPopularMovies(page: Int): Single<List<MovieDto>> {
        return client.getPopularMovies(
            page = page
        ).map { it.results!! }
    }

}
