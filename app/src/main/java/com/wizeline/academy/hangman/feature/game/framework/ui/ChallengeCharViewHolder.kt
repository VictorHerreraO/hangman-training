package com.wizeline.academy.hangman.feature.game.framework.ui

import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.wizeline.academy.hangman.databinding.ItemCharBinding
import com.wizeline.academy.hangman.feature.game.framework.presentation.ChallengeCharState

class ChallengeCharViewHolder(
    private val binding: ItemCharBinding,
    private val beforeCharGuess: (guess: Char?) -> Boolean,
    private val afterCharGuess: (guess: Char?) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val TAG = "ChallengeCharViewHolder"
        fun inflate(
            inflater: LayoutInflater,
            beforeCharGuess: (guess: Char?) -> Boolean,
            afterCharGuess: (guess: Char?) -> Unit
        ): ChallengeCharViewHolder {
            return ChallengeCharViewHolder(
                binding = ItemCharBinding.inflate(inflater),
                beforeCharGuess = beforeCharGuess,
                afterCharGuess = afterCharGuess
            )
        }

        const val SPACE_CHAR = ' '
    }

    private var currentItem: ChallengeCharState? = null

    private var isBinding = false
    private var isGuessing = false

    init {
        binding.edTxtChar.apply {
            filters = arrayOf(
                InputFilter { text, _, _, _, _, _ ->
                    if (isBinding || isGuessing) return@InputFilter null
                    Log.d(TAG, "filtering input [$text]")
                    val accept = beforeCharGuess(text?.firstOrNull())
                    return@InputFilter if (accept) null else ""
                }
            )
            doAfterTextChanged { text ->
                if (isBinding || isGuessing) return@doAfterTextChanged
                else {
                    Log.d(TAG, "text changed to [${text.toString()}]")
                    isGuessing = true
                    afterCharGuess(text?.firstOrNull())
                    this.text = null
                    isGuessing = false
                }
            }
        }
    }

    fun bind(item: ChallengeCharState) = with(binding) {
        isBinding = true
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
        isBinding = false
    }

}
