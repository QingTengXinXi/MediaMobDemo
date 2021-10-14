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
import com.media.mob.bean.TacticsType.TYPE_WEIGHT
import com.media.mob.bean.request.SlotParams
import com.media.mob.demo.databinding.ActivityMainBinding
import com.media.mob.media.rewardVideo.MobRewardVideo
import com.media.mob.platform.IPlatform

class MainActivity : AppCompatActivity() {

    private val classTarget = MainActivity::class.java.simpleName

    private var viewBinding : ActivityMainBinding? = null

    private var mobRewardVideo : MobRewardVideo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding?.root)

        viewBinding?.buMainSplash?.setOnClickListener {
            startActivity(Intent(this@MainActivity, SplashActivity::class.java))
        }

        viewBinding?.buMainRewardVideoRequest?.setOnClickListener {
            requestRewardVideo()
        }

        viewBinding?.buMainRewardVideoShow?.setOnClickListener {
            if (mobRewardVideo != null && mobRewardVideo?.checkValidity() == true) {
                mobRewardVideo?.show()
            } else {
                Toast.makeText(this, "暂无有效的激励视频广告可供展示，请点击请求激励视频广告按钮后再试！", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestRewardVideo() {
        val positionConfig = PositionConfig(
            "2-1000", "激励视频广告", false, SlotConfig(
                "RewardVideo", arrayListOf(
                    TacticsConfig(
                        TYPE_WEIGHT,
                        arrayListOf(
                            TacticsInfo(50, "1111543873", "2042846457377656", IPlatform.PLATFORM_YLH),
                            TacticsInfo(50, "5152507", "946871312", IPlatform.PLATFORM_CSJ),
                        )
                    )
                )
            )
        )

        mobRewardVideo = MobRewardVideo(this@MainActivity, positionConfig).apply {
            requestSuccessListener = {
                Log.e(classTarget, "激励视频广告请求成功")
                Toast.makeText(this@MainActivity, "激励视频广告请求成功，您可以点击展示激励视频广告按钮进行展示！", Toast.LENGTH_LONG).show()
            }

            requestFailedListener = { code, message ->
                Log.e(classTarget, "激励视频广告请求失败: code=$code, message=$message")
                Toast.makeText(this@MainActivity, "激励视频广告请求失败，Code=$code, Message=$message", Toast.LENGTH_LONG).show()
            }

            mediaShowListener = {
                Log.e(classTarget, "激励视频广告展示")
            }

            mediaClickListener = {
                Log.e(classTarget, "激励视频广告点击")
            }

            mediaCloseListener = {
                Log.e(classTarget, "激励视频广告关闭")
            }

            rewardedListener = {
                Log.e(classTarget, "激励视频广告发放奖励: $it")
            }
        }

        val slotParams = SlotParams()

        mobRewardVideo?.requestRewardVideo(slotParams)
    }
}