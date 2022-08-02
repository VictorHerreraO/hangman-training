package com.wizeline.academy.hangman.feature.game.framework.ui

import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.wizeline.academy.hangman.databinding.ItemCharBinding
import com.wizeline.academy.hangman.feature.game.framework.presentation.ChallengeCharState

class ChallengeCharViewHolder(
    private val binding: ItemCharBinding,
    private val onTextChanged: (index: Int, text: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun inflate(
            inflater: LayoutInflater,
            onTextChanged: (index: Int, text: String) -> Unit
        ): ChallengeCharViewHolder {
            return ChallengeCharViewHolder(
                binding = ItemCharBinding.inflate(inflater),
                onTextChanged = onTextChanged
            )
        }

        const val SPACE_CHAR = ' '
    }

    private var currentItem: ChallengeCharState? = null

    init {
        binding.edTxtChar.doAfterTextChanged { text ->
            currentItem?.let {
                onTextChanged(it.index, text.toString())
            }
        }
    }

    fun bind(item: ChallengeCharState) = with(binding) {
        currentItem = item
        edTxtChar.visibility = if (item.char == SPACE_CHAR) View.INVISIBLE
        else View.VISIBLE
        if (item.guessed) edTxtChar.apply {
            setText(item.char.toString())
            isEnabled = false
        } else edTxtChar.apply {
            text = null
            isEnabled = true
        }
    }

}
