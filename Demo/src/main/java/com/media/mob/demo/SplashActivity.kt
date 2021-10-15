package com.media.mob.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.media.mob.bean.PositionConfig
import com.media.mob.bean.SlotConfig
import com.media.mob.bean.TacticsConfig
import com.media.mob.bean.TacticsInfo
import com.media.mob.bean.TacticsType
import com.media.mob.bean.request.SlotParams
import com.media.mob.demo.databinding.ActivitySplashBinding
import com.media.mob.media.view.splash.MobSplash
import com.media.mob.platform.IPlatform

class SplashActivity : AppCompatActivity() {

    private val classTarget = SplashActivity::class.java.simpleName

    private var viewBinding: ActivitySplashBinding? = null

    private var mobSplash: MobSplash? = null

    private lateinit var splashHandler: SplashHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(viewBinding?.root)

        splashHandler = SplashHandler(this)

        val positionConfig = PositionConfig(
            "1-1000", "开屏广告", false, SlotConfig(
                "Splash", arrayListOf(
                    TacticsConfig(
                        TacticsType.TYPE_WEIGHT,
                        arrayListOf(
                            TacticsInfo(10, "cd5b6b54", "7421681", IPlatform.PLATFORM_BQT),
                            TacticsInfo(45, "1111543873", "7021586070555663", IPlatform.PLATFORM_YLH),
                            TacticsInfo(45, "5152507", "887486168", IPlatform.PLATFORM_CSJ),
                        )
                    )
                )
            )
        )

        handlePositionConfig(positionConfig)
        delayedStartMainActivity(5000)
    }

    override fun onDestroy() {
        super.onDestroy()

        mobSplash?.destroy()
        mobSplash = null
    }

    private fun handlePositionConfig(positionConfig: PositionConfig) {
        if (viewBinding?.clSplashContainer != null) {
            mobSplash = MobSplash(this@SplashActivity, positionConfig).apply {
                requestSuccessListener = {
                    splashHandler.removeCallbacksAndMessages(null)

                    Log.e(classTarget, "开屏广告请求成功")

                    if (this.platformName == IPlatform.PLATFORM_CSJ) {
                        if (this.parent != null && this.parent is ViewGroup) {
                            (this.parent as ViewGroup).removeView(this)
                        }

                        if (this.parent == null) {
                            viewBinding?.clSplashContainer?.addView(this)
                        } else {
                            delayedStartMainActivity(1000)
                        }
                    }
                }

                requestFailedListener = { code, message ->
                    Log.e(classTarget, "开屏广告请求失败: code=$code, message=$message")

                    delayedStartMainActivity(1000)
                }

                mediaShowListener = {
                    Log.e(classTarget, "开屏广告展示")
                }

                mediaClickListener = {
                    Log.e(classTarget, "开屏广告点击")
                }

                mediaCloseListener = {
                    Log.e(classTarget, "开屏广告关闭: $isFinishing")

                    if (!isFinishing) {
                        startMainActivity()
                    }
                }
            }

            val slotParams = SlotParams()
            slotParams.splashViewGroup = viewBinding?.clSplashContainer
            slotParams.splashFullScreen = true
            slotParams.splashRequestTimeOut = 4000
            slotParams.splashLimitClickArea = true

            /**
             * 京准通开屏广告的宽高比有限制，这里使用16:9的尺寸
             */
            val viewAcceptedWidth = this.resources.displayMetrics.widthPixels.toFloat()
            val viewAcceptedHeight = viewAcceptedWidth * 16.0F / 9.0F

            slotParams.viewAcceptedWidth = viewAcceptedWidth.transformDiPixels()
            slotParams.viewAcceptedHeight = viewAcceptedHeight.transformDiPixels()

            mobSplash?.requestSplash(slotParams)
        } else {
            startMainActivity()
        }
    }

    /**
     * 延迟跳转到主页
     */
    private fun delayedStartMainActivity(delay: Long) {
        splashHandler.removeCallbacksAndMessages(null)
        splashHandler.sendEmptyMessageDelayed(0x80, delay)
    }

    /**
     * 跳转到主页
     */
    private fun startMainActivity() {
        if (isFinishing) {
            return
        }

        splashHandler.removeCallbacksAndMessages(null)

        finish()
    }

    private class SplashHandler(var splashActivity: SplashActivity) :
        Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            when (message.what) {
                0x80 -> {
                    splashActivity.startMainActivity()
                }
            }
        }
    }
}