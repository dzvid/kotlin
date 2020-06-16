package com.hefesto.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_detail.*

class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        intent.getParcelableExtra<Pokemon>(POKEMON_EXTRA)?.let{pokemon ->
            tvName.text = pokemon.name
            tvNumber.text = "#%03d".format(pokemon.number)
            tvFirstType.text = pokemon.types.getOrNull(0)
            val secondType = pokemon.types.getOrNull(1)
            if(secondType == null){
                tvSecondType.visibility = View.GONE
            }else{
                tvSecondType.visibility = View.VISIBLE
                tvSecondType.text = secondType
            }
            tvWeight.text = "%.1f kg".format(pokemon.weight)
            tvHeight.text = "%.2f m".format(pokemon.height / 10)
            Picasso.get().load(pokemon.imageUrl).into(ivImage)

            // Configurando o fragment e passando a localização para colocar o marcador no map (it é o mapa)
            val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
            mapFragment.getMapAsync{
                it.uiSettings.isZoomControlsEnabled = true

                // latitude e longitude do pokemon
                val latLng = LatLng(pokemon.latitude, pokemon.longitude)

                val marker = MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))

                it.addMarker(marker)

                //Setar o mapa pra mostrar a localização
                it.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                it.moveCamera(CameraUpdateFactory.zoomTo(15f))

            }
        }
    }

    companion object{
        const val POKEMON_EXTRA = "Pokemon"
    }
}