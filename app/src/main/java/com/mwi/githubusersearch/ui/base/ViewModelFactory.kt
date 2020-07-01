package com.mwi.githubusersearch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwi.githubusersearch.data.api.ApiHelper
import com.mwi.githubusersearch.data.repository.DetailRepository
import com.mwi.githubusersearch.data.repository.MainRepository
import com.mwi.githubusersearch.ui.detail.viewmodel.DetailViewModel
import com.mwi.githubusersearch.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(DetailRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}