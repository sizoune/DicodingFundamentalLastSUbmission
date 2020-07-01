package com.mwi.githubusersearch.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mwi.githubusersearch.data.repository.DetailRepository
import com.mwi.githubusersearch.utils.Resource
import kotlinx.coroutines.Dispatchers

class DetailViewModel(private val detailRepository: DetailRepository) : ViewModel() {

    fun getDetailUsers(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = detailRepository.getDetailUser(username)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}