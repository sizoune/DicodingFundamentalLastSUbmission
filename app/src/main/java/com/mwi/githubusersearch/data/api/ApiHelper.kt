package com.mwi.githubusersearch.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun findUsers(username: String) = apiService.findUsers(username)
    suspend fun getDetailUser(username: String) = apiService.getDetail(username)
    suspend fun getFollowers(username: String, page: Int) = apiService.getFollowers(username, page)
    suspend fun getFollowing(username: String, page: Int) = apiService.getFollowing(username, page)
}