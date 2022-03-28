package com.agaperra.mynotes.presentation.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.agaperra.mynotes.R
import com.agaperra.mynotes.databinding.MainFragmentBinding
import com.agaperra.mynotes.domain.model.AppState
import com.agaperra.mynotes.domain.model.NoteItem
import com.agaperra.mynotes.presentation.adapters.NoteListAdapter
import com.agaperra.mynotes.presentation.adapters.listeners.OnItemClickListener
import com.agaperra.mynotes.util.helper.SimpleItemTouchHelperCallback
import com.agaperra.mynotes.util.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val noteAdapter by lazy {
        NoteListAdapter(object : OnItemClickListener {
            override fun onItemClick(note: NoteItem) {
                val action = MainFragmentDirections.openAddNotesFragment(note.edit_date)
                requireView().findNavController().navigate(action)
            }
        }, mainViewModel, requireContext())
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doInitialization()
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun doInitialization() {
        mainViewModel.liveData.observe(viewLifecycleOwner) { binding.textView.text = it }

        binding.floating.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.add_nav)
        }
        binding.noteRecycler.adapter = noteAdapter
        startObserve(mainViewModel.readAllNote)

//        binding.searchView.setOnSearchClickListener {
//            binding.textView.visibility =
//                View.GONE
//        }
//        binding.searchView.setOnCloseListener {
//            binding.textView.visibility = View.VISIBLE
//            false
//        }

//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return when (data.trim()) {
//                    "" -> false
//                    else -> {
//
//                        true
//                    }
//                }
//            }

//            override fun onQueryTextChange(newText: String): Boolean {
//                return true
//            }
//        })


        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(
            noteAdapter,
            requireContext()
        )
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.noteRecycler)
    }

    private fun startObserve(contentSource: StateFlow<List<NoteItem>>) {

        contentSource.onEach { result ->
            setResult(result)
        }.launchWhenStarted(lifecycleScope)
    }

    private fun setResult(result: List<NoteItem>) {

        noteAdapter.notes = ArrayList(result)
        noteAdapter.submitList(result)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}