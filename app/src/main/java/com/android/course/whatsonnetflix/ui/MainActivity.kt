package com.android.course.whatsonnetflix.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchBar: LinearLayout
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        searchBar = binding.searchBar
        searchView = binding.searchView

        setupActionBar()
        setupSearchView()
    }

    private fun setupActionBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration =
            AppBarConfiguration.Builder(R.id.homeFragment, R.id.welcomeScreenFragment).build()
        setupActionBarWithNavController(navController, appBarConfiguration)

        searchBar.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToSearchableFragment())
        }
        configureDestinationChangedListener()
    }

    private fun configureDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    searchBar.visibility = View.VISIBLE
                    searchView.visibility = View.GONE
                    searchView.setQuery("", false)
                }
                R.id.searchableFragment -> {
                    searchBar.visibility = View.GONE
                    searchView.visibility = View.VISIBLE
                }
                R.id.searchResultsFragment -> {
                    searchBar.visibility = View.GONE
                    searchView.visibility = View.VISIBLE
                    searchView.isFocusable = false
                    searchView.clearFocus()
                }
                else -> {
                    searchBar.visibility = View.GONE
                    searchView.visibility = View.GONE
                }
            }
        }
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextFocusChangeListener { _, focused ->
            val isSearchResultsFragment =
                navController.currentDestination?.id == R.id.searchResultsFragment
            if (focused && isSearchResultsFragment) {
                navController.navigate(SearchResultsFragmentDirections.actionSearchResultsFragmentToSearchableFragment())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}
