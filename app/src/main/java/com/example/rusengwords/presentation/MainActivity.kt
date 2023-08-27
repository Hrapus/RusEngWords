package com.example.rusengwords.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.rusengwords.R
import com.example.rusengwords.presentation.WordItemActivity.Companion.newIntentAddWord
import com.example.rusengwords.presentation.WordItemActivity.Companion.newIntentEditWord
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var wordListAdapter: WordListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecycleView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.wordList.observe(this) {
            wordListAdapter.submitList(it)
        }

        val buttonAddWord = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddWord.setOnClickListener {
            val intent = newIntentAddWord(this)
            startActivity(intent)
        }
    }

    private fun setupRecycleView() {
        val rvWordList = findViewById<RecyclerView>(R.id.rv_shop_list)
        wordListAdapter = WordListAdapter()
        rvWordList.adapter = wordListAdapter

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvWordList)
    }

    private fun setupSwipeListener(rvWordList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = wordListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteWord(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvWordList)
    }

    private fun setupClickListener() {
        wordListAdapter.onWordClickListener = {
            Log.d("Log", it.toString())
            val intent = newIntentEditWord(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        wordListAdapter.onWordLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

}
