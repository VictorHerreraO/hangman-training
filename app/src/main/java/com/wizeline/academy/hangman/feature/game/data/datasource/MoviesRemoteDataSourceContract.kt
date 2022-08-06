package com.wizeline.academy.hangman.feature.game.data.datasource

import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieDto
import io.reactivex.rxjava3.core.Single

interface MoviesRemoteDataSourceContract {

    fun getRandomPopularMovies(): Single<List<MovieDto>>

    fun getPopularMovies(page: Int): Single<List<MovieDto>>

}
