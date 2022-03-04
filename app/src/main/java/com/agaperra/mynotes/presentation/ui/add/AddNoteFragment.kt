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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.agaperra.mynotes.R
import com.agaperra.mynotes.databinding.AddNoteFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment(){

    private lateinit var binding: AddNoteFragmentBinding
    private lateinit var addViewModel: AddNoteViewModel
    private val args: AddNoteFragmentArgs by navArgs()

    @SuppressLint("SimpleDateFormat")
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_note_fragment, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        addViewModel = AddNoteViewModel(requireActivity().application)
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
            addViewModel.getDetails(args.noteDate).observe(viewLifecycleOwner) {
                it?.let {
                    binding.head.setText(it.title, TextView.BufferType.EDITABLE)
                    binding.editDate.text = it.edit_date
                    binding.edittxtMultilines.setText(
                        it.note,
                        TextView.BufferType.EDITABLE
                    )
                }
            }
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
                        0,
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

    private fun getCount(viewModel: AddNoteViewModel): Int {
        var temp=0
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            temp=viewModel.getCount()
        }
        return temp
    }


    private fun saveNote(
        position: Int,
        title: String,
        create_date: String,
        edit_date: String,
        note: String
    ) {
        addViewModel.saveNoteToDB(position, title, create_date, edit_date, note)

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
            val v = (ctx as Activity).currentFocus ?: return
            inputManager.hideSoftInputFromWindow(v.windowToken, 0)
        } else {
            //показать
            val inputManager = ctx
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            (ctx as Activity).currentFocus ?: return
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }


}