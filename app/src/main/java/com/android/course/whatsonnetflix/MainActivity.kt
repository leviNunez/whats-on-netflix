package com.android.course.whatsonnetflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.android.course.whatsonnetflix.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isHome = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigation()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                true
            }
            else -> {
                onBackPressed()
                true
            }
        }
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.findItem(R.id.action_search)
        item?.let { it.isVisible = isHome }
        return true
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)


        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val actionBar = supportActionBar ?: return@addOnDestinationChangedListener
            if (destination.id != R.id.homeFragment) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                isHome = false
                invalidateOptionsMenu()
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false)
                isHome = true
                invalidateOptionsMenu()
            }
        }
    }

}
