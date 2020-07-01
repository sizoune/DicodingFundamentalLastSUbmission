package com.mwi.githubusersearch.ui.detail.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mwi.githubusersearch.R
import com.mwi.githubusersearch.ui.detail.view.FollowersFragment
import com.mwi.githubusersearch.ui.detail.view.FollowingFragment

class SectionsPagerAdapter(
    private val mContext: Context,
    fm: FragmentManager,
    private val username: String
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0)
            fragment = FollowersFragment.newInstance(username)
        else if (position == 1)
            fragment = FollowingFragment.newInstance(username)
        return fragment!!
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}