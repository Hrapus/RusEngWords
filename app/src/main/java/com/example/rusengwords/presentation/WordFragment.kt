package com.example.rusengwords.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rusengwords.R
import com.example.rusengwords.domain.WordUnit
import com.google.android.material.textfield.TextInputLayout

class WordFragment : Fragment() {

    private lateinit var viewModel: WordItemViewModel

    private lateinit var tilEng: TextInputLayout
    private lateinit var tilRus: TextInputLayout
    private lateinit var engWord: EditText
    private lateinit var rusWord: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var wordId: Int = WordUnit.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WordItemViewModel::class.java]
        initViews(view)
        addTextChangeListener()
        launchRightMode()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.errorInputWord.observe(viewLifecycleOwner) {
            val message = if (!it) {
                "Ошибка"
            } else {
                null
            }
            tilEng.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getWord(wordId)
        viewModel.word.observe(viewLifecycleOwner) {
            engWord.setText(it.engWord)
            rusWord.setText(it.rusWord)
        }
        buttonSave.setOnClickListener {
            viewModel.editWord(engWord.text?.toString(), rusWord.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addWord(engWord.text?.toString(), rusWord.text?.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("No screen mode params")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(WORD_ID)) {
                throw RuntimeException("No word ID")
            }
            wordId = args.getInt(WORD_ID, WordUnit.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilEng = view.findViewById(R.id.til_eng_word)
        tilRus = view.findViewById(R.id.til_rus_word)
        engWord = view.findViewById(R.id.eng_word)
        rusWord = view.findViewById(R.id.rus_word)
        buttonSave = view.findViewById(R.id.save_button)
    }

    private fun addTextChangeListener() {

//        rusWord.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetError()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
        engWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetError()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val WORD_ID = "extra_word_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddWord(): WordFragment {
            return WordFragment().apply {
                arguments =  Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }
        fun newInstanceEditWord(wordId: Int): WordFragment {
            return WordFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(WORD_ID, wordId)
                }
            }
        }
    }
}
