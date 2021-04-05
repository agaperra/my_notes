package com.agaperra.mynotes.ui.add

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.agaperra.mynotes.R
import com.agaperra.mynotes.databinding.AddNoteFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {

    private lateinit var binding: AddNoteFragmentBinding
    private lateinit var addViewModel: AddNoteViewModel
    private val args: AddNoteFragmentArgs by navArgs()

    @SuppressLint("SimpleDateFormat")
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_note_fragment, container, false)
        addViewModel = AddNoteViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doInitialization()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun doInitialization() {

        if (args.noteDate != "") {
            addViewModel.getDetails(args.noteDate)

            binding.head.setText(addViewModel.noteDetails.title, TextView.BufferType.EDITABLE)
            binding.edittxtMultilines.setText(
                addViewModel.noteDetails.note,
                TextView.BufferType.EDITABLE
            )
        }

        binding.floatingDone.setOnClickListener { view: View ->
            hideKeyboard(requireContext())
            if (binding.head.text.trim() == "") {
                view.findNavController().navigate(R.id.main_nav)
            } else {
                if (args.noteDate != "") {
                    dropNoteFromDB(args.noteDate)
                }
                saveNote(
                    binding.head.text.trim().toString(),
                    simpleDateFormat.format(Date()),
                    binding.edittxtMultilines.text.toString()

                )
                Toast.makeText(context, context?.resources?.getString(R.string.note_added), Toast.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.main_nav)
            }
        }
    }


    private fun saveNote(title: String, date: String, note: String) {
        addViewModel.saveNoteToDB(title, date, note)

    }

    private fun dropNoteFromDB(date: String) {
        addViewModel.dropNote(date)
    }

    private fun hideKeyboard(ctx: Context) {
        val inputManager = ctx
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        // check if no view has focus:
        val v = (ctx as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

}