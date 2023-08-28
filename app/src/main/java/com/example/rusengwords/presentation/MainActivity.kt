package com.example.rusengwords.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.rusengwords.R
import com.example.rusengwords.presentation.WordItemActivity.Companion.newIntentAddWord
import com.example.rusengwords.presentation.WordItemActivity.Companion.newIntentEditWord
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), WordFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var wordListAdapter: WordListAdapter
    private var wordContainer: FragmentContainerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordContainer = findViewById(R.id.word_container_main)

        setupRecycleView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.wordList.observe(this) {
            wordListAdapter.submitList(it)
        }

        val buttonAddWord = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddWord.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = newIntentAddWord(this)
                startActivity(intent)
            } else {
                launchFragment(WordFragment.newInstanceAddWord())
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
    private fun isOnePaneMode(): Boolean {
        return wordContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.word_container_main, fragment)
            .addToBackStack(null)
            .commit()
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
            if (isOnePaneMode()) {
                val intent = newIntentEditWord(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(WordFragment.newInstanceEditWord(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        wordListAdapter.onWordLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

}
