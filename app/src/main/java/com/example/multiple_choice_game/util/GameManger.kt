package com.example.multiple_choice_game.util

import com.example.multiple_choice_game.models.Question

/**
 * GameManager is a singleton object that manages and persists game state
 * across activities during a game session. It tracks:
 * - Player information
 * - Score and remaining chances
 * - List of current questions
 * - Answer log for summary screen
 *
 */
object GameManager {

// READ THE ABOVE AND COMPLETE THIS

    var playerName :String =""
    var playerScore:Int = 0
    var listOfRandomQuestions:List<Question> = mutableListOf()
    var remainingChances:Int = 2 ;
    var answeredQuestions:MutableList<String> = mutableListOf()

    /**
     * Resets all game data to start a new session.
     * Called when the player launches or restarts the game.
     * IMPLEMENT THIS
     */
    fun reset() {
        this.answeredQuestions = mutableListOf()
        this.playerScore = 0
        this.remainingChances = 2
        this.listOfRandomQuestions= mutableListOf()


    }
}
