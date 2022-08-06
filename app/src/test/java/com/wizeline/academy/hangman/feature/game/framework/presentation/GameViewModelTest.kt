package com.wizeline.academy.hangman.feature.game.framework.presentation

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.core.arch.UseCase
import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import com.wizeline.academy.hangman.testutil.MainDispatcherRule
import com.wizeline.academy.hangman.testutil.TestChallengeData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.stub

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GameViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getRandomChallengeUseCaseMock: UseCase<Unit, Result<ChallengeModel>>

    private lateinit var subjectUnderTest: GameViewModel

    private lateinit var initialStateHandle: SavedStateHandle

    private val expectedUserId = 1

    @Before
    fun setUp() {
        getRandomChallengeUseCaseMock.stub {
            onBlocking { invoke(any()) } doAnswer { Result.success(TestChallengeData.challenge_1) }
        }

        initialStateHandle = SavedStateHandle(
            initialState = mapOf(GameViewModel.KEY_USER_ID to expectedUserId)
        )

        subjectUnderTest = GameViewModel(
            savedStateHandle = initialStateHandle,
            getRandomChallengeUseCase = getRandomChallengeUseCaseMock
        )
    }

    @Test
    fun `assert that init gets challenge and updates state `() = runTest {
        val expectedList = TestChallengeData.challenge_1_char_list_state
        val state = subjectUnderTest.state.first()

        assertThat(state.isGameLoading).isFalse()
        assertThat(state.challengeCharList).isNotEmpty()
        assertThat(state.challengeCharList).isEqualTo(expectedList)
    }

    @Test
    fun `assert that beforeCharGuess accepts null`() {
        val accepted = subjectUnderTest.beforeCharGuess(null)

        assertThat(accepted).isTrue()
    }

    @Test
    fun `assert that beforeCharGuess accepts non guessed char`() {
        val expectedChar = 'a'

        val accepted = subjectUnderTest.beforeCharGuess(expectedChar)

        assertThat(accepted).isTrue()
    }

    @Test
    fun `assert that beforeCharGuess rejects guessed char`() {
        val expectedChar = 'a'
        subjectUnderTest.afterCharGuess(expectedChar)

        val accepted = subjectUnderTest.beforeCharGuess(expectedChar)

        assertThat(accepted).isFalse()
    }

    @Test
    fun `assert that afterCharGuess does not modifies state`() {
        val expectedChar = null
        val firstState = subjectUnderTest.state.value

        subjectUnderTest.afterCharGuess(expectedChar)

        val lastState = subjectUnderTest.state.value

        assertThat(lastState).isEqualTo(firstState)
    }

    @Test
    fun `assert that afterCharGuess does not modifies state when guess retry`() {
        val expectedChar = 'a'
        // First try
        subjectUnderTest.afterCharGuess(expectedChar)
        val firstState = subjectUnderTest.state.value

        // Retry
        subjectUnderTest.afterCharGuess(expectedChar)

        val lastState = subjectUnderTest.state.value
        assertThat(lastState).isEqualTo(firstState)
    }

    @Test
    fun `assert that afterCharGuess state updates list when guess is correct`() {
        val expectedChallenge = TestChallengeData.challenge_1
        val expectedChar = expectedChallenge.text.first()
        val firstState = subjectUnderTest.state.value

        subjectUnderTest.afterCharGuess(expectedChar)

        val finalState = subjectUnderTest.state.value
        assertThat(finalState).isNotEqualTo(firstState)
        finalState.run {
            challengeCharList.filter {
                it.char == expectedChar
            }.forEach {
                assertThat(it.guessed).isTrue()
            }
        }
    }

    @Test
    fun `assert that afterCharGuess state updates error count when guess is incorrect`() {
        val expectedChallenge = TestChallengeData.challenge_1
        val expectedChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ!?,."
            .toCharArray()
            .toList()
            .first {
                !expectedChallenge.text.contains(it, ignoreCase = true)
            }
        val firstState = subjectUnderTest.state.value

        subjectUnderTest.afterCharGuess(expectedChar)

        val finalState = subjectUnderTest.state.value
        assertThat(finalState).isNotEqualTo(firstState)
        finalState.run {
            assertThat(guessMistakes).isEqualTo(firstState.guessMistakes.inc())
        }
    }

}