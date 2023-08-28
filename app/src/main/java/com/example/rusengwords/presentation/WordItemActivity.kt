package com.example.rusengwords.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.rusengwords.R
import com.example.rusengwords.domain.WordUnit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class WordItemActivity : AppCompatActivity() {

    //    private lateinit var viewModel: WordItemViewModel
//
//    private lateinit var tilEng: TextInputLayout
//    private lateinit var tilRus: TextInputLayout
//    private lateinit var engWord: EditText
//    private lateinit var rusWord: EditText
//    private lateinit var buttonSave: Button
//
    private var screenMode = MODE_UNKNOWN
    private var wordId = WordUnit.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_item)
        parseIntent()
//        viewModel = ViewModelProvider(this)[WordItemViewModel::class.java]
//        initViews()
//        addTextChangeListener()
        launchRightMode()
//        observeViewModel()
    }

    //
//    private fun observeViewModel() {
//        viewModel.errorInputWord.observe(this) {
//            val message = if (!it) {
//                "Ошибка"
//            } else {
//                null
//            }
//            tilEng.error = message
//        }
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> WordFragment.newInstanceEditWord(wordId = wordId)
            MODE_ADD -> WordFragment.newInstanceAddWord()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.word_container, fragment)
            .commit()
    }

    //
//    private fun launchEditMode() {
//        viewModel.getWord(wordId)
//        viewModel.word.observe(this) {
//            engWord.setText(it.engWord)
//            rusWord.setText(it.rusWord)
//        }
//        buttonSave.setOnClickListener{
//            viewModel.editWord(engWord.text?.toString(), rusWord.text?.toString())
//        }
//    }
//
//    private fun launchAddMode() {
//        buttonSave.setOnClickListener {
//            viewModel.addWord(engWord.text?.toString(), rusWord.text?.toString())
//        }
//    }
//
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("No screen mode params")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_WORD_ID)) {
                throw RuntimeException("No word ID")
            }
            wordId = intent.getIntExtra(EXTRA_WORD_ID, WordUnit.UNDEFINED_ID)
        }
    }
//
//    private fun initViews() {
//        tilEng = findViewById(R.id.til_eng_word)
//        tilRus = findViewById(R.id.til_rus_word)
//        engWord = findViewById(R.id.eng_word)
//        rusWord = findViewById(R.id.rus_word)
//        buttonSave = findViewById(R.id.save_button)
//    }
//
//    private fun addTextChangeListener() {
//
////        rusWord.addTextChangedListener(object : TextWatcher {
////            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
////
////            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
////                viewModel.resetError()
////            }
////
////            override fun afterTextChanged(s: Editable?) {}
////        })
//        engWord.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetError()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_WORD_ID = "extra_word_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddWord(context: Context): Intent {
            val intent = Intent(context, WordItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditWord(context: Context, wordId: Int): Intent {
            val intent = Intent(context, WordItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_WORD_ID, wordId)
            return intent
        }

    }
}