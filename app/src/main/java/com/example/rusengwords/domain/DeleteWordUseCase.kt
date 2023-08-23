package com.example.rusengwords.domain

class DeleteWordUseCase(private val wordListRepository: WordListRepository) {

        fun deleteWord(word: WordUnit){
            wordListRepository.deleteWord(word)
        }

}