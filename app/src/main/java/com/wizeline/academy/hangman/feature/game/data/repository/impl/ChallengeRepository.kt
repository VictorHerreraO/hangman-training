package com.wizeline.academy.hangman.feature.game.data.repository.impl

import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import com.wizeline.academy.hangman.feature.game.data.datasource.MoviesRemoteDataSourceContract
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieDto
import com.wizeline.academy.hangman.feature.game.data.repository.ChallengeRepositoryContract
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ChallengeRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSourceContract,
    private val movieDtoToChallengeModelMapper: Mapper<MovieDto, ChallengeModel>
) : ChallengeRepositoryContract {

    override fun getRandomChallenge(): Single<ChallengeModel> {
        return moviesRemoteDataSource.getRandomPopularMovies()
            .map { list -> list.random() }
            .map(movieDtoToChallengeModelMapper::map)
    }

}
