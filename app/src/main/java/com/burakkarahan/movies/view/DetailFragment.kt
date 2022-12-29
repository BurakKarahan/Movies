package com.burakkarahan.movies.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.burakkarahan.movies.databinding.FragmentDetailBinding
import com.burakkarahan.movies.paging.DetailViewModelFactory
import com.burakkarahan.movies.service.RetrofitAPI
import com.burakkarahan.movies.viewmodel.DetailViewModel
import com.burakkarahan.movies.service.Repository

class DetailFragment : Fragment() {

    private val TAG = "Detail"
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: DetailViewModel
    private val retrofitService = RetrofitAPI.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, DetailViewModelFactory(Repository(retrofitService))).get(DetailViewModel::class.java)

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreate: $it")

            Glide.with(view.context).load("https://image.tmdb.org/t/p/w500" + it.movieImge)
                .centerCrop()
                .into(binding.ivMovieImage)

            binding.tvMovieTitle.text = it.movieTitle
            binding.tvMovieContent.text = it.movieOverview
            binding.tvVoteAverage.text = it.movieVoteAverage.substring(0,3)
            binding.tvMovieYear.text = it.movieReleaseDate.substring(8,10) + "." + it.movieReleaseDate.substring(5,7) + "." + it.movieReleaseDate.substring(0,4)

        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {})
        viewModel.getDetailMovies(requireArguments().getInt("movieId"))

    }
}