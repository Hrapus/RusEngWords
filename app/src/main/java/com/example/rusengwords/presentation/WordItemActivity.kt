package com.example.rusengwords.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rusengwords.R
import com.example.rusengwords.domain.WordUnit

class WordItemActivity : AppCompatActivity(), WordFragment.OnEditingFinishedListener {


    private var screenMode = MODE_UNKNOWN
    private var wordId = WordUnit.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }

    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> WordFragment.newInstanceEditWord(wordId = wordId)
            MODE_ADD -> WordFragment.newInstanceAddWord()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.word_container, fragment)
            .commit()
    }


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

    override fun onEditingFinished() {
        finish()
    }
}