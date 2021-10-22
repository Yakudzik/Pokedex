package com.example.pokedex.retrofit

import com.example.pokedex.model.PokemonModel
import com.example.pokedex.model.PokemonPopulationModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    //   /api/v2/pokemon/898/
    @GET("/api/v2/pokemon/{id}")
    fun searchPokemon(@Path("id") nameOrNumber: String): retrofit2.Call<PokemonModel>

    @GET("/api/v2/pokemon/?limit=898")
    fun getAllPokemonNames() : retrofit2.Call<PokemonPopulationModel>

    companion object {
        fun invoke(): PokemonApi {
            //перехватчик для просмотра ответов сервера
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokemonApi::class.java)
        }
    }
}