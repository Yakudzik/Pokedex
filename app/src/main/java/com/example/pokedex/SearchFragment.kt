package com.example.pokedex

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.pokedex.databinding.FragmentSearchBinding
import com.example.pokedex.model.PokemonViewModel


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllPokemon()
        //  var inputStatus: Boolean? = null

        binding.buttonSearchID.setOnClickListener {
            val pokemonNameOrId =
                Editable.Factory.getInstance().newEditable(binding.inputSearchID.text)
            val id = pokemonNameOrId.toString().lowercase().filter { it.isLetterOrDigit() }

            if (id.toIntOrNull() != null) {
              val intEx = id.toInt()
                if (intEx in 1..898) {
                    viewModel.findPokemon(id)
                    findNavController().navigate(R.id.itemInfoFragment)
                } else
                    Toast.makeText(context, "Uncorrected input", Toast.LENGTH_SHORT).show()
            } else {
                if (viewModel.pokedexSize.find { it.name == id } != null) {
                    viewModel.findPokemon(id)
                    findNavController().navigate(R.id.itemInfoFragment)
                } else
                    Toast.makeText(context, "Unknown pokemon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}