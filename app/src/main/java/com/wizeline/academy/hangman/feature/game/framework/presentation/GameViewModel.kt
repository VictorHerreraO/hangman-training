package com.wizeline.academy.hangman.feature.game.framework.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val TAG = "GameViewModel"
        const val KEY_USER_ID = "user-id"
    }

    private val userId: Int get() = savedStateHandle[KEY_USER_ID]!!

}
