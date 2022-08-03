package com.wizeline.academy.hangman.feature.game.domain.usecase

import com.wizeline.academy.hangman.core.arch.UseCase
import com.wizeline.academy.hangman.feature.game.data.repository.ChallengeRepositoryContract
import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import kotlinx.coroutines.rx3.await
import javax.inject.Inject

class GetRandomChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepositoryContract
) : UseCase<Unit, Result<ChallengeModel>>() {

    override suspend fun invoke(params: Unit): Result<ChallengeModel> {
        return runCatching {
            challengeRepository.getRandomChallenge().await()
        }
    }

}
