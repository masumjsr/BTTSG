package com.mssoftinc.bttsg.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.masum.datastore.Config
import com.masum.datastore.copy
import com.mssoftinc.bttsg.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrayerPreferencesDataSource @Inject constructor(
    private val prayerPreferences: DataStore<Config>
) {
    val prayerPreferenceData = prayerPreferences.data
        .map {
            UserData(
                ad=it.ad,
                theme=it.darkMode
            )
        }


    suspend fun updateTheme(date:Int){
        prayerPreferences.updateData {
            it.copy {
                darkMode=date
            }
        }
    }


}