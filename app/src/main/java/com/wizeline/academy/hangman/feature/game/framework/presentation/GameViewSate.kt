package com.wizeline.academy.hangman.feature.game.framework.presentation

import com.wizeline.academy.hangman.core.util.Event

data class GameViewSate(
    val remainingTime: String = "00:00",
    val currentRound: String = "1/7",
    val challengeCharList: List<ChallengeCharState> = emptyList(),
    val guessMistakes: Int = 0,
    val isGameLoading: Boolean = true,
    val isGameOver: Boolean = false,
    val error: Event<String?> = Event(null)
)
