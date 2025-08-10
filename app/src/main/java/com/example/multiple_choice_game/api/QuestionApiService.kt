package com.example.multiple_choice_game.api

import com.example.multiple_choice_game.models.Question
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit service interface that defines endpoints of the Questions API.
 * IMPLEMENT THIS
 */
interface QuestionApiService {

    /**
    * Fetches a list of all Questions from the API.
    * GET /questions
    */
   @GET("questions")
   suspend fun getAllQeustions():Response<ApiResponse<List<Question>>>
    /**
     * Fetches a single questions by ID.
     * GET /questions/{id}
     * @param id The unique ID of the question
     */
   @GET("questions/{id}")
   suspend fun getQuestionsById(@Path("id") id:String ):Response<ApiResponse<Question>>

    /**
     * Fetches Random 5 questions  from the API.
     * GET /questions/random
     */
   @GET("questions/random")
   suspend fun getRandomQuestions():Response<ApiResponse<List<Question>>>

    /**
     * Creates a new question on the server.
     * POST /questions
     * @param question The question object to be created (sent in request body)
     */
   @POST("questions")
   suspend fun createQuestion(@Body question:Question):Response<ApiResponse<Question>>

    /**
     * Updates an existing question on the server.
     * PATCH /questions/{id}
     * @param id The ID of the question to update
     * @param question The updated question data
     */
   @PATCH("question/{id}")
   suspend fun updateQuestion(@Body question:Question):Response<ApiResponse<Question>>

    /**
     * Deletes a question by ID from the server.
     * DELETE /questions/{id}
     * @param id The ID of the question to delete
     */
   @DELETE("question/{id}")
   suspend fun deletQuestion(@Path("id") id :String ):Response<Void>
}

