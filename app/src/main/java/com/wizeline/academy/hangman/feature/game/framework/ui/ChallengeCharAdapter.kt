package com.wizeline.academy.hangman.feature.game.framework.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.wizeline.academy.hangman.feature.game.framework.presentation.ChallengeCharState

class ChallengeCharAdapter(
    private val beforeCharGuess: (guess: Char?) -> Boolean,
    private val afterCharGuess: (guess: Char?) -> Unit
) : ListAdapter<ChallengeCharState, ChallengeCharViewHolder>(ChallengeCharDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeCharViewHolder {
        return ChallengeCharViewHolder.inflate(
            inflater = LayoutInflater.from(parent.context),
            beforeCharGuess = beforeCharGuess,
            afterCharGuess = afterCharGuess
        )
    }

    override fun onBindViewHolder(holder: ChallengeCharViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ChallengeCharDiffCallback : DiffUtil.ItemCallback<ChallengeCharState>() {
    override fun areItemsTheSame(
        oldItem: ChallengeCharState,
        newItem: ChallengeCharState
    ): Boolean = oldItem.index == newItem.index

    override fun areContentsTheSame(
        oldItem: ChallengeCharState,
        newItem: ChallengeCharState
    ): Boolean = oldItem == newItem
}
