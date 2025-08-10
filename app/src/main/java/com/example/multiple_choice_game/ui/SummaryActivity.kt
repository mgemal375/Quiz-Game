package com.example.multiple_choice_game.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multiple_choice_game.R
import com.example.multiple_choice_game.util.GameManager


/**
 * SummaryActivity displays the final game results including:
 * - Player name
 * - Final score
 * - Chances left
 * - Summary of each question (correct or incorrect)
 * It also allows the user to restart the game.
 */
class SummaryActivity : AppCompatActivity() {

    /**
     * Initializes the summary screen, displays player info and results,
     * and sets up the "Play Again" button to restart the game.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Fetch views from layout
        val tvPlayerName = findViewById<TextView>(R.id.tv_summary_player)
        val tvScore = findViewById<TextView>(R.id.tv_summary_score)
        val tvChances = findViewById<TextView>(R.id.tv_summary_chances)
        val tvSummaryTitle = findViewById<TextView>(R.id.tv_summary_title)
        val layoutAnswers = findViewById<LinearLayout>(R.id.layout_question_summary)
        val btnPlayAgain = findViewById<Button>(R.id.btn_play_again)

        // Display player's name, score, and chances (IMPLEMENT THIS)
        tvPlayerName.text = GameManager.playerName
        tvChances.text = "Chances Left:${GameManager.remainingChances}"
        tvScore.text = "Fianl Score: ${GameManager.playerScore}"

        // Populate the question summary section (IMPLEMENT THIS)
        addQuestionSummary(GameManager.answeredQuestions,layoutAnswers)

        // Restart game and go back to MainActivity (IMPLEMENT THIS)
        btnPlayAgain.setOnClickListener{
            GameManager.reset()
            AlertDialog.Builder(this)
                .setMessage("Do want to play as a new player")
                .setTitle("New player")
                .setPositiveButton("Yes"){dialog,_->
                    finish()
                    startActivity(Intent(this,MainActivity::class.java))
                }
                .setNegativeButton("No"){dialog,_->
                    finish()
                    startActivity(Intent(this,GameActivity::class.java))
                }.show()

        }
    }
    /**
     * this funstion will create new textViews based in the number of answered quesiotns .
     * @param list hold the list of answered questions
     * @param layoutView is the View grou in which the text view will be inside
     *
     **/
    fun addQuestionSummary(list:MutableList<String>,layoutView:LinearLayout) {
        for (question in list) {
            val textView = TextView(this).apply {
                text = question
                textSize = 18f
                setPadding(16, 16, 16, 16)
            }
            layoutView.addView(textView)
        }
    }
}



