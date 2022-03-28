package com.agaperra.mynotes.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.agaperra.mynotes.R
import com.agaperra.mynotes.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.splash_fragment){


    private val splashViewModel: SplashViewModel by activityViewModels()

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        splashViewModel.liveData.observe(viewLifecycleOwner) { binding.startText.text = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({
            val navController = Navigation.findNavController(requireActivity(), R.id.fragment)
            navController.navigate(R.id.action_splashFragment_to_main_nav)
        }, 1000)
        super.onViewCreated(view, savedInstanceState)
    }
}