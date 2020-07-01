package com.mwi.githubusersearch.data.repository

import com.mwi.githubusersearch.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun findUsers(username: String) = apiHelper.findUsers(username)
}