package com.example.rusengwords.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rusengwords.data.WordListRepositoryImpl
import com.example.rusengwords.domain.DeleteWordUseCase
import com.example.rusengwords.domain.EditWordUseCase
import com.example.rusengwords.domain.GetWordsListUseCase
import com.example.rusengwords.domain.WordUnit

class MainViewModel : ViewModel() {

    private val repository = WordListRepositoryImpl
    val wordList = MutableLiveData<List<WordUnit>>()

    private val getWordsListUseCase = GetWordsListUseCase(repository)
    private val deleteWordUseCase = DeleteWordUseCase(repository)
    private val editWordUseCase = EditWordUseCase(repository)

    fun getWordList(){
        val list = getWordsListUseCase.getWordList()
        wordList.value = list
    }

    fun deleteWord(word: WordUnit){
        deleteWordUseCase.deleteWord(word)
        getWordList()
    }

    fun changeEnableState(word: WordUnit){
        val newWord = word.copy(actuality = !word.actuality)
        editWordUseCase.editWord(newWord)
        getWordList()
    }
}