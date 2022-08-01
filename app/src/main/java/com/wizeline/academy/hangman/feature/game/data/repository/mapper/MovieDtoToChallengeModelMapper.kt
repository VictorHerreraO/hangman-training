package com.wizeline.academy.hangman.feature.game.data.repository.mapper

import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieDto
import javax.inject.Inject

class MovieDtoToChallengeModelMapper @Inject constructor(

) : Mapper<MovieDto, ChallengeModel>() {
    override fun map(value: MovieDto): ChallengeModel = with(value) {
        return ChallengeModel(
            text = title!!
        )
    }
}
