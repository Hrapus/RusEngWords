package com.example.rusengwords.domain

data class WordUnit(
    val rusWord: String,
    val engWord: String,
    val actuality: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}