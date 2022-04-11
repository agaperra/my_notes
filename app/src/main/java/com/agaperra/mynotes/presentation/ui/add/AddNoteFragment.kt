package com.agaperra.mynotes.presentation.ui.add

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.agaperra.mynotes.R
import com.agaperra.mynotes.databinding.AddNoteFragmentBinding
import com.agaperra.mynotes.domain.model.AppState
import com.agaperra.mynotes.domain.model.NoteItem
import com.agaperra.mynotes.util.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.add_note_fragment) {

    private var _binding: AddNoteFragmentBinding? = null
    private val binding get() = _binding!!
    private val addViewModel: AddNoteViewModel by activityViewModels()
    private val args: AddNoteFragmentArgs by navArgs()

    @SuppressLint("SimpleDateFormat")
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddNoteFragmentBinding.inflate(inflater, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doInitialization()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun doInitialization() {

        binding.head.apply {
            isFocusableInTouchMode
            isFocusable = true
            requestFocus()
            highlightColor = ContextCompat.getColor(context, R.color.silver_grey)
            hideKeyboard(context, false)
        }

        if (args.noteDate != "") {
            addViewModel.getDetails(args.noteDate)
            startObserve(addViewModel.detailsNote)
        }



        binding.floatingDone.setOnClickListener { view: View ->
            hideKeyboard(requireContext(), true)
            if (binding.head.text.trim() == "") {
                view.findNavController().navigate(R.id.main_nav)
            } else {
                if (args.noteDate != "") {

                    updateNote(
                        binding.head.text.trim().toString(),
                        args.noteDate,
                        simpleDateFormat.format(Date()),
                        binding.edittxtMultilines.text.toString()

                    )
                } else {
                    saveNote(
                        binding.head.text.trim().toString(),
                        simpleDateFormat.format(Date()),
                        simpleDateFormat.format(Date()),
                        binding.edittxtMultilines.text.toString()
                    )

                }
                Toast.makeText(
                    context,
                    context?.resources?.getString(R.string.note_added),
                    Toast.LENGTH_SHORT
                ).show()
                view.findNavController().navigate(R.id.main_nav)
            }
        }

    }

    private fun startObserve(contentSource: StateFlow<AppState<NoteItem>>) {

        contentSource.onEach { result ->
            setResult(result)
        }.launchWhenStarted(lifecycleScope)
    }

    private fun setResult(result: AppState<NoteItem>) {
        when (result) {
            is AppState.Success -> {
                binding.head.setText(result.data.title, TextView.BufferType.EDITABLE)
                binding.editDate.text = result.data.edit_date
                binding.edittxtMultilines.setText(
                    result.data.note,
                    TextView.BufferType.EDITABLE
                )
            }
            is AppState.Error -> {}
            is AppState.Loading -> {}
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun saveNote(
        title: String,
        create_date: String,
        edit_date: String,
        note: String
    ) {
        addViewModel.saveNoteToDB(title, create_date, edit_date, note)

    }

    private fun updateNote(
        title: String,
        create_date: String,
        edit_date: String,
        note: String
    ) {
        addViewModel.updateNote(title, create_date, edit_date, note)
    }

//    private fun updatePosition(position: Int, position_other: Int, viewModel: AddNoteViewModel){
//        viewModel.viewModelScope.launch(Dispatchers.IO) {
//            addViewModel.updatePosition(position, position_other)
//        }
//    }

    private fun hideKeyboard(ctx: Context, flag: Boolean) {
        if (flag) {
            //скрыть
            val inputManager = ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val v = requireActivity().currentFocus ?: return
            inputManager.hideSoftInputFromWindow(v.windowToken, 0)
        } else {
            //показать
            val inputManager = ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            requireActivity().currentFocus ?: return
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }


}