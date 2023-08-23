package com.example.rusengwords.data

import androidx.lifecycle.LiveData
import com.example.rusengwords.domain.WordListRepository
import com.example.rusengwords.domain.WordUnit
import java.lang.RuntimeException

object WordListRepositoryImpl: WordListRepository {

    private val wordList = mutableListOf<WordUnit>()
    private var autoIncrementId = 0

    init {
        for(i in 0..10){
            addWord(WordUnit("Русский №$i", "Endlish #$i", true))
        }
    }

    override fun addWord(word: WordUnit) {
        if (word.id == WordUnit.UNDEFINED_ID){
            word.id = autoIncrementId++
        }
        wordList.add(word)
    }

    override fun deleteWord(word: WordUnit) {
        wordList.remove(word)
    }

    override fun editWord(word: WordUnit) {
        val oldWord = getWord(word.id)
        wordList.remove(oldWord)
        addWord(word)
    }

    override fun getWord(id: Int): WordUnit {
        return wordList.find { it.id == id } ?: throw RuntimeException("Not found")
    }

    override fun getWordList(): List<WordUnit> {
        return wordList.toList()
    }
}