package com.wizeline.academy.hangman.feature.game.data.repository.impl

import com.wizeline.academy.hangman.feature.game.ChallengeModel
import com.wizeline.academy.hangman.feature.game.data.datasource.MoviesRemoteDataSourceContract
import com.wizeline.academy.hangman.feature.game.data.repository.ChallengeRepositoryContract
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ChallengeRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSourceContract
) : ChallengeRepositoryContract {

    override fun getRandomChallenge(): Single<ChallengeModel> {
        return moviesRemoteDataSource.getRandomPopularMovies()
            .map { list -> list.random() }
            .map { movie -> ChallengeModel(movie.title!!) }
    }

}
