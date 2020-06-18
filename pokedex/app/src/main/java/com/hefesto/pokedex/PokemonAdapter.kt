package com.hefesto.pokedex

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hefesto.pokedex.data.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class PokemonAdapter(
    private val pokemons: List<Pokemon>,
    private val onItemClick: (Pokemon)-> Unit
) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pokemon, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun getItemCount(): Int = pokemons.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.itemView.tvPokemonName.text = pokemons[position].name
        holder.itemView.tvPokemonNumber.text = "#%03d".format(pokemons[position].number)
        Picasso.get().load(pokemons[position].imageUrl).into(holder.itemView.ivPokemonImage)

        holder.itemView.setOnClickListener {
            onItemClick(pokemons[position])
        }
    }
}