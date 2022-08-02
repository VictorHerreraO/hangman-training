package com.wizeline.academy.hangman.feature.game.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.wizeline.academy.hangman.databinding.FragmentGameBinding
import com.wizeline.academy.hangman.feature.game.framework.presentation.ChallengeCharState
import com.wizeline.academy.hangman.feature.game.framework.presentation.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()

    private var adapter: ChallengeCharAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        val layoutManager = GridLayoutManager(requireContext(), 7)
        rvItems.layoutManager = layoutManager
        adapter = ChallengeCharAdapter()
        rvItems.adapter = adapter
        val list: List<ChallengeCharState> = "Hola mundo!".uppercase().mapIndexed { index, char ->
            ChallengeCharState(
                index = index,
                char = char,
                guessed = true
            )
        }.map {
            it.copy(guessed = it.char != 'O')
        }
        adapter!!.submitList(list)
    }

}
