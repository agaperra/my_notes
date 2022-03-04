package com.agaperra.mynotes.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.agaperra.mynotes.R
import com.agaperra.mynotes.databinding.SplashFragmentBinding
import com.agaperra.mynotes.presentation.interactor.StringInteractorImpl

class SplashFragment : Fragment(){
    private lateinit var binding: SplashFragmentBinding
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false)
        splashViewModel = SplashViewModel(StringInteractorImpl(requireContext()))
        splashViewModel.liveData.observe(viewLifecycleOwner) { binding.startText.text = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({
            view.findNavController().navigate(R.id.action_splashFragment_to_main_nav)
        }, 1000)
        super.onViewCreated(view, savedInstanceState)
    }
}