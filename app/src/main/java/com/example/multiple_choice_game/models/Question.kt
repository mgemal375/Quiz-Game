package com.example.multiple_choice_game.models

/**
 * Data class representing a single quiz question.
 * This matches the structure returned from the Questions API.
 *
 *  IMPLEMENT THIS
 */
data class Question(
    val _id:String? = null,
    val title:String ,
    val correctAnswer: String ,
    val incorrectAnswers:MutableList<String>
)
