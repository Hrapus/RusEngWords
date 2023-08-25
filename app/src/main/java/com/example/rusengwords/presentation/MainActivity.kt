package com.example.rusengwords.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rusengwords.R
import com.example.rusengwords.domain.WordUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var wordListAdapter: WordListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecycleView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.wordList.observe(this){
            wordListAdapter.wordList = it
        }
    }

    private fun setupRecycleView(){
        val rvWordList = findViewById<RecyclerView>(R.id.rv_shop_list)
        wordListAdapter = WordListAdapter()
        rvWordList.adapter = wordListAdapter

        wordListAdapter.onWordLongClickListener = object : WordListAdapter.OnWordLongClickListener {
            override fun onWordLongClick(word: WordUnit) {
                TODO("Not yet implemented")
            }

        }
    }
}