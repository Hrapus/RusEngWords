package com.example.rusengwords.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rusengwords.domain.WordListRepository
import com.example.rusengwords.domain.WordUnit
import java.lang.RuntimeException
import kotlin.random.Random

object WordListRepositoryImpl: WordListRepository {

    private val wordListLD = MutableLiveData<List<WordUnit>>()
    private val wordList = sortedSetOf<WordUnit>({o1, o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0

    init {
        for(i in 0..10){
            addWord(WordUnit("Русский №$i", "Endlish #$i", Random.nextBoolean()))
        }
    }

    override fun addWord(word: WordUnit) {
        if (word.id == WordUnit.UNDEFINED_ID){
            word.id = autoIncrementId++
        }
        wordList.add(word)
        updateList()
    }

    override fun deleteWord(word: WordUnit) {
        wordList.remove(word)
        updateList()
    }

    override fun editWord(word: WordUnit) {
        val oldWord = getWord(word.id)
        wordList.remove(oldWord)
        addWord(word)
    }

    override fun getWord(id: Int): WordUnit {
        return wordList.find { it.id == id } ?: throw RuntimeException("Not found")
    }

    override fun getWordList(): LiveData<List<WordUnit>> {
        return wordListLD
    }

    private fun updateList(){
        wordListLD.value = wordList.toList()
    }
}