package com.burakkarahan.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.burakkarahan.movies.R
import com.burakkarahan.movies.databinding.SliderItemBinding
import com.burakkarahan.movies.model.MovieModel
import com.burakkarahan.movies.view.HomeFragmentDirections
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(imageUrl: List<MovieModel>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    var sliderList: List<MovieModel> = imageUrl

    override fun getCount(): Int {
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder? {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = SliderItemBinding.inflate(inflater, parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder?, position: Int) {

        sliderList.let {
            if (holder != null) {
                Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500" + it[position].movieImagePath)
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.view.ivMovieImage)

                holder.view.tvMovieTitle.text = it[position].movieTitle
                holder.view.tvMovieOverview.text = it[position].movieOverview
            }

            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it[position].movieId.toInt())
            holder?.view?.cvSliderToDetail?.setOnClickListener{
                Navigation.findNavController(it).navigate(action)
            }
        }

    }

    class SliderViewHolder(val view: SliderItemBinding): ViewHolder(view.root) {}
}

