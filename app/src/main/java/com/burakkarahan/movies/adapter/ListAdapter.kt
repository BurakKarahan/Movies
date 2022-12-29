package com.burakkarahan.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burakkarahan.movies.R
import com.burakkarahan.movies.databinding.ListItemBinding
import com.burakkarahan.movies.model.MovieModel
import com.burakkarahan.movies.view.HomeFragmentDirections

class ListAdapter: PagingDataAdapter<MovieModel, ListAdapter.MovieViewHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = getItem(position)!!
        movie.let {
            holder.view.tvMovieTitle.text = it.movieTitle + " (" + it.movieReleaseDate.substring(0,4) + ")"
            holder.view.tvMovieOverview.text = it.movieOverview
            holder.view.tvMovieReleaseDate.text = it.movieReleaseDate.substring(8,10) + "." + it.movieReleaseDate.substring(5,7) + "." + it.movieReleaseDate.substring(0,4)

            Glide.with(holder.itemView.context).load("https://image.tmdb.org/t/p/w500" + it.movieImagePath)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(holder.view.ivMovieImagePath)

            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.movieId.toInt())
            holder.view.llListToDetail.setOnClickListener {
                Navigation.findNavController(it).navigate(action)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    class MovieViewHolder(val view: ListItemBinding): RecyclerView.ViewHolder(view.root) {

    }

    object MovieComparator: DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            // Id is unique.
            return oldItem.movieId == newItem.movieId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }
}

