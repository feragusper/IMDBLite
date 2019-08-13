package com.feragusper.imdblite.movies.android.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.feragusper.imdblite.R
import com.feragusper.imdblite.common.android.BaseActivity
import com.feragusper.imdblite.movies.android.fragment.MoviesFragment

class MoviesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }

    override fun fragment() = MoviesFragment()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movies_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }
}
