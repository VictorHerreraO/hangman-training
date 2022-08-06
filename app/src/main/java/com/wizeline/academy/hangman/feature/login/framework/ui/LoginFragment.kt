package com.wizeline.academy.hangman.feature.login.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.wizeline.academy.hangman.databinding.FragmentLoginBinding
import com.wizeline.academy.hangman.feature.login.framework.presentation.LoginNavigation
import com.wizeline.academy.hangman.feature.login.framework.presentation.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        edTxtName.doAfterTextChanged {
            viewModel.onUserNameChanged(it.toString())
        }
        edTxtName.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_GO -> {
                    viewModel.login()
                    true
                }
                else -> false
            }
        }
        btnStart.setOnClickListener {
            viewModel.login()
        }
        subscribeUi()
    }

    private fun subscribeUi() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigateTo.collect { navigationEvent ->
                        navigationEvent.getContentIfNotHandled()?.let {
                            val directions = when (it) {
                                is LoginNavigation.NavigateHome -> LoginFragmentDirections.actionNavLoginToHome(
                                    it.userId
                                )
                            }
                            findNavController().navigate(directions)
                        }
                    }
                }
                launch {
                    viewModel.loginState.collect { state ->
                        btnStart.isEnabled = state.loginEnabled && !state.loginLoading
                    }
                }
            }
        }
    }

}
