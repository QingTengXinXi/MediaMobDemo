package com.media.mob.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.media.mob.bean.PositionConfig
import com.media.mob.bean.SlotConfig
import com.media.mob.bean.TacticsConfig
import com.media.mob.bean.TacticsInfo
import com.media.mob.bean.TacticsType.TYPE_PARALLEL
import com.media.mob.bean.TacticsType.TYPE_WEIGHT
import com.media.mob.bean.request.SlotParams
import com.media.mob.demo.databinding.ActivityMainBinding
import com.media.mob.media.interstitial.MobInterstitial
import com.media.mob.media.rewardVideo.MobRewardVideo
import com.media.mob.platform.IPlatform

class MainActivity : AppCompatActivity() {

    private val classTarget = MainActivity::class.java.simpleName

    private var viewBinding : ActivityMainBinding? = null

    private var mobRewardVideo : MobRewardVideo? = null

    private var mobInterstitial : MobInterstitial? = null


    private val interstitial_template_CSJ = ""

    /**
     * 百青藤新模板插屏广告位
     */
    private val interstitial_template_BQT = "7765839"

    /**
     * 百青藤插屏广告位
     */
    private val interstitial_BQT = "7765840"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding?.root)

        viewBinding?.buMainSplash?.setOnClickListener {
            startActivity(Intent(this@MainActivity, SplashConfigActivity::class.java))
        }

        viewBinding?.buMainRewardVideo?.setOnClickListener {
            startActivity(Intent(this@MainActivity, RewardVideoConfigActivity::class.java))
        }

        viewBinding?.buMainInterstitialRequest?.setOnClickListener {
            requestInterstitial()
        }

        viewBinding?.buMainInterstitialShow?.setOnClickListener {
            if (mobInterstitial != null) {
                mobInterstitial?.show()
            } else {
                Toast.makeText(this, "暂无插屏广告可供展示，请点击请求插屏广告按钮后再试！", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestInterstitial() {
        val positionConfig = PositionConfig(
            "3-1000", "插屏广告", false, SlotConfig(
                "Interstitial", arrayListOf(
                    TacticsConfig(
                        TYPE_WEIGHT,
                        1,
                        arrayListOf(
                            TacticsInfo("806300001", "8063000003", IPlatform.PLATFORM_KS, 10),
                            TacticsInfo("7765839", "cd5b6b54", IPlatform.PLATFORM_BQT, 20),
                            TacticsInfo("1111543873", "1072749707937568", IPlatform.PLATFORM_YLH, 30),
                            TacticsInfo("5152507", "946916307", IPlatform.PLATFORM_CSJ, 40),
                        )
                    )
                )
            )
        )

        mobInterstitial = MobInterstitial(this@MainActivity, positionConfig).apply {
            requestSuccessListener = {
                Log.e(classTarget, "插屏广告请求成功")
                Toast.makeText(this@MainActivity, "插屏广告请求成功，您可以点击展示插屏广告按钮进行展示！", Toast.LENGTH_LONG).show()
            }

            requestFailedListener = { code, message ->
                Log.e(classTarget, "插屏广告请求失败: code=$code, message=$message")
                Toast.makeText(this@MainActivity, "插屏广告请求失败，Code=$code, Message=$message", Toast.LENGTH_LONG).show()
            }

            mediaShowListener = {
                Log.e(classTarget, "插屏广告展示")
            }

            mediaClickListener = {
                Log.e(classTarget, "插屏广告点击")
            }

            mediaCloseListener = {
                Log.e(classTarget, "插屏广告关闭")
            }
        }

        val slotParams = SlotParams()
        slotParams.interstitialFullScreenShow = true
        slotParams.interstitialNewTemplateExpress = true

        /**
         * 穿山甲模板渲染的广告比例尺寸为：1:1, 3:2, 2:3，demo中使用 2:3 的尺寸
         */
        val mediaAcceptedWidth = resources.displayMetrics.widthPixels.transformPixels() - 16 * 2
        val mediaAcceptedHeight = mediaAcceptedWidth / 2 * 3

        slotParams.mediaAcceptedWidth = mediaAcceptedWidth
        slotParams.mediaAcceptedHeight = mediaAcceptedHeight

        mobInterstitial?.requestInterstitial(slotParams)
    }
}