package com.example.pokedex

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pokedex.databinding.FragmentRandomBinding
import com.example.pokedex.model.PokemonViewModel
import kotlin.random.Random

class RandomFragment : Fragment() {
     private lateinit var binding: FragmentRandomBinding
    private val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {findNavController().navigate(R.id.itemInfoFragment)}
        }


        binding.randomButtonID.setOnClickListener {
            var randomNum = (1..898).random().toString()
            binding.randomNumberID.text = randomNum
            viewModel.findPokemon(randomNum)

            timer.start()
        }
    }
}