package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokedex.databinding.ActivityNavigationHostBinding
import com.example.pokedex.model.PokeBaseViewModel
import com.example.pokedex.model.PokemonViewModel
import javax.net.ssl.SSLSessionBindingEvent

class NavigationHostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationHostBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView_ID)

        binding.bottomNavigationViewID.setupWithNavController(navController)

    }
}