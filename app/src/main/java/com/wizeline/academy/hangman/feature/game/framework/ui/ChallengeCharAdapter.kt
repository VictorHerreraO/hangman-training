package com.wizeline.academy.hangman.feature.game.framework.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.wizeline.academy.hangman.feature.game.framework.presentation.ChallengeCharState

class ChallengeCharAdapter(

) : ListAdapter<ChallengeCharState, ChallengeCharViewHolder>(ChallengeCharDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeCharViewHolder {
        return ChallengeCharViewHolder.inflate(
            inflater = LayoutInflater.from(parent.context),
            onTextChanged = { index, text ->
                Log.d("ChallengeCharAdapter", "item at $index is now: $text")
            }
        )
    }

    override fun onBindViewHolder(holder: ChallengeCharViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ChallengeCharDiffCallback : DiffUtil.ItemCallback<ChallengeCharState>() {
    override fun areItemsTheSame(oldItem: ChallengeCharState, newItem: ChallengeCharState): Boolean {
        return oldItem.index == newItem.index
    }

    override fun areContentsTheSame(oldItem: ChallengeCharState, newItem: ChallengeCharState): Boolean {
        return oldItem == newItem
    }
}
