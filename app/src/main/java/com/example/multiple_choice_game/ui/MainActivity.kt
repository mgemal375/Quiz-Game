package com.example.multiple_choice_game.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multiple_choice_game.R
import com.example.multiple_choice_game.models.Question
import com.example.multiple_choice_game.util.GameManager
import com.example.quizgame.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


/**
 * This is the main (welcome) screen where the player enters their name,
 * can start the game, view rules, or add a new question to the API.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var etPlayerName: EditText
    private lateinit var btnStartGame: Button
    private lateinit var btnRules: Button
    private lateinit var btnAddQuestion: Button

    /**
     * Initializes the welcome screen, sets up view references, and handles
     * button interactions for starting the game, viewing rules, and adding questions.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fetch views by ID
        etPlayerName = findViewById(R.id.et_player_name)
        btnStartGame = findViewById(R.id.btn_start_game)
        btnRules = findViewById(R.id.btn_rules)
        btnAddQuestion = findViewById(R.id.btn_add_question)

        // Handle "Start Game" button click
        btnStartGame.setOnClickListener {
            val name = etPlayerName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            GameManager.reset()
            // Reset game data before starting (IMPLEMENT THIS)

            GameManager.playerName = name
            // Save player name and start GameActivity (IMPLEMENT THIS)
            val intent  = Intent(this ,GameActivity::class.java)
            Log.d("QG"," reseted teh game and saved the name of the player")
            startActivity(intent)
            finish()
        }

        // Handle "Rules" button click
        btnRules.setOnClickListener {
            showRulesDialog()
        }

        // Handle "Add Question" button click
        btnAddQuestion.setOnClickListener {
            showAddQuestionDialog()
        }
    }

    /**
     * Displays the game rules in an AlertDialog when the user taps the "Rules" button.
     *
     */
    private fun showRulesDialog() {
        //IMPLEMENT THIS
        AlertDialog.Builder(this)
            .setTitle("Game Rules")
            .setMessage(
                    "1. You will be asked 5 random questions.\n" +
                    "2. Each correct answer = +10 points.\n" +
                    "3. Each wrong answer = -5 points.\n" +
                    "4. You can only get 2 questions wrong.\n" +
                    "5. Answers are shuffled every round!")
            .setPositiveButton("Ok"){dialog,_->
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Displays a form in a dialog that allows the user to enter a new question,
     * correct answer, and three incorrect answers. On submission, the question
     * is posted to the API and a success or error dialog is shown.
     *
     *
     */
    private fun showAddQuestionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_question, null)

        val etTitle = dialogView.findViewById<EditText>(R.id.et_question_title)
        val etCorrect = dialogView.findViewById<EditText>(R.id.et_correct_answer)
        val etIncorrect1 = dialogView.findViewById<EditText>(R.id.et_incorrect_1)
        val etIncorrect2 = dialogView.findViewById<EditText>(R.id.et_incorrect_2)
        val etIncorrect3 = dialogView.findViewById<EditText>(R.id.et_incorrect_3)

       //IMPLEMENT DIALOG BOX HERE and the rest of the code

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add a New Question")
            .setPositiveButton("Submit") { dialog, _ ->
                val title = etTitle.text.toString().trim()
                val corectAnswer = etCorrect.text.toString().trim()
                val incorrectAnswers = mutableListOf(
                    etIncorrect3.text.toString().trim(),
                    etIncorrect1.text.toString().trim(),
                    etIncorrect2.text.toString().trim()
                )
            if (title.isEmpty()||corectAnswer.isEmpty()||incorrectAnswers.isEmpty()){
                mutableListOf(etIncorrect3.text.toString(),etIncorrect1.text.toString(),etIncorrect2.text.toString())

            }
                val newQuestion = Question(
                    title = title,
                    correctAnswer = corectAnswer,
                    incorrectAnswers = incorrectAnswers
                )
                Log.d("QG",newQuestion.toString())

                creatQuestion(newQuestion)

            }.setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
            .show()
    }
    /**
     * Creates a question in the database using the Api end point
     * **/
   private fun creatQuestion(question:Question){

        CoroutineScope(Dispatchers.IO).launch {
            try {
                var response = RetrofitClient.api.createQuestion(question)
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Question added!", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Failed to add Question", Toast.LENGTH_SHORT).show()
                    }
                }

            }catch (error:HttpException){
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "HTTP error: ${error.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }catch (error:Exception){
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "HTTP error: ${error.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }
}

