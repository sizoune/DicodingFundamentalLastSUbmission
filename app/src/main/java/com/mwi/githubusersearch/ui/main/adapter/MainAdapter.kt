package com.mwi.githubusersearch.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mwi.githubusersearch.R
import com.mwi.githubusersearch.data.model.Item
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_user.view.*

class MainAdapter(
    private val context: Context,
    private val dataUser: List<Item>,
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<MainAdapter.UserHolder>() {

    inner class UserHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(
            data: Item,
            clickListener: (String) -> Unit
        ) {
            with(containerView) {
                txtName.text = data.login
                Picasso.get().load(data.avatar_url).fit().into(img_photo)
                setOnClickListener { clickListener(data.login) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = dataUser.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindItem(dataUser[position], clickListener)
    }
}