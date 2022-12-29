package com.burakkarahan.movies.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import cn.pedant.SweetAlert.SweetAlertDialog
import com.burakkarahan.movies.adapter.ListAdapter
import com.burakkarahan.movies.adapter.SliderAdapter
import com.burakkarahan.movies.databinding.FragmentHomeBinding
import com.burakkarahan.movies.paging.HomeViewModelFactory
import com.burakkarahan.movies.service.Repository
import com.burakkarahan.movies.service.RetrofitAPI
import com.burakkarahan.movies.viewmodel.HomeViewModel
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    private val listAdapter = ListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofitService = RetrofitAPI.getInstance()
        val mainRepository = Repository(retrofitService)
        binding.recyclerview.adapter = listAdapter

        viewModel = ViewModelProvider(this, HomeViewModelFactory(mainRepository)).get(HomeViewModel::class.java)

        //RecyclerView Paging and LiveData
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        listAdapter.addLoadStateListener { loadState ->

            //Show empty list
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading)
                //binding.progressDialog.isVisible = true
            else {
                //binding.progressDialog.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getMovieList().observe(viewLifecycleOwner) {
                it?.let {
                    listAdapter.submitData(lifecycle, it)
                }
            }
        }

        //Slider Image
        sliderView = binding.sliderView
        viewModel.mtNowPlayingList.observe(viewLifecycleOwner, Observer {

            sliderAdapter = SliderAdapter(it.movieList)
            sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            sliderView.setSliderAdapter(sliderAdapter)
            sliderView.scrollTimeInSec = 3
            sliderView.isAutoCycle = true
            sliderView.startAutoCycle()

        })
        viewModel.NowPlayingErrorMessage.observe(viewLifecycleOwner, Observer {})
        viewModel.vmNowPlaying()

        //Refresh
        binding.refreshLayout.setOnRefreshListener {
            listAdapter.notifyDataSetChanged()
            sliderAdapter.notifyDataSetChanged()
            Handler().postDelayed(Runnable {
                binding.refreshLayout.isRefreshing = false
            }, 2000)
        }

    }
}