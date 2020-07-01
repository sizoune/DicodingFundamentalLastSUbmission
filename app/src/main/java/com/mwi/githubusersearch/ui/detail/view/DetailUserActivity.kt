package com.mwi.githubusersearch.ui.detail.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mwi.githubusersearch.R
import com.mwi.githubusersearch.data.api.ApiHelper
import com.mwi.githubusersearch.data.api.RetrofitBuilder
import com.mwi.githubusersearch.data.model.DetailUser
import com.mwi.githubusersearch.ui.base.ViewModelFactory
import com.mwi.githubusersearch.ui.detail.adapter.SectionsPagerAdapter
import com.mwi.githubusersearch.ui.detail.viewmodel.DetailViewModel
import com.mwi.githubusersearch.utils.Status
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.layout_error.*

class DetailUserActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(DetailViewModel::class.java)
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.setExpandedTitleColor(Color.parseColor("#00FFFFFF"))

        val username = intent.getStringExtra("username")
        username?.let {
            getDetail(it)
            val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, it)
            view_pager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(view_pager)
        }

        supportActionBar?.elevation = 0f

        btnRetry.setOnClickListener {
            username?.let {
                showError(false)
                getDetail(it)
            }
        }
    }

    private fun getDetail(username: String) {
        viewModel.getDetailUsers(username).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        showLoading(false)
                        displayResult(resource.data)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        showError(true)
                        if (resource.message != null)
                            Log.e("error", resource.message)
                    }
                    Status.LOADING -> {
                        showLoading(true)
                    }
                }
            }
        })
    }

    private fun displayResult(result: DetailUser?) {
        if (result != null) {
            Picasso.get().load(result.avatar_url).fit().into(avatar)
            if (!result.name.isNullOrEmpty())
                txtName.text = result.name
            else
                txtName.text = result.login
            if (!result.bio.isNullOrEmpty())
                txtBio.text = result.bio
            else
                txtBio.visibility = View.GONE
            if (!result.location.isNullOrEmpty())
                txtLocation.text = result.location
            else
                lyt_loc.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            lytError.visibility = View.GONE
            tabs.visibility = View.GONE
            realLyt.visibility = View.GONE
            shimmerLyt.visibility = View.VISIBLE
            shimmerLyt.startShimmer()
        } else {
            shimmerLyt.stopShimmer()
            shimmerLyt.visibility = View.GONE
            realLyt.visibility = View.VISIBLE
            tabs.visibility = View.VISIBLE
        }
    }

    private fun showError(state: Boolean) {
        if (state) {
            app_bar_layout.setExpanded(false)
            tabs.visibility = View.GONE
            view_pager.visibility = View.GONE
            lytError.visibility = View.VISIBLE
        } else {
            view_pager.visibility = View.VISIBLE
            app_bar_layout.setExpanded(true)
            collapsing_toolbar.visibility = View.VISIBLE
        }
    }


}