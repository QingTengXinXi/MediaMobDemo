package com.media.mob.demo.tactics.info

import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.media.mob.bean.TacticsInfo
import com.media.mob.bean.TacticsType
import com.media.mob.demo.R.drawable
import com.media.mob.demo.afterTextChanged
import com.media.mob.demo.databinding.ItemTacticsInfoBinding
import com.media.mob.platform.IPlatform

class TacticsInfoViewHolder(private val viewBinding: ItemTacticsInfoBinding) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(tacticsInfo: TacticsInfo, tacticsType: TacticsType) = with(itemView) {
        when (tacticsInfo.thirdPlatformName) {
            IPlatform.PLATFORM_KS -> {
                viewBinding.ivTacticsPlatform.setImageResource(drawable.icon_demo_logo_kuaishou)
            }
            IPlatform.PLATFORM_CSJ -> {
                viewBinding.ivTacticsPlatform.setImageResource(drawable.icon_demo_logo_chuanshanjia)
            }
            IPlatform.PLATFORM_BQT -> {
                viewBinding.ivTacticsPlatform.setImageResource(drawable.icon_demo_logo_baiqingteng)
            }
            IPlatform.PLATFORM_YLH -> {
                viewBinding.ivTacticsPlatform.setImageResource(drawable.icon_demo_logo_youlianghui)
            }
        }

        viewBinding.tvTacticsSlotId.text = tacticsInfo.thirdSlotId

        val textWatcher = viewBinding.etTacticsInfoValue.tag as TextWatcher?

        if (textWatcher != null) {
            viewBinding.etTacticsInfoValue.removeTextChangedListener(textWatcher)
        }

        when (tacticsType) {
            TacticsType.TYPE_WEIGHT -> {
                viewBinding.clTacticsInfoConfig.visibility = View.VISIBLE
                viewBinding.tvTacticsInfoKey.text = "权重:"
                viewBinding.etTacticsInfoValue.setText("${tacticsInfo.tacticsInfoWeight}")
            }
            TacticsType.TYPE_PARALLEL -> {
                viewBinding.clTacticsInfoConfig.visibility = View.VISIBLE
                viewBinding.tvTacticsInfoKey.text = "序列:"
                viewBinding.etTacticsInfoValue.setText("${tacticsInfo.tacticsInfoSequence}")
            }
            else -> {
                viewBinding.clTacticsInfoConfig.visibility = View.INVISIBLE
            }
        }

        viewBinding.etTacticsInfoValue.setTextIsSelectable(false)

        viewBinding.etTacticsInfoValue.afterTextChanged { result ->
            if (tacticsType == TacticsType.TYPE_WEIGHT) {
                if (result.isNotEmpty()) {
                    tacticsInfo.tacticsInfoWeight = result.toInt()
                } else {
                    tacticsInfo.tacticsInfoWeight = 10
                }
            } else if (tacticsType == TacticsType.TYPE_PARALLEL) {
                if (result.isNotEmpty()) {
                    tacticsInfo.tacticsInfoSequence = result.toInt()
                } else {
                    tacticsInfo.tacticsInfoSequence = 1
                }
            }
        }

        viewBinding.etTacticsInfoValue.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewBinding.etTacticsInfoValue.clearFocus()
                true
            }
            false
        }
    }
}