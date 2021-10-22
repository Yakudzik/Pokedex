package com.example.pokedex.model

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.retrofit.PokemonApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel : ViewModel() {

    private val _mutableLiveData = MutableLiveData<PokemonModel>()
    val liveData: LiveData<PokemonModel> = _mutableLiveData

    lateinit var pokedexSize :List<PokemonPopulationModel.Result>

    fun getAllPokemon(){
        PokemonApi.invoke().getAllPokemonNames().enqueue(object : Callback<PokemonPopulationModel?> {
            override fun onResponse(
                call: Call<PokemonPopulationModel?>,
                response: Response<PokemonPopulationModel?>
            ) {
                if (response.isSuccessful){
                    var result = response.body()

                    pokedexSize = result!!.results

                }
            }

            override fun onFailure(call: Call<PokemonPopulationModel?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun findPokemon(nameOrNumber: String) {
        PokemonApi.invoke().searchPokemon(nameOrNumber).enqueue(object : Callback<PokemonModel?> {
            override fun onResponse(call: Call<PokemonModel?>, response: Response<PokemonModel?>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.i(" pokemon id", response.body()!!.id.toString())
                    Log.i(" pokemon name", response.body()!!.name)
                    Log.i(" pokemon baseExp", response.body()!!.baseExperience.toString())
                    Log.i(" pokemon image link", response.body()!!.sprites.frontDefault.toString())
                    val typeSize = response.body()!!.types.size
                    for (i in 0 until typeSize) {
                        Log.i(" pokemon $i type", response.body()!!.types[i].type.name)
                    }
                    _mutableLiveData.value = result!!

                }
            }

            override fun onFailure(call: Call<PokemonModel?>, t: Throwable) {
            }
        })
    }
}