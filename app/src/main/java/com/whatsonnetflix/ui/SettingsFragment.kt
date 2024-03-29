package com.whatsonnetflix.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.whatsonnetflix.utils.PrefConfig
import com.whatsonnetflix.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var navController: NavController
    private var regionPreference: Preference? = null

    @Inject
    lateinit var prefsConfig: PrefConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(RegionUpdateFragment.REGION_UPDATED)
            .observe(currentBackStackEntry) { regionUpdated ->
                if (regionUpdated) {
                    setRegionPreferenceSummary()
                }
            }

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        regionPreference = findPreference(getString(R.string.preference_file_key))
        setRegionPreferenceSummary()

        regionPreference?.setOnPreferenceClickListener {
            navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToRegionUpdateFragment())
            true
        }
    }

    private fun setRegionPreferenceSummary() {
        val currentRegion = prefsConfig.loadRegionCode()
        regionPreference?.summary = currentRegion
    }
}