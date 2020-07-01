package com.mwi.githubusersearch.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mwi.githubusersearch.data.api.RetrofitBuilder
import com.mwi.githubusersearch.data.model.Item
import com.mwi.githubusersearch.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FollowersingRepository(
    coroutineContext: CoroutineContext,
    private val username: String,
    private val state: String
) :
    PageKeyedDataSource<Int, Item>() {

    private val apiInterface = RetrofitBuilder.apiService
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
    var status: MutableLiveData<Status> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        scope.launch {
            try {
                status.postValue(Status.LOADING)
                if (state == "followers") {
                    val response = apiInterface.getFollowers(username, page = 1)
                    status.postValue(Status.SUCCESS)
                    callback.onResult(response, null, 2)
                } else if (state == "following") {
                    val response = apiInterface.getFollowing(username, page = 1)
                    status.postValue(Status.SUCCESS)
                    callback.onResult(response, null, 2)
                }
            } catch (exception: Exception) {
                status.postValue(Status.ERROR)
                Log.e("PostsDataSource", "Failed to fetch data! = ${exception.localizedMessage}")
            }

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        scope.launch {
            try {
                status.postValue(Status.LOADING)
                val nextKey = params.key + 1
                if (state == "followers") {
                    val response = apiInterface.getFollowers(username, page = params.key)
                    status.postValue(Status.SUCCESS)
                    callback.onResult(response, nextKey)
                } else if (state == "following") {
                    val response = apiInterface.getFollowing(username, page = params.key)
                    status.postValue(Status.SUCCESS)
                    callback.onResult(response, nextKey)
                }
            } catch (exception: Exception) {
                status.postValue(Status.ERROR)
                Log.e("PostsDataSource", "Failed to fetch data! = ${exception.localizedMessage}")
            }

        }


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
//      no need to implement

    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}