package com.wizeline.academy.hangman.feature.game.data.repository

import com.wizeline.academy.hangman.feature.game.ChallengeModel
import io.reactivex.rxjava3.core.Single

interface ChallengeRepositoryContract {
    fun getRandomChallenge(): Single<ChallengeModel>
}
