package com.id22.movieapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.id22.movieapp.R
import com.id22.movieapp.databinding.ActivityMainBinding
import com.id22.movieapp.presentation.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var barConfig: AppBarConfiguration
    private lateinit var navController: NavController

    override fun createBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarActionBar()
        setNavigationDrawer()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(barConfig) || super.onSupportNavigateUp()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.layoutToolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setNavigationDrawer() {
        barConfig = AppBarConfiguration(
            setOf(R.id.nav_home),
            bind.drawerLayout,
        )
        navController = NavHostFragment.findNavController(
            supportFragmentManager.findFragmentById(R.id.nav_main_fragment)!!,
        )

        setupActionBarWithNavController(navController, barConfig)
        bind.navView.inflateMenu(R.menu.main_menu)
        bind.navView.setCheckedItem(R.id.nav_home)
        bind.navView.setupWithNavController(navController)
    }

    fun setToolbarVisible(
        title: Any,
        isVisible: Boolean = true,
    ) {
        if (isVisible) {
            bind.layoutToolbar.appbar.visibility = View.VISIBLE
        } else {
            bind.layoutToolbar.appbar.visibility = View.GONE
        }

        if (title is Int) {
            bind.layoutToolbar.toolbarTitle.text = getString(title)
        } else {
            bind.layoutToolbar.toolbarTitle.text = title as CharSequence?
        }
    }
}
