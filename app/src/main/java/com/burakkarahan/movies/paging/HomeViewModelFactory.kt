package com.burakkarahan.movies.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.burakkarahan.movies.viewmodel.HomeViewModel
import com.burakkarahan.movies.service.Repository

class HomeViewModelFactory constructor(private val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}