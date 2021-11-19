package com.media.mob.demo.tactics.check

import androidx.recyclerview.widget.RecyclerView
import com.media.mob.bean.TacticsInfo
import com.media.mob.demo.R.drawable
import com.media.mob.demo.databinding.ItemTacticsInfoCheckBinding
import com.media.mob.platform.IPlatform

class TacticsInfoCheckViewHolder(private val viewBinding: ItemTacticsInfoCheckBinding) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(tacticsInfo: TacticsInfo, checked: Boolean, checkCallback: ((TacticsInfo, Boolean) -> Unit)?) = with(itemView) {
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

        viewBinding.tvTacticsAlias.text = tacticsInfo.thirdSlotId

        viewBinding.tvTacticsCheck.setOnCheckedChangeListener(null)

        viewBinding.tvTacticsCheck.isChecked = checked

        viewBinding.tvTacticsCheck.setOnCheckedChangeListener { _, isChecked ->
            checkCallback?.invoke(tacticsInfo, isChecked)
        }
    }
}