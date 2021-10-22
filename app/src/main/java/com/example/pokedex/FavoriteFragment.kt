package com.example.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentFavoriteBinding
import com.example.pokedex.model.PokeBaseViewModel
import com.example.pokedex.model.PokemonViewModel
import com.example.pokedex.recycler.MyRecyclerAdapter


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    lateinit var adapter: MyRecyclerAdapter
    private val baseViewModel: PokeBaseViewModel by activityViewModels()
    private val viewModel: PokemonViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        //recycler
        adapter = MyRecyclerAdapter(baseViewModel,viewModel,this)
        val recyclerView = binding.recyclerviewID
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        //observe
        baseViewModel.readAllData.observe(viewLifecycleOwner, Observer { pokemon ->
            adapter.setData(pokemon)

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}