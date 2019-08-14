package com.feragusper.imdblite.movies.android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.feragusper.imdblite.movies.android.fragment.FavoriteMoviesFragment
import com.feragusper.imdblite.movies.android.fragment.LatestMoviesFragment

class MoviesAndFavoritesPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LatestMoviesFragment()
            1 -> FavoriteMoviesFragment()
            else -> throw Exception("Invalid position: $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            // TODO Move this to resources, and probably have a unique pair of values wrapped in a single object that contains the fragment to be created and the title of the tab
            0 -> "Latest"
            1 -> "Favorites"
            else -> throw Exception("Invalid position: $position")
        }
    }
}
