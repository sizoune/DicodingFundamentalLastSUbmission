package com.mwi.githubusersearch.data.model

import com.mwi.githubusersearch.data.model.Item

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)