package com.example.rusengwords.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.rusengwords.domain.WordUnit

class WordItemDiffCallback: DiffUtil.ItemCallback<WordUnit>() {
    override fun areItemsTheSame(oldItem: WordUnit, newItem: WordUnit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WordUnit, newItem: WordUnit): Boolean {
        return oldItem == newItem
    }
}