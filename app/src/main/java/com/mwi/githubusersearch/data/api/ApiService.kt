package com.mwi.githubusersearch.data.api

import com.mwi.githubusersearch.data.model.DetailUser
import com.mwi.githubusersearch.data.model.Item
import com.mwi.githubusersearch.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    @Headers("Authorization: token 0dfb7f19a751b3101c75cc8cd837e0d32dffd76d")
    suspend fun findUsers(@Query("q") username: String): SearchResponse

    @GET("/users/{username}")
    @Headers("Authorization: token 0dfb7f19a751b3101c75cc8cd837e0d32dffd76d")
    suspend fun getDetail(@Path("username") username: String): DetailUser

    @GET("/users/{username}/followers")
    @Headers("Authorization: token 0dfb7f19a751b3101c75cc8cd837e0d32dffd76d")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Query("page") page: Int
    ): List<Item>

    @GET("/users/{username}/following")
    @Headers("Authorization: token 0dfb7f19a751b3101c75cc8cd837e0d32dffd76d")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Query("page") page: Int
    ): List<Item>
}