package com.feragusper.imdblite.common.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.feragusper.imdblite.AndroidApplication
import com.feragusper.imdblite.common.di.ApplicationComponent
import javax.inject.Inject

/**
 * Entry point of the application. Just a transparent activity that handles what to do when the application is being launched.
 */
class RouteActivity : AppCompatActivity() {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).appComponent
    }

    @Inject
    internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.showMain(this)
    }
}
