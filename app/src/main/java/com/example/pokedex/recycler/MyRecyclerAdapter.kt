package com.example.pokedex.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.FavoriteFragment
import com.example.pokedex.R
import com.example.pokedex.databinding.OneFavoriteItemBinding
import com.example.pokedex.model.PokeBaseViewModel
import com.example.pokedex.model.PokemonViewModel
import com.example.pokedex.room.BasePokemon

class MyRecyclerAdapter(
    private val viewModelBase: PokeBaseViewModel,
    private val viewModel: PokemonViewModel,
    private val fragment: FavoriteFragment
) : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>() {
    private var pokemonList = emptyList<BasePokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            OneFavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        holder.itemView.findViewById<TextView>(R.id.one_fav_item_number_ID).text =
            currentItem.pokemonNumber.toString()
        holder.itemView.findViewById<TextView>(R.id.one_fav_item_name_ID).text =
            currentItem.pokemonName

        holder.itemView.setOnClickListener {
            viewModel.findPokemon(pokemonList[position].pokemonNumber.toString())
            findNavController(fragment).navigate(R.id.itemInfoFragment)
        }
        holder.binding.oneFavItemDeleteButtonID.setOnClickListener {
            pokemonList.drop(position)
            delData(pokemonList[position])

        }
    }

    override fun getItemCount(): Int = pokemonList.size

    class MyViewHolder(var binding: OneFavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun setData(pokemonList: List<BasePokemon>) {
        this.pokemonList = pokemonList
        notifyDataSetChanged()
    }

    private fun delData(pokemon: BasePokemon) {

        viewModelBase.deletePokemon(pokemon)
        notifyDataSetChanged()
        Log.i("delete !!!","Deleted!")

    }

}