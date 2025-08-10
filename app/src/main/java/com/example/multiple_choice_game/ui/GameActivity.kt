package com.example.multiple_choice_game.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multiple_choice_game.R
import com.example.multiple_choice_game.util.GameManager
import com.example.quizgame.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


/**
 * GameActivity is responsible for:
 * - Displaying questions
 * - Randomizing and handling answer selection
 * - Tracking score and chances
 * - Navigating to the Summary screen
 */
class GameActivity : AppCompatActivity() {

    private lateinit var tvQuestionCounter: TextView
    private lateinit var tvPlayerName: TextView
    private lateinit var tvQuestionTitle: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvChances: TextView
    private lateinit var btnChoices: List<Button>
    private lateinit var linearLayout:LinearLayout
    var currentQuestion :Int = 0 // keep track of the current question number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Initialize and reference all views from the layout
        tvPlayerName = findViewById(R.id.tv_player_name)
        tvQuestionCounter = findViewById(R.id.tv_question_count)
        tvQuestionTitle = findViewById(R.id.tv_question_title)
        tvScore = findViewById(R.id.tv_score)
        tvChances = findViewById(R.id.tv_chances_remaining)


        btnChoices = listOf<Button>(
            findViewById(R.id.btn_choice_1),
            findViewById(R.id.btn_choice_2),
            findViewById(R.id.btn_choice_3),
            findViewById(R.id.btn_choice_4)
        ).shuffled()// Initial shuffling of hte order of buttons


        tvPlayerName.text = "Welcome, ${GameManager.playerName}"
        tvChances.text = "Chance Remaining: ${GameManager.remainingChances}"
        loadQuestions()
    }

    /**
     * Loads 5 random questions from the API using Retrofit
     * and stores them in the GameManager. Once fetched, it
     * calls showNextQuestion() to begin the game.
     */
    private fun loadQuestions() {
       //IMPLEMENT THIS

        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = RetrofitClient.api.getRandomQuestions()
                if (response.isSuccessful){
                    GameManager.listOfRandomQuestions = response.body()?.data?:emptyList()
                   runOnUiThread {
                       showNextQuestion() }
                }else{
                    runOnUiThread {
                        Toast.makeText(this@GameActivity, "Failed to fetch Questions", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (error:HttpException){
                runOnUiThread {
                    Toast.makeText(this@GameActivity, "HTTP error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            }catch (error:Exception){
                runOnUiThread {
                    Toast.makeText(this@GameActivity, "HTTP error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * Displays the current question, updates the UI (question text, score, chances),
     * shuffles answer choices, and sets up click listeners for each option.
     * Navigates to summary screen if all questions are answered or chances are used up.
     */
    private fun showNextQuestion() {
       // Toast.makeText(this@GameActivity, "hello ",Toast.LENGTH_SHORT).show()
        Log.d("QG","What is happening ")
        //Updating the ui
        tvQuestionTitle.text = GameManager.listOfRandomQuestions[currentQuestion].title
        btnChoices[0].text = GameManager.listOfRandomQuestions[currentQuestion].correctAnswer
        btnChoices[1].text = GameManager.listOfRandomQuestions[currentQuestion].incorrectAnswers[0]
        btnChoices[2].text = GameManager.listOfRandomQuestions[currentQuestion].incorrectAnswers[1]
        btnChoices[3].text = GameManager.listOfRandomQuestions[currentQuestion].incorrectAnswers[2]
        tvQuestionCounter.text = "Question " + (currentQuestion+1).toString() + " of 5"
        for (btn in btnChoices){
            btn.setOnClickListener{
                var isCorrect :Boolean //
                if(GameManager.listOfRandomQuestions[currentQuestion].correctAnswer == btn.text){
                    isCorrect=true
                    GameManager.playerScore+=10
                }else{
                    GameManager.remainingChances--
                    if(GameManager.playerScore>=5){GameManager.playerScore-=5}else{GameManager.playerScore=0}
                    isCorrect=false
                }
                showAnswerDialog(isCorrect)
            }
        }
        btnChoices=btnChoices.shuffled()

    }






    /**
     * Displays an AlertDialog showing whether the user's selected answer
     * was correct or incorrect. Proceeds to the next question after dismissal.
     */
    private fun showAnswerDialog(isCorrect: Boolean) {
        val answer = if(isCorrect){"Correct"}else{"Incorrect"}
        val questionSummary :String = (currentQuestion+1).toString() + ".${GameManager.listOfRandomQuestions[currentQuestion].title}-${answer}"
        AlertDialog.Builder(this)
            .setTitle(" Result")
            .setMessage(answer)
            .setPositiveButton("OK") { dialog, _ ->
                if(currentQuestion<4 && GameManager.remainingChances>0){
                    dialog.dismiss()
                    currentQuestion++
                    tvScore.text = "Score: "+GameManager.playerScore.toString()
                    tvChances.text = "Chances Left:${GameManager.remainingChances}"
                    GameManager.answeredQuestions.add(questionSummary)
                    showNextQuestion()
                }else{
                    finish()
                    dialog.dismiss()
                    GameManager.answeredQuestions.add(questionSummary)
                    startActivity(Intent(this,SummaryActivity::class.java))
                }



            }
            .show()
       // IMPLEMENT THIS
    }

    /**
     * Navigates to the SummaryActivity screen to show the game results
     * and ends the current GameActivity.
     */
    private fun goToSummary() {
        //IMPLEMENT THIS
    }
}


