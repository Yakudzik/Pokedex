package com.example.pokedex

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokedex.databinding.FragmentItemInfoBinding
import com.example.pokedex.model.PokeBaseViewModel
import com.example.pokedex.model.PokemonViewModel
import com.example.pokedex.room.BasePokemon
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.util.*

class ItemInfoFragment : Fragment() {
    private lateinit var binding: FragmentItemInfoBinding
    private val viewModel: PokemonViewModel by activityViewModels()
    private val baseModel: PokeBaseViewModel by activityViewModels()
    private var types: String = ""
    private var presentIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemInfoBinding.inflate(inflater, container, false)
        binding.hpProgressID.max = 255
        binding.attackProgressID.max = 180
        binding.defenseProgressID.max = 230
        binding.spAtkProgressID.max = 180
        binding.spDefProgressID.max = 230
        binding.speedProgressID.max = 180

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previousButtonID.setOnClickListener {
            val st = presentIndex - 1

            viewModel.findPokemon(st.toString())
        }
        binding.nextButtonID.setOnClickListener {
            val st = presentIndex + 1
            viewModel.findPokemon(st.toString())
        }

        viewModel.liveData.observe(viewLifecycleOwner, { pokemon ->
            types = ""
            presentIndex = 0

            binding.pokemonNameID.text = camelFirstLetter(pokemon.name)
            binding.pokemonNumberID.text = "№ ${pokemon.id}"
            presentIndex = pokemon.id
            checkIndex(presentIndex) //проверяю индекс и прячу крайние кнопки перехода
            binding.pokemonExpID.text = "${pokemon.baseExperience} Exp."

            if (pokemon.types.size == 2) {
                binding.pokemonType2ID.visibility = View.VISIBLE

                binding.pokemonType1ID.text = pokemon.types[0].type.name
                binding.pokemonType2ID.text = pokemon.types[1].type.name
            } else {
                binding.pokemonType2ID.visibility = View.INVISIBLE
                binding.pokemonType1ID.text = pokemon.types[0].type.name
            }

            fillStats(
                pokemon.stats[0].baseStat,
                pokemon.stats[1].baseStat,
                pokemon.stats[2].baseStat,
                pokemon.stats[3].baseStat,
                pokemon.stats[4].baseStat,
                pokemon.stats[5].baseStat,
            )

            Picasso.get()
                .load(pokemon.sprites.frontDefault) //.load(pokemon.sprites.frontDefault)
                .noPlaceholder()
                .resize(750, 750)
                .into(binding.pokemonAvatarID)

            checkLike(pokemon.id)

            checkFavoriteItem(pokemon.id, pokemon.name)
        })
    }

    private fun fillStats(hp: Int, attack: Int, def: Int, spAtk: Int, spDef: Int, speed: Int) {
        val duration = 500L
        ObjectAnimator.ofInt(binding.hpProgressID, "progress", hp)
            .setDuration(duration)
            .start()
        binding.hpStatID.text = hp.toString()
        ObjectAnimator.ofInt(binding.attackProgressID, "progress", attack)
            .setDuration(duration)
            .start()
        binding.attackStatID.text = attack.toString()

        ObjectAnimator.ofInt(binding.defenseProgressID, "progress", def)
            .setDuration(duration)
            .start()
        binding.defenseStatID.text = def.toString()

        ObjectAnimator.ofInt(binding.spAtkProgressID, "progress", spAtk)
            .setDuration(duration)
            .start()
        binding.spAtkStatID.text = spAtk.toString()

        ObjectAnimator.ofInt(binding.spDefProgressID, "progress", spDef)
            .setDuration(duration)
            .start()
        binding.spDefStatID.text = spDef.toString()

        ObjectAnimator.ofInt(binding.speedProgressID, "progress", speed)
            .setDuration(duration)
            .start()
        binding.speedStatID.text = speed.toString()

    }

    private fun checkIndex(index: Int) {
        if (index == 1)
            binding.previousButtonID.visibility = View.INVISIBLE
        else
            binding.previousButtonID.visibility = View.VISIBLE
        if (index == 898)
            binding.nextButtonID.visibility = View.INVISIBLE
        else
            binding.nextButtonID.visibility = View.VISIBLE
    }

    private fun checkLike(pokemonApiId: Int) {
        baseModel.getSamePokemon(pokemonApiId)

        baseModel.number.observe(viewLifecycleOwner, { number ->

            if (pokemonApiId == number) {
                Log.i("Liked", number.toString())
                binding.likeCheckboxId.isChecked = true

            } else {
                Log.i("Disliked", number.toString())
                binding.likeCheckboxId.isChecked = false
            }
        })
    }

    private fun checkFavoriteItem(pokemonId: Int, name: String) {
        val name = camelFirstLetter(name)
        var pokemon = BasePokemon(0, pokemonId, name, true)

        binding.likeCheckboxId.setOnClickListener {
            if (binding.likeCheckboxId.isChecked) {
                baseModel.addPokemon(pokemon)
                Snackbar.make(requireView(), "Liked", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.bottom_navigation_view_ID).show()
            } else if (!binding.likeCheckboxId.isChecked) {
                if (baseModel.pokemon!!.pokemonNumber == pokemonId) {
                    pokemon.id = baseModel.pokemon!!.id
                    baseModel.deletePokemon(pokemon)
                    Snackbar.make(requireView(), "Disliked", Snackbar.LENGTH_SHORT)
                        .setAnchorView(R.id.bottom_navigation_view_ID).show()
                }
            }
        }
    }

    private fun camelFirstLetter(string: String): String {
        return string.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else
                it.toString()
        }
    }
}