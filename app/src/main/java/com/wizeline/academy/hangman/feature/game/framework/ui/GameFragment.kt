package com.wizeline.academy.hangman.feature.game.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.wizeline.academy.hangman.databinding.FragmentGameBinding
import com.wizeline.academy.hangman.feature.game.framework.presentation.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()

    private var challengeCharAdapter: ChallengeCharAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        rvItems.run {
            val gridLayoutManager = GridLayoutManager(requireContext(), 7)
            this.layoutManager = gridLayoutManager
            challengeCharAdapter = ChallengeCharAdapter(
                beforeCharGuess = viewModel::beforeCharGuess,
                afterCharGuess = viewModel::afterCharGuess
            )
            this.adapter = challengeCharAdapter
        }
        subscribeUi()
    }

    private fun subscribeUi(): Unit = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    // Timer
                    // Round count
                    // HangMan state
                    // Chars guess
                    challengeCharAdapter?.submitList(state.challengeCharList)
                }
            }
        }
    }

}
