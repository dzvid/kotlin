package com.hefesto.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pokemons = listOf("Pikachu", "Voltorb", "Charmander")
        rvPokemons.adapter = PokemonAdapter(pokemons)
        shouldDisplayEmptyView(pokemons.isEmpty())
    }

    fun shouldDisplayEmptyView(isEmpty: Boolean){
        emptyView.visibility = if(isEmpty) View.VISIBLE else View.GONE
    }

    class PokemonAdapter(private val pokemons: List<String>): RecyclerView.Adapter< PokemonAdapter.PokemonViewHolder>(){
        class PokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon, parent, false)
            return PokemonViewHolder(itemView)
        }

        override fun getItemCount():Int = pokemons.size

        override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
            holder.itemView.tvPokemonName.text = pokemons[position]
        }
    }
}