package com.mwi.githubusersearch.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mwi.githubusersearch.data.model.Item
import com.mwi.githubusersearch.data.repository.FollowersingRepository
import com.mwi.githubusersearch.utils.Status
import kotlinx.coroutines.Dispatchers

class FragmentViewModel(username: String, state: String) : ViewModel() {

    private var followersingLiveData: LiveData<PagedList<Item>>
    private val dataSourceLiveData = MutableLiveData<FollowersingRepository>()

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(true)
            .build()
        followersingLiveData = initializedPagedListBuilder(config, username, state).build()
    }

    fun getFollowers(): LiveData<PagedList<Item>> =
        followersingLiveData


    fun getFollowing(): LiveData<PagedList<Item>> =
        followersingLiveData

    fun getNetworkState(): LiveData<Status> {
        return Transformations.switchMap<FollowersingRepository, Status>(
            dataSourceLiveData,
            FollowersingRepository::status
        )
    }

    fun listIsEmpty(): Boolean {
        return followersingLiveData.value?.isEmpty() ?: true
    }

    private fun initializedPagedListBuilder(
        config: PagedList.Config,
        username: String,
        state: String
    ):
            LivePagedListBuilder<Int, Item> {

        val dataSourceFactory = object : DataSource.Factory<Int, Item>() {
            override fun create(): DataSource<Int, Item> {
                val source = FollowersingRepository(Dispatchers.IO, username, state)
                dataSourceLiveData.postValue(source)
                return source
            }
        }
        return LivePagedListBuilder<Int, Item>(dataSourceFactory, config)
    }

}