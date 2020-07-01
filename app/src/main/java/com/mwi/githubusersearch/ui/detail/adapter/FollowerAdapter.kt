package com.mwi.githubusersearch.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mwi.githubusersearch.R
import com.mwi.githubusersearch.data.model.Item
import com.mwi.githubusersearch.utils.DiffUtilCallBack
import com.mwi.githubusersearch.utils.Status
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_user.view.*

class FollowerAdapter : PagedListAdapter<Item, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    private var state = Status.LOADING

    companion object {
        private const val DATA_VIEW_TYPE = 1
        private const val FOOTER_VIEW_TYPE = 2
    }

    inner class UserHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(
            data: Item
        ) {
            with(containerView) {
                txtName.text = data.login
                Picasso.get().load(data.avatar_url).fit().into(img_photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_user,
                parent,
                false
            )
            UserHolder(view)
        } else {
            val loadingView = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_item, parent, false)
            LoadingViewHolder(loadingView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            val userHolder = holder as UserHolder
            getItem(position)?.let { userHolder.bindItem(it) }
        } else {
            (holder as LoadingViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == Status.LOADING || state == Status.ERROR)
    }

    fun setState(state: Status) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}