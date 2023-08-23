package com.example.rusengwords.domain

class EditWordUseCase(private val wordListRepository: WordListRepository) {

    fun editWord(word: WordUnit){
        wordListRepository.editWord(word)
    }
}