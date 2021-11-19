package com.media.mob.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.media.mob.bean.PositionConfig
import com.media.mob.bean.SlotConfig
import com.media.mob.bean.TacticsConfig
import com.media.mob.demo.databinding.ActivitySplashConfigBinding
import com.media.mob.demo.tactics.config.TacticsConfigAdapter
import com.media.mob.demo.tactics.info.TacticsInfoConfigDialog
import com.media.mob.demo.widget.SimpleItemTouchHelper

class SplashConfigActivity : AppCompatActivity() {

    private var viewBinding: ActivitySplashConfigBinding? = null

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

    private var splashFullScreenShow = false

    private var splashLimitClickArea = false

    private val simpleItemTouchHelper: SimpleItemTouchHelper by lazy {
        SimpleItemTouchHelper(tacticsConfigAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivitySplashConfigBinding.inflate(layoutInflater)

        setContentView(viewBinding?.root)

        viewBinding?.ivSplashConfigBack?.setOnClickListener {
            finish()
        }

        viewBinding?.scSplashConfigFullScreen?.isChecked = splashFullScreenShow

        viewBinding?.scSplashConfigFullScreen?.setOnCheckedChangeListener { _, isChecked ->
            splashFullScreenShow = isChecked
        }

        viewBinding?.scSplashConfigLimitClickArea?.isChecked = splashLimitClickArea

        viewBinding?.scSplashConfigLimitClickArea?.setOnCheckedChangeListener { _, isChecked ->
            splashLimitClickArea = isChecked
        }

        viewBinding?.tvSplashConfigInsert?.setOnClickListener {
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

        viewBinding?.tvSplashConfigRequest?.setOnClickListener {
            if (tacticsConfigList.isNotEmpty()) {
                tacticsConfigList.forEachIndexed { index, tacticsConfig ->
                    tacticsConfig.tacticsSequence = index + 1
                }

                val positionConfig = PositionConfig(
                    "1-1000", "开屏广告", false, SlotConfig("Splash", tacticsConfigList)
                )

                val intent = Intent(this@SplashConfigActivity, SplashActivity::class.java)
                intent.putExtra("PositionConfig", positionConfig)
                intent.putExtra("SplashFullScreenShow", splashFullScreenShow)
                intent.putExtra("SplashLimitClickArea", splashLimitClickArea)

                startActivity(intent)

            } else {
                Toast.makeText(this, "广告位策略配置为空，请先配置广告位策略", Toast.LENGTH_LONG).show()
            }
        }

        viewBinding?.rvSplashConfigResult?.adapter = tacticsConfigAdapter
        viewBinding?.rvSplashConfigResult?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchHelper)

        itemTouchHelper.attachToRecyclerView(viewBinding?.rvSplashConfigResult)

        checkEmptyViewState()
    }

    private fun checkEmptyViewState() {
        if (tacticsConfigList.isEmpty()) {
            viewBinding?.clSplashConfigEmpty?.visibility = View.VISIBLE
            viewBinding?.rvSplashConfigResult?.visibility = View.GONE
        } else {
            viewBinding?.clSplashConfigEmpty?.visibility = View.GONE
            viewBinding?.rvSplashConfigResult?.visibility = View.VISIBLE
        }
    }
}