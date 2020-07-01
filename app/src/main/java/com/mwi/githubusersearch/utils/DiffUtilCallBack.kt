package com.mwi.githubusersearch.utils

import androidx.recyclerview.widget.DiffUtil
import com.mwi.githubusersearch.data.model.Item

class DiffUtilCallBack : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.login == newItem.login
    }
}