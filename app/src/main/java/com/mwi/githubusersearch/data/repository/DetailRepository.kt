package com.mwi.githubusersearch.data.repository

import com.mwi.githubusersearch.data.api.ApiHelper

class DetailRepository(
    private val apiHelper: ApiHelper
) {
    suspend fun getDetailUser(username: String) = apiHelper.getDetailUser(username)
}