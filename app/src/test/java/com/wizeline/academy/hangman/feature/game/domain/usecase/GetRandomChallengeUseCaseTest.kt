package com.wizeline.academy.hangman.feature.game.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.feature.game.data.repository.ChallengeRepositoryContract
import com.wizeline.academy.hangman.testutil.TestChallengeData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetRandomChallengeUseCaseTest {

    @Mock
    private lateinit var challengeRepositoryMock: ChallengeRepositoryContract

    private lateinit var subjectUnderTest: GetRandomChallengeUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        whenever(challengeRepositoryMock.getRandomChallenge()).thenReturn(
            Single.just(TestChallengeData.challenge_1)
        )

        subjectUnderTest = GetRandomChallengeUseCase(
            challengeRepository = challengeRepositoryMock,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `assert that invoke runs successfully`() = runTest {
        val expectedChallenge = TestChallengeData.challenge_1

        val result = subjectUnderTest.invoke(Unit)

        assertThat(result).isNotNull()
        assertThat(result.isSuccess).isTrue()
        result.getOrThrow().let { challenge ->
            assertThat(challenge).isEqualTo(expectedChallenge)
        }
    }

}