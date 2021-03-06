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
                Toast.makeText(this, "?????????????????????????????????????????????????????????", Toast.LENGTH_LONG).show()
            }
        }

        viewBinding?.tvRewardVideoConfigShow?.setOnClickListener {
            if (mobRewardVideo != null && mobRewardVideo?.checkMediaValidity() == true) {
                mobRewardVideo?.show()
            } else {
                Toast.makeText(this, "???????????????????????????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_LONG).show()
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
            "2-1000", "??????????????????", false, SlotConfig("RewardVideo", tacticsConfigList)
        )

        if (mobRewardVideo != null) {
            mobRewardVideo?.destroy()
        }

        mobRewardVideo = MobRewardVideo(this@RewardVideoConfigActivity, positionConfig).apply {
            requestSuccessListener = {
                Log.e(classTarget, "??????????????????????????????")
                Toast.makeText(this@RewardVideoConfigActivity, "?????????????????????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_LONG).show()
            }

            requestFailedListener = { code, message ->
                Log.e(classTarget, "??????????????????????????????: code=$code, message=$message")
                Toast.makeText(this@RewardVideoConfigActivity, "?????????????????????????????????Code=$code, Message=$message", Toast.LENGTH_LONG).show()
            }

            mediaShowListener = {
                Log.e(classTarget, "????????????????????????")
            }

            mediaClickListener = {
                Log.e(classTarget, "????????????????????????")
            }

            mediaCloseListener = {
                Log.e(classTarget, "????????????????????????")
            }

            mediaRewardedListener = {
                Log.e(classTarget, "??????????????????????????????: $it")
            }
        }

        val slotParams = SlotParams()

        mobRewardVideo?.requestRewardVideo(slotParams)
    }
}