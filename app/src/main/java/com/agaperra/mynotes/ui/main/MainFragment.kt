package com.agaperra.mynotes.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agaperra.mynotes.R
import com.agaperra.mynotes.adapter.NoteAdapter
import com.agaperra.mynotes.data.Note
import com.agaperra.mynotes.databinding.MainFragmentBinding
import com.agaperra.mynotes.helper.SimpleItemTouchHelperCallback
import com.agaperra.mynotes.interactor.string.StringInteractorImpl
import com.agaperra.mynotes.listener.OnItemClickListener


class MainFragment : Fragment() {

    private val data: String = ""
    private lateinit var notes: List<Note>

    private val noteAdapter by lazy {
        NoteAdapter(object : OnItemClickListener {
            override fun onItemClick(note: Note) {
                val action = MainFragmentDirections.openAddNotesFragment(note.create_date)
                requireView().findNavController().navigate(action)
            }
        }, requireActivity().application, mainViewModel, requireContext())
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        mainViewModel = MainViewModel(
            requireActivity().application, StringInteractorImpl(
                requireContext()
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doInitialization()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun doInitialization() {
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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return when (data.trim()) {
                    "" -> false
                    else -> {

                        true
                    }
                }
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        binding.noteRecycler.apply {
            adapter = noteAdapter
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        mainViewModel.readAllNote.observe(viewLifecycleOwner, {
            notes = it
            noteAdapter.clearItems()
            noteAdapter.addItems(notes)
            noteAdapter.notifyDataSetChanged()
        })

        noteAdapter.setRecycler(binding.noteRecycler)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(
            noteAdapter,
            requireContext()
        )
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.noteRecycler)
        binding.viewModel = mainViewModel
    }

}