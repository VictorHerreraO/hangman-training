package com.wizeline.academy.hangman.testutil

import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import com.wizeline.academy.hangman.feature.game.framework.presentation.ChallengeCharState

object TestChallengeData {

    val challenge_1 = ChallengeModel(
        text = "A CHALLENGE"
    )

    val challenge_1_char_list = challenge_1.text.toCharArray().toList()

    val challenge_1_char_list_state = challenge_1_char_list.mapIndexed(::ChallengeCharState)

}
