package com.example.rusengwords.domain

import androidx.lifecycle.LiveData

interface WordListRepository {
    fun addWord(word: WordUnit)
    fun deleteWord(word: WordUnit)
    fun editWord(word: WordUnit)
    fun getWord(id: Int): WordUnit
    fun getWordList(): List<WordUnit>
}