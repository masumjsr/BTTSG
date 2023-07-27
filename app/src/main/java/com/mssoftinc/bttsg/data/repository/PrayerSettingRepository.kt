package com.mssoftinc.bttsg.data.repository

import com.mssoftinc.bttsg.datastore.PrayerPreferencesDataSource
import javax.inject.Inject

class PrayerSettingRepository @Inject constructor(
    private val  prayerPreferenceDataSource: PrayerPreferencesDataSource
) {
    val prayerPreferenceData=prayerPreferenceDataSource.prayerPreferenceData
    suspend fun updateTheme(i:Int)=prayerPreferenceDataSource.updateTheme(i)
}