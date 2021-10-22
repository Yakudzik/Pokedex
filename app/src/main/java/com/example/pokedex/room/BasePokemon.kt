package com.example.pokedex.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemons")
data class BasePokemon(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var pokemonNumber: Int?=null,
    var pokemonName: String,
    var like: Boolean
)
