package com.mwi.githubusersearch.ui.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mwi.githubusersearch.R
import com.mwi.githubusersearch.ui.base.FollowerModelFactory
import com.mwi.githubusersearch.ui.detail.adapter.FollowerAdapter
import com.mwi.githubusersearch.ui.detail.viewmodel.FragmentViewModel
import com.mwi.githubusersearch.utils.Status
import kotlinx.android.synthetic.main.fragment_followers.view.*

class FollowersFragment : Fragment() {

    private lateinit var viewModel: FragmentViewModel
    private val adapter = FollowerAdapter()

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.listFollowers.layoutManager = LinearLayoutManager(context)
        view.listFollowers.adapter = adapter

        if (arguments != null) {
            arguments?.let {
                val username = it.getString(ARG_USERNAME, "")
                setupViewModel(username)
                setupObserver()
            }
        }

    }

    private fun setupViewModel(username: String) {
        viewModel = ViewModelProviders.of(this, FollowerModelFactory(username, "followers"))
            .get(FragmentViewModel::class.java)
    }

    private fun setupObserver() {
        viewModel.getFollowers().observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.getNetworkState().observe(this, Observer {
            if (!viewModel.listIsEmpty()) {
                adapter.setState(it ?: Status.SUCCESS)
            } else {
                if (it == Status.ERROR){
                    Toast.makeText(context, "Failed to fetching data !", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}