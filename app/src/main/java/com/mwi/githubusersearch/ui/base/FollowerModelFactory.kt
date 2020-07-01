package com.mwi.githubusersearch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwi.githubusersearch.ui.detail.viewmodel.FragmentViewModel

class FollowerModelFactory(private val username: String, private val state: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FragmentViewModel(username, state) as T
    }


}