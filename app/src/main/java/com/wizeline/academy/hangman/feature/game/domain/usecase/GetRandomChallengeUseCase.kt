package com.wizeline.academy.hangman.feature.game.domain.usecase

import com.wizeline.academy.hangman.core.arch.IoDispatcher
import com.wizeline.academy.hangman.core.arch.UseCase
import com.wizeline.academy.hangman.feature.game.data.repository.ChallengeRepositoryContract
import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.rx3.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRandomChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepositoryContract,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Result<ChallengeModel>>() {

    private val regex = Regex("[^A-Z0-9 ]")

    override suspend fun invoke(params: Unit): Result<ChallengeModel> = withContext(dispatcher) {
        return@withContext runCatching {
            challengeRepository.getRandomChallenge().map {
                it.copy(
                    text = regex.replace(it.text.uppercase(), "")
                )
            }.await()
        }
    }

}
