package com.example.rusengwords.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GetWordsListUseCase(private val wordListRepository: WordListRepository) {

    fun getWordList(): List<WordUnit> {
        return wordListRepository.getWordList()
    }
}