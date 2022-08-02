package com.wizeline.academy.hangman.feature.game.framework.presentation

data class ChallengeCharState(
    val index: Int,
    val char: Char,
    val guessed: Boolean
)
