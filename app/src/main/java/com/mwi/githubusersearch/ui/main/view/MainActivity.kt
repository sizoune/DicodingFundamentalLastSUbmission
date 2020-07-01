package com.mwi.githubusersearch.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mwi.githubusersearch.R
import com.mwi.githubusersearch.data.api.ApiHelper
import com.mwi.githubusersearch.data.api.RetrofitBuilder
import com.mwi.githubusersearch.data.model.SearchResponse
import com.mwi.githubusersearch.ui.base.ViewModelFactory
import com.mwi.githubusersearch.ui.detail.view.DetailUserActivity
import com.mwi.githubusersearch.ui.main.adapter.MainAdapter
import com.mwi.githubusersearch.ui.main.viewmodel.MainViewModel
import com.mwi.githubusersearch.ui.setting.SettingActivity
import com.mwi.githubusersearch.utils.Status.*
import com.mwi.githubusersearch.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var viewModel: MainViewModel
    private var keywoard = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        with(toolbar) {
            inflateMenu(R.menu.setting_menu)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.menuSetting) {
                    val intent = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(intent)
                }
                true
            }
        }

        listUser.layoutManager = LinearLayoutManager(this)
        etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard(this@MainActivity.currentFocus)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    keywoard = newText
                    launch {
                        delay(300)
                        if (it.isNotEmpty()) {
                            doSearch(it)
                        }
                    }
                }
                return true
            }

        })

        btnRetry.setOnClickListener {
            if (keywoard.isNotEmpty()) {
                doSearch(keywoard)
            } else {
                Toast.makeText(this, getString(R.string.error_search), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun doSearch(keyword: String) {
        viewModel.findUsers(keyword).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        showLoading(false)
                        showSearchResult(resource.data)
                    }
                    ERROR -> {
                        showLoading(false)
                        lytError.visibility = View.VISIBLE
                        if (resource.message != null)
                            Log.e("error", resource.message)
                    }
                    LOADING -> {
                        showLoading(true)
                    }
                }
            }
        })
    }

    private fun showSearchResult(result: SearchResponse?) {
        if (result != null) {
            if (result.items.isNotEmpty()) {
                lyt_empty_result.visibility = View.GONE
                listUser.visibility = View.VISIBLE
                listUser.adapter = MainAdapter(this, result.items) {
                    val intent = Intent(this, DetailUserActivity::class.java)
                    intent.putExtra("username", it)
                    startActivity(intent)
                }
            } else {
                Log.e("tes", "masuk")
                listUser.visibility = View.GONE
                lyt_empty_result.visibility = View.VISIBLE
            }
        } else {
            lyt_empty_result.visibility = View.VISIBLE
            listUser.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            lyt_empty_result.visibility = View.GONE
            lytError.visibility = View.GONE
            listUser.visibility = View.GONE
            loadLyt.visibility = View.VISIBLE
        } else {
            loadLyt.visibility = View.GONE
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}