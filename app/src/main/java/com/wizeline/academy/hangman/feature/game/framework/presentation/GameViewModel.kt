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

    init {
        _state.update {
            it.copy(isGameLoading = true)
        }
        viewModelScope.launch {
            getRandomChallengeUseCase(Unit).fold(
                { challenge ->
                    Log.d(TAG, "New challenge is: $challenge")
                    challenge.text
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

    fun onCharGuess(index: Int, text: String) {
        Log.d(TAG, "Guess for index $index is $text")
    }

}
