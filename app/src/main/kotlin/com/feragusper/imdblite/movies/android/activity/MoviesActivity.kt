package com.feragusper.imdblite.movies.android.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.feragusper.imdblite.R
import com.feragusper.imdblite.movies.android.adapter.MoviesAndFavoritesPagerAdapter
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.toolbar.*

class MoviesActivity : AppCompatActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movies)

        val fragmentAdapter = MoviesAndFavoritesPagerAdapter(supportFragmentManager)
        main_view_pager.adapter = fragmentAdapter

        main_tabs.setupWithViewPager(main_view_pager)

        setSupportActionBar(toolbar as Toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movies_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }
}
