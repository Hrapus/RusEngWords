package com.example.rusengwords.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rusengwords.R
import com.example.rusengwords.domain.WordUnit
import java.lang.RuntimeException

class WordListAdapter: RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {

    var wordList = listOf<WordUnit>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onWordLongClickListener: OnWordLongClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {

        val layout = when(viewType){
            VIEW_TYPE_ACTUAL -> R.layout.item_shop_enabled
            VIEW_TYPE_NO_ACTUAL -> R.layout.item_shop_disable
            else -> throw RuntimeException(" Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return WordListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }


    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        val word = wordList[position]

        holder.view.setOnLongClickListener{
            onWordLongClickListener?.onWordLongClick(word)
            true
        }
        holder.tvName.text = word.engWord
        holder.tvCount.text = word.rusWord
    }

    override fun onViewRecycled(holder: WordListViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(ContextCompat.getColor(holder.view.context, android.R.color.white))
    }

    override fun getItemViewType(position: Int): Int {
        val word = wordList[position]
        return if (word.actuality){
            VIEW_TYPE_ACTUAL
        } else{
            VIEW_TYPE_NO_ACTUAL
        }

    }

    class WordListViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    interface OnWordLongClickListener{
        fun onWordLongClick(word: WordUnit)
    }

    companion object{
        const val VIEW_TYPE_ACTUAL = 1
        const val VIEW_TYPE_NO_ACTUAL = 0

    }
}