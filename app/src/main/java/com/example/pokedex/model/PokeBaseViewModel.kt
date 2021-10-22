package com.example.pokedex.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.room.BasePokemon
import com.example.pokedex.room.PokemonDataBase
import com.example.pokedex.room.PokemonRepositiry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PokeBaseViewModel(application: Application) : AndroidViewModel(application) {

    private var _number = MutableLiveData<Int>(0)
    val number: LiveData<Int> = _number
    val readAllData: LiveData<List<BasePokemon>>
    private val repositiry: PokemonRepositiry
    var pokemon: BasePokemon? = null

    init {
        val pokemonDao = PokemonDataBase.getDatabase(application).pokemonDao()
        repositiry = PokemonRepositiry(pokemonDao)
        readAllData = repositiry.readAllData
    }


    fun addPokemon(pokemon: BasePokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            repositiry.addPokemon(pokemon)
        }
    }

    fun deletePokemon(pokemon: BasePokemon){
        viewModelScope.launch(Dispatchers.IO) {
            repositiry.deletePokemon(pokemon)
        }
    }

    //request 4 search same pokemon from base. Add returned pokemon's number in liveData obj
    fun getSamePokemon(pokeNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            //delay(10)
            pokemon = repositiry.selectSamePokemon(pokeNumber)
            if (pokemon.toString().isEmpty())
                Log.i("query is empty", pokemon.toString())
            else {
                try {
                    _number.postValue(pokemon!!.pokemonNumber!!)
                } catch (e: NullPointerException) {
                    Log.i("query isn't empty BUT nullException", null.toString())
                }
            }
        }
    }
}