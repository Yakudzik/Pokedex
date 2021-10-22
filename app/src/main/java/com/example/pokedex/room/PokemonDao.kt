package com.example.pokedex.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPokemon(pokemon: BasePokemon)

    @Delete
    fun deleteOneElement(pokemon: BasePokemon)

    @Query("SELECT * FROM favorite_pokemons WHERE pokemonNumber =:number")
    fun getOneElement(number: Int):BasePokemon

    @Query("SELECT * FROM favorite_pokemons ORDER BY id ASC")
    fun getAllData(): LiveData<List<BasePokemon>>
}