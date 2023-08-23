package com.example.rusengwords.domain

class AddWordUseCase(private val wordListRepository: WordListRepository) {

    fun addWord(word: WordUnit){
        wordListRepository.addWord(word)
    }
}