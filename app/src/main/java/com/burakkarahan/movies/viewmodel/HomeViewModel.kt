package com.burakkarahan.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.burakkarahan.movies.model.MovieMainModel
import com.burakkarahan.movies.model.MovieModel
import com.burakkarahan.movies.service.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel constructor(private val mainRepository: Repository) : ViewModel() {

    val mtNowPlayingList = MutableLiveData<MovieMainModel>()
    val NowPlayingErrorMessage = MutableLiveData<String>()
    fun vmNowPlaying() {
        val response = mainRepository.getNowPlaying()
        response.let {
            it.enqueue(object : Callback<MovieMainModel> {
                override fun onResponse(call: Call<MovieMainModel>, response: Response<MovieMainModel>) {
                    mtNowPlayingList.postValue(response.body())
                }
                override fun onFailure(call: Call<MovieMainModel>, t: Throwable) {
                    NowPlayingErrorMessage.postValue(t.message)
                }
            })
        }
    }

    val errorMessage = MutableLiveData<String>()
    fun getMovieList(): LiveData<PagingData<MovieModel>> {
        return mainRepository.getAllMovies().cachedIn(viewModelScope)
    }
}

