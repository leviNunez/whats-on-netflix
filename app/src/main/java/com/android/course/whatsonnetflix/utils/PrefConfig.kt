package com.android.course.whatsonnetflix.utils

import android.app.Application
import androidx.preference.PreferenceManager
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.domain.RegionModel
import javax.inject.Inject

class PrefConfig @Inject constructor(private val app: Application) {
    private val prefKey = app.getString(R.string.preference_file_key)
    private val defaultValue = app.getString(R.string.default_value)

    fun loadRegionCode(): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(app)
        return prefs.getString(prefKey, defaultValue)
    }

    fun saveRegionCode(region: RegionModel) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(app)
        prefs.edit().putString(prefKey, region.countryCode)
            .apply()
    }
}