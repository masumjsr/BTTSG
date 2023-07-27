package com.mssoftinc.bttsg

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.mssoftinc.bttsg.utils.ADMOB_APP_OPEN_ID
import com.mssoftinc.bttsg.utils.AppOpenAdManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(), ImageLoaderFactory{
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) {}
        FirebaseAnalytics.getInstance(this);

        AppOpenAdManager(this, ADMOB_APP_OPEN_ID)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }
}