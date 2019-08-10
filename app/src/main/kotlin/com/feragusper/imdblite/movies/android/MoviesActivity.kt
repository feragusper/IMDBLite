package com.feragusper.imdblite.movies.android

import android.content.Context
import android.content.Intent
import com.feragusper.imdblite.common.android.BaseActivity

class MoviesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }

    override fun fragment() = MoviesFragment()
}
