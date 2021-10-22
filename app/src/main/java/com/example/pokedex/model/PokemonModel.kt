package com.example.pokedex.model


import com.google.gson.annotations.SerializedName

data class PokemonModel(
//    @SerializedName("abilities")
//    val abilities: List<Ability>,
    @SerializedName("base_experience")
    var baseExperience: Int,
//    @SerializedName("forms")
//    val forms: List<Form>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("stats")
    val stats: List<Stat>,
    @SerializedName("types")
    var types: List<Type>,
//    @SerializedName("weight")
//    val weight: Int,
) {
    data class Sprites(
        @SerializedName("front_default")
        val frontDefault: String
    )
    data class Stat(
        @SerializedName("base_stat")
        val baseStat: Int,
        @SerializedName("effort")
        val effort: Int,
        @SerializedName("stat")
        val stat: Stat
    ) {
        data class Stat(
            @SerializedName("name")
            val name: String,
//            @SerializedName("url")
//            val url: String
        )
    }
    data class Type(
        @SerializedName("type")
        val type: Type
    ) {
        data class Type(
            @SerializedName("name")
            val name: String,
//            @SerializedName("url")
//            val url: String
        )
    }
}