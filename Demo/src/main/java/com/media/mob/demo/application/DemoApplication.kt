package com.media.mob.demo.application

import android.app.Application
import android.util.Log
import com.media.mob.MediaMob
import com.media.mob.bean.InitialParams

class DemoApplication: Application() {

    companion object {

        lateinit var application: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        val initialParams = InitialParams()
        initialParams.debug = true
        initialParams.useTextureView = true
        initialParams.allowShowNotify = true

        MediaMob.initial(this, initialParams) { result, message ->
            Log.e("DemoApplication", "聚合广告SDK初始化结果: Result=$result, Message=$message")
        }
    }
}