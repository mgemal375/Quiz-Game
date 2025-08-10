package com.example.multiple_choice_game.api

/**
 * Generic wrapper to handle responses from the API.
 * API always returns a message and a data object/array.
 * IMPLEMENT THIS
 */
data class ApiResponse<T>(
    val message: String,
    val data: T,
    val totalQuestions: Int? = null
)

