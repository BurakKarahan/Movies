package com.burakkarahan.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burakkarahan.movies.model.MovieDetailModel
import com.burakkarahan.movies.service.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel constructor(private val mainRepository: Repository) : ViewModel() {

    val movieList = MutableLiveData<MovieDetailModel>()
    val errorMessage = MutableLiveData<String>()

    fun getDetailMovies(id: Int) {
        val response = mainRepository.getDetailMovies(id)
        response?.enqueue(object : Callback<MovieDetailModel> {
            override fun onResponse(call: Call<MovieDetailModel>, response: Response<MovieDetailModel>) {
                movieList.postValue(response.body())
            }
            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}