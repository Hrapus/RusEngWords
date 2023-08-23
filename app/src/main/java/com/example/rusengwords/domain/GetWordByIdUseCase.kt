package com.example.rusengwords.domain

class GetWordByIdUseCase(private val wordListRepository: WordListRepository) {

    fun getWord(id: Int): WordUnit{
        return wordListRepository.getWord(id)
    }
}