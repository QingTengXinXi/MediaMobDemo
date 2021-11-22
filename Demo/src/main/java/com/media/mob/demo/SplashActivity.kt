package com.media.mob.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.media.mob.bean.PositionConfig
import com.media.mob.bean.request.SlotParams
import com.media.mob.demo.databinding.ActivitySplashBinding
import com.media.mob.media.view.splash.MobSplash
import com.media.mob.platform.IPlatform

class SplashActivity : AppCompatActivity() {

    private val classTarget = SplashActivity::class.java.simpleName

    private var viewBinding: ActivitySplashBinding? = null

    private var mobSplash: MobSplash? = null

    private lateinit var splashHandler: SplashHandler

    private var splashFullScreenShow = false
    private var splashLimitClickArea = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(viewBinding?.root)


        val positionConfig : PositionConfig? = intent.getSerializableExtra("PositionConfig") as PositionConfig?

        splashFullScreenShow = intent.getBooleanExtra("SplashFullScreenShow", false)
        splashLimitClickArea = intent.getBooleanExtra("SplashLimitClickArea", false)

        if (positionConfig == null) {
            Toast.makeText(this, "广告位配置异常，请重新配置广告位策略", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        splashHandler = SplashHandler(this)

        handlePositionConfig(positionConfig)
        delayedStartMainActivity(5000)
    }

    override fun onDestroy() {
        super.onDestroy()

        mobSplash?.destroy()
        mobSplash = null
    }

    override fun onBackPressed() {

    }

    private fun handlePositionConfig(positionConfig: PositionConfig) {
        if (viewBinding?.clSplashContainer != null) {
            mobSplash = MobSplash(this@SplashActivity, positionConfig).apply {
                requestSuccessListener = {
                    splashHandler.removeCallbacksAndMessages(null)

                    Log.e(classTarget, "开屏广告请求成功")

                    viewBinding?.clSplashContainer?.let {
                        mobSplash?.show(it)
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
            slotParams.splashFullScreenShow = splashFullScreenShow
            slotParams.splashRequestTimeOut = 4000
            slotParams.splashLimitClickArea = splashLimitClickArea

            /**
             * 京准通开屏广告的宽高比有限制，这里使用16:9的尺寸
             */
            val viewAcceptedWidth = this.resources.displayMetrics.widthPixels.toFloat()
            val viewAcceptedHeight = viewAcceptedWidth * 16.0F / 9.0F

            slotParams.mediaAcceptedWidth = viewAcceptedWidth.transformDiPixels()
            slotParams.mediaAcceptedHeight = viewAcceptedHeight.transformDiPixels()

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