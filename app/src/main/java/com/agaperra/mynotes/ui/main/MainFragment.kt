package com.agaperra.mynotes.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agaperra.mynotes.R
import com.agaperra.mynotes.adapter.NoteAdapter
import com.agaperra.mynotes.databinding.MainFragmentBinding
import com.agaperra.mynotes.helper.SimpleItemTouchHelperCallback
import com.agaperra.mynotes.interactor.string.StringInteractorImpl
import com.agaperra.mynotes.listener.OnItemClickListener
import com.agaperra.mynotes.response.NoteResponse
import com.agaperra.mynotes.utils.AppState


class MainFragment : Fragment() {

    private val noteAdapter by lazy {
        NoteAdapter(object : OnItemClickListener {
            override fun onItemClick(note: NoteResponse) {
                val action = MainFragmentDirections.openAddNotesFragment(note.date)
                requireView().findNavController().navigate(action)
            }
        })
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        mainViewModel = MainViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doInitialization()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun doInitialization() {
        mainViewModel.setInteractor(StringInteractorImpl(requireContext()))
        mainViewModel.setText()
        mainViewModel.liveData.observe(viewLifecycleOwner, { binding.textView.text = it })

        binding.floating.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.add_nav)
        }
        binding.searchView.setOnSearchClickListener {
            binding.textView.visibility =
                View.GONE
        }
        binding.searchView.setOnCloseListener {
            binding.textView.visibility = View.VISIBLE
            false
        }
        mainViewModel.getAllNotesList()

        binding.noteRecycler.apply {
            adapter = noteAdapter
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        mainViewModel.noteLiveData.observe(viewLifecycleOwner, {
            renderData(it)
            noteAdapter.notifyDataSetChanged()
        })
        noteAdapter.setContext(requireContext())
        noteAdapter.setRecycler(binding.noteRecycler)
        mainViewModel.getAllNotesList()
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(noteAdapter, requireContext())
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.noteRecycler)
        binding.viewModel = mainViewModel
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.noteRecycler.visibility = View.VISIBLE
                noteAdapter.setData(appState.noteData as ArrayList<NoteResponse>)
            }
            is AppState.Loading -> {
                binding.noteRecycler.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.noteRecycler.visibility = View.VISIBLE
                mainViewModel.getAllNotesList()
            }
        }
    }



}