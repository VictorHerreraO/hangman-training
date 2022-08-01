package com.wizeline.academy.hangman.feature.game.domain.usecase

import com.wizeline.academy.hangman.feature.game.data.repository.ChallengeRepositoryContract
import com.wizeline.academy.hangman.testutil.TestChallengeData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetRandomChallengeUseCaseTest {

    @Mock
    private lateinit var challengeRepositoryMock: ChallengeRepositoryContract

    private lateinit var subjectUnderTest: GetRandomChallengeUseCase

    private val testScheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
        whenever(challengeRepositoryMock.getRandomChallenge()).thenReturn(
            Single.just(TestChallengeData.challenge_1)
        )

        subjectUnderTest = GetRandomChallengeUseCase(
            challengeRepository = challengeRepositoryMock,
            subscribeScheduler = testScheduler,
            observeScheduler = testScheduler
        )
    }

    @Test
    fun `assert that invoke runs successfully`() {
        val expectedChallenge = TestChallengeData.challenge_1

        subjectUnderTest.invoke(Unit).test().run {
            assertComplete()
            assertNoErrors()
            assertValue { challenge ->
                challenge == expectedChallenge
            }
        }
    }

}