package com.hefesto.pokedex.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.libraries.places.api.Places
import com.hefesto.pokedex.*
import com.hefesto.pokedex.data.AppDatabase
import com.hefesto.pokedex.data.Pokemon
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PokemonAdapter
    private var pokemons: MutableList <Pokemon> = mutableListOf ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Places.initialize(applicationContext,
            BuildConfig.GOOGLE_API_KEY
        )
        setUpRecyclerView()
        fabAddPokemon.setOnClickListener{
            val intent = Intent(this, AddPokemonActivity::class.java)
            // Metodo pra esperar que a activity retorne algum dado para esta tela
            startActivity(intent)
        }
        shouldDisplayEmptyView()

    }

    private fun setUpRecyclerView() {
        adapter = PokemonAdapter(pokemons) {
            val intent = Intent(
                this,
                PokemonDetailActivity::class.java
            ).apply {
                //adicionando dados do pokemon para serem enviados para a outra tela
                putExtra(
                    PokemonDetailActivity.POKEMON_EXTRA,
                    it
                )
            }
            startActivity(intent)
        }
        rvPokemons.adapter = adapter
    }

    private fun shouldDisplayEmptyView() {
        emptyView.visibility = if (pokemons.isEmpty()) View.VISIBLE else View.GONE
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == ADD_POKEMON_REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            // Adiciona na lista esse pokemon do resultado
//            data?.getParcelableExtra<Pokemon>(AddPokemonActivity.ADD_POKEMON_EXTRA)?.let{
//
//                pokemons.add(it)
//                adapter.notifyDataSetChanged()
//            }
//
//        }
//    }

    override fun onResume() {
        super.onResume()

       val updatedPokemonList =  AppDatabase.getInstance(this).pokemonDao.selectAll()

        updateRecyclerView(updatedPokemonList)

    }

    private fun updateRecyclerView(updatedPokemonList: List<Pokemon>){
        pokemons.clear()
        pokemons.addAll(updatedPokemonList)
        adapter.notifyDataSetChanged()
        shouldDisplayEmptyView()
    }

    companion object{
        const val ADD_POKEMON_REQUEST_CODE = 1
    }
}