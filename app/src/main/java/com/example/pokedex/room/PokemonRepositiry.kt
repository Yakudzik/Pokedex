package com.example.pokedex.room

import androidx.lifecycle.LiveData

class PokemonRepositiry(private val pokemonDao: PokemonDao) {

    val readAllData: LiveData<List<BasePokemon>> = pokemonDao.getAllData()

    suspend fun addPokemon(pokemon: BasePokemon) {
        pokemonDao.addPokemon(pokemon)
    }
    suspend  fun selectSamePokemon(pokeNumber: Int): BasePokemon {
        return pokemonDao.getOneElement(pokeNumber)
    }
    suspend fun deletePokemon(pokemon: BasePokemon){
        pokemonDao.deleteOneElement(pokemon)
    }
}