package com.wizeline.academy.hangman.feature.game.framework.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.academy.hangman.core.arch.UseCase
import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getRandomChallengeUseCase: UseCase<Unit, Result<ChallengeModel>>
) : ViewModel() {

    companion object {
        const val TAG = "GameViewModel"
        const val KEY_USER_ID = "user-id"
    }

    private val _state = MutableStateFlow(GameViewSate())
    val state = _state.asStateFlow()

    private val userId: Int get() = savedStateHandle[KEY_USER_ID]!!

    private val guesses = mutableListOf<Char>()

    private var errorCount = 0

    init {
        _state.update {
            it.copy(isGameLoading = true)
        }
        viewModelScope.launch {
            getRandomChallengeUseCase(Unit).fold(
                { challenge ->
                    Log.d(TAG, "New challenge is: $challenge")
                    challenge.text
                        .uppercase()
                        .toCharArray()
                        .mapIndexed(::ChallengeCharState)
                        .let { list ->
                            _state.update {
                                it.copy(
                                    challengeCharList = list,
                                    isGameLoading = false
                                )
                            }
                        }
                },
                { error ->
                    Log.e(TAG, "Error: ${error.message}", error)
                    _state.update { it.copy(isGameLoading = false) }
                }
            )
        }
    }

    fun beforeCharGuess(guess: Char?): Boolean {
        Log.d(TAG, "user is guessing [$guess]")
        return (if (guess == null) true
        else !guesses.contains(guess.uppercaseChar()))
    }

    fun afterCharGuess(guess: Char?) {
        Log.d(TAG, "user guessed [$guess]")
        if (guess == null) return
        val uppercase = guess.uppercaseChar()
        if (guesses.contains(uppercase)) {
            Log.d(TAG, "already guessed")
            return
        } else guesses.add(uppercase)
        _state.value.run {
            val occurrence = challengeCharList.firstOrNull { it.char == uppercase }
            if (occurrence != null) {
                challengeCharList.map {
                    if (uppercase == it.char) it.copy(guessed = true)
                    else it
                }.let { list ->
                    _state.update { it.copy(challengeCharList = list) }
                    Log.d(TAG, "state updated")
                }
            } else {
                errorCount++
                Log.w(TAG, "invalid guess, error count = $errorCount")
            }

        }
    }

}
