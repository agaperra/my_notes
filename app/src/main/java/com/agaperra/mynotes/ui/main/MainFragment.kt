package com.agaperra.mynotes.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.agaperra.mynotes.ui.add.AddNoteFragment
import com.agaperra.mynotes.R
import com.agaperra.mynotes.databinding.MainFragmentBinding
import com.agaperra.mynotes.interactor.string.StringInteractorImpl


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        mainViewModel = MainViewModel(StringInteractorImpl(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doInitialization()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun doInitialization() {
        mainViewModel.liveData.observe(viewLifecycleOwner, { binding.textView.text = it })

        binding.floating.setOnClickListener {
            val transaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, AddNoteFragment.newInstance())
            transaction?.addToBackStack(null)
            transaction?.commitNow()
        }
        binding.searchView.setOnSearchClickListener {
            binding.textView.visibility =
                View.GONE
        }
        binding.searchView.setOnCloseListener {
            binding.textView.visibility = View.VISIBLE
            false
        }
    }


}