package com.media.mob.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.media.mob.bean.PositionConfig
import com.media.mob.bean.SlotConfig
import com.media.mob.bean.TacticsConfig
import com.media.mob.bean.request.SlotParams
import com.media.mob.demo.databinding.ActivityRewardVideoConfigBinding
import com.media.mob.demo.tactics.config.TacticsConfigAdapter
import com.media.mob.demo.tactics.info.TacticsInfoConfigDialog
import com.media.mob.demo.widget.SimpleItemTouchHelper
import com.media.mob.media.rewardVideo.MobRewardVideo

class RewardVideoConfigActivity : AppCompatActivity() {

    private val classTarget = RewardVideoConfigActivity::class.java.simpleName

    private var viewBinding: ActivityRewardVideoConfigBinding? = null

    private val tacticsConfigList: ArrayList<TacticsConfig> by lazy {
        ArrayList()
    }

    private val tacticsConfigAdapter: TacticsConfigAdapter by lazy {
        TacticsConfigAdapter(tacticsConfigList)
    }

    private val tacticsInfoConfigDialog: TacticsInfoConfigDialog by lazy {
        TacticsInfoConfigDialog().apply {
            confirmListener = {
                tacticsConfigList.add(it)

                tacticsConfigAdapter.notifyDataSetChanged()

                checkEmptyViewState()
            }
        }
    }

    private var rewardVideoMutePlay = false

    private var mobRewardVideo: MobRewardVideo? = null

    private val simpleItemTouchHelper: SimpleItemTouchHelper by lazy {
        SimpleItemTouchHelper(tacticsConfigAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityRewardVideoConfigBinding.inflate(layoutInflater)

        setContentView(viewBinding?.root)

        viewBinding?.ivRewardVideoConfigBack?.setOnClickListener {
            finish()
        }

        viewBinding?.scRewardVideoConfigMute?.isChecked = rewardVideoMutePlay

        viewBinding?.scRewardVideoConfigMute?.setOnCheckedChangeListener { _, isChecked ->
            rewardVideoMutePlay = isChecked
        }

        viewBinding?.tvRewardVideoConfigInsert?.setOnClickListener {
            tacticsInfoConfigDialog.insertSlotType("RewardVideo")

            if (tacticsInfoConfigDialog.isAdded) {
                tacticsInfoConfigDialog.showsDialog = true
            } else {
                tacticsInfoConfigDialog.show(
                    this.supportFragmentManager,
                    "TacticsInfoConfigDialog"
                )
            }
        }

        viewBinding?.tvRewardVideoConfigRequest?.setOnClickListener {
            if (tacticsConfigList.isNotEmpty()) {
                tacticsConfigList.forEachIndexed { index, tacticsConfig ->
                    tacticsConfig.tacticsSequence = index + 1
                }

                requestRewardVideo()
            } else {
                Toast.makeText(this, "广告位策略配置为空，请先配置广告位策略", Toast.LENGTH_LONG).show()
            }
        }

        viewBinding?.tvRewardVideoConfigShow?.setOnClickListener {
            if (mobRewardVideo != null && mobRewardVideo?.checkMediaValidity() == true) {
                mobRewardVideo?.show()
            } else {
                Toast.makeText(this, "暂无有效的激励视频广告可供展示，请点击请求激励视频广告按钮后再试！", Toast.LENGTH_LONG).show()
            }
        }

        viewBinding?.rvRewardVideoConfigResult?.adapter = tacticsConfigAdapter
        viewBinding?.rvRewardVideoConfigResult?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchHelper)

        itemTouchHelper.attachToRecyclerView(viewBinding?.rvRewardVideoConfigResult)

        checkEmptyViewState()
    }

    private fun checkEmptyViewState() {
        if (tacticsConfigList.isEmpty()) {
            viewBinding?.clRewardVideoConfigEmpty?.visibility = View.VISIBLE
            viewBinding?.rvRewardVideoConfigResult?.visibility = View.GONE
        } else {
            viewBinding?.clRewardVideoConfigEmpty?.visibility = View.GONE
            viewBinding?.rvRewardVideoConfigResult?.visibility = View.VISIBLE
        }
    }

    private fun requestRewardVideo() {
        val positionConfig = PositionConfig(
            "2-1000", "激励视频广告", false, SlotConfig("RewardVideo", tacticsConfigList)
        )

        if (mobRewardVideo != null) {
            mobRewardVideo?.destroy()
        }

        mobRewardVideo = MobRewardVideo(this@RewardVideoConfigActivity, positionConfig).apply {
            requestSuccessListener = {
                Log.e(classTarget, "激励视频广告请求成功")
                Toast.makeText(this@RewardVideoConfigActivity, "激励视频广告请求成功，您可以点击展示激励视频广告按钮进行展示！", Toast.LENGTH_LONG).show()
            }

            requestFailedListener = { code, message ->
                Log.e(classTarget, "激励视频广告请求失败: code=$code, message=$message")
                Toast.makeText(this@RewardVideoConfigActivity, "激励视频广告请求失败，Code=$code, Message=$message", Toast.LENGTH_LONG).show()
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

            mediaRewardedListener = {
                Log.e(classTarget, "激励视频广告发放奖励: $it")
            }
        }

        val slotParams = SlotParams()

        mobRewardVideo?.requestRewardVideo(slotParams)
    }
}