package com.example.rusengwords.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.rusengwords.R
import com.example.rusengwords.domain.WordUnit

class WordListAdapter: ListAdapter<WordUnit, WordListViewHolder>(WordItemDiffCallback()) {


    var onWordLongClickListener: ((WordUnit) -> Unit)? = null
    var onWordClickListener: ((WordUnit) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {

        val layout = when(viewType){
            VIEW_TYPE_ACTUAL -> R.layout.item_shop_enabled
            VIEW_TYPE_NO_ACTUAL -> R.layout.item_shop_disable
            else -> throw RuntimeException(" Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return WordListViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        val word = getItem(position)

        holder.view.setOnLongClickListener{
            onWordLongClickListener?.invoke(word)
            true
        }

        holder.view.setOnClickListener{
            onWordClickListener?.invoke(word)
        }

        holder.tvName.text = word.engWord
        holder.tvCount.text = word.rusWord
    }

    override fun getItemViewType(position: Int): Int {
        val word = getItem(position)
        return if (word.actuality){
            VIEW_TYPE_ACTUAL
        } else{
            VIEW_TYPE_NO_ACTUAL
        }

    }

    companion object{
        const val VIEW_TYPE_ACTUAL = 1
        const val VIEW_TYPE_NO_ACTUAL = 0

    }
}