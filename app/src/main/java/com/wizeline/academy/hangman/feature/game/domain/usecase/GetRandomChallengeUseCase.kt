package com.wizeline.academy.hangman.feature.game.domain.usecase

import com.soyvictorherrera.bdates.core.arch.BlockingUseCase
import com.wizeline.academy.hangman.core.arch.IoScheduler
import com.wizeline.academy.hangman.core.arch.MainThreadScheduler
import com.wizeline.academy.hangman.feature.game.data.repository.ChallengeRepositoryContract
import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetRandomChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepositoryContract,
    @IoScheduler private val subscribeScheduler: Scheduler,
    @MainThreadScheduler private val observeScheduler: Scheduler
) : BlockingUseCase<Unit, Single<ChallengeModel>>() {

    override fun invoke(params: Unit): Single<ChallengeModel> {
        return challengeRepository.getRandomChallenge()
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }

}
