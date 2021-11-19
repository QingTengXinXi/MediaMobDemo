package com.media.mob.demo.tactics.show

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.media.mob.bean.TacticsInfo
import com.media.mob.bean.TacticsType
import com.media.mob.demo.R.drawable
import com.media.mob.demo.databinding.ItemTacticsInfoShowBinding
import com.media.mob.platform.IPlatform

class TacticsInfoShowViewHolder(private val viewBinding: ItemTacticsInfoShowBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(tacticsInfo: TacticsInfo, tacticsType: TacticsType, position: Int) = with(itemView) {
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

        when (tacticsType) {
            TacticsType.TYPE_WEIGHT -> {
                viewBinding.tvTacticsOther.text = "权重: ${tacticsInfo.tacticsInfoWeight}"
            }
            TacticsType.TYPE_PRIORITY -> {
                viewBinding.tvTacticsOther.text = "优先级: ${position + 1}"
            }
            TacticsType.TYPE_PARALLEL -> {
                viewBinding.tvTacticsOther.text = "序列: ${tacticsInfo.tacticsInfoSequence}"
            }
        }

    }
}