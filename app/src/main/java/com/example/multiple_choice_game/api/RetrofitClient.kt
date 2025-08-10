package com.example.quizgame.api

import com.example.multiple_choice_game.api.QuestionApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitClient is a singleton object that configures and provides access
 * to the Question API using Retrofit. It initializes Retrofit with:
 * - The base URL of the live question API
 * - A GSON converter for serializing/deserializing JSON responses
 */
object RetrofitClient {

    /** The base URL of the Questions RESTful API */
    private const val BASE_URL = "https://questions-api-36jw.onrender.com/"

    /**
     * Lazily initializes the API interface once and reuses it for all HTTP calls.
     * This ensures efficient memory use and avoids recreating Retrofit instances.
     */
    val api: QuestionApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApiService::class.java)
    }
}
