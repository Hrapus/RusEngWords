package com.example.rusengwords.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rusengwords.data.WordListRepositoryImpl
import com.example.rusengwords.domain.AddWordUseCase
import com.example.rusengwords.domain.EditWordUseCase
import com.example.rusengwords.domain.GetWordByIdUseCase
import com.example.rusengwords.domain.WordUnit

class WordItemViewModel: ViewModel() {

    private val repository = WordListRepositoryImpl

    private val getWordUseCase = GetWordByIdUseCase(repository)
    private val addWordUseCase = AddWordUseCase(repository)
    private val editWordUseCase = EditWordUseCase(repository)

    private val _errorInputWord = MutableLiveData<Boolean>()
    val errorInputWord: LiveData<Boolean>
        get() = _errorInputWord

    private val _word = MutableLiveData<WordUnit>()
    val word: LiveData<WordUnit>
        get() = _word

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen : LiveData<Unit>
        get() = _shouldCloseScreen

    fun getWord(wordId: Int){
        val word= getWordUseCase.getWord(wordId)
        _word.value = word
    }

    fun addWord(eng: String?, rus: String?){
        val engWord = parseWord(eng)
        val rusWord = parseWord(rus)
        val fieldsValid = validateInput(engWord, rusWord)
        if (fieldsValid) {
            addWordUseCase.addWord(WordUnit(rusWord, engWord, true))
        }
        finishWork()
    }

    fun editWord(eng: String?, rus: String?){
        val engWord = parseWord(eng)
        val rusWord = parseWord(rus)
        val fieldsValid = validateInput(engWord, rusWord)
        if (fieldsValid) {
            _word.value?.let {
                val word = it.copy(rusWord = rusWord, engWord = engWord)
                editWordUseCase.editWord(word)
                finishWork()
            }
        }
    }

    private fun parseWord(input: String?): String{
        return input?.trim() ?: ""
    }

    private fun validateInput(eng: String, rus: String): Boolean {
        var result = true
        if (eng.isBlank() ){
            _errorInputWord.value = false
            result = false
        }
        return result
    }

    fun resetError(){
        _errorInputWord.value = true
    }

    private fun finishWork(){
        _shouldCloseScreen.value = Unit
    }
}