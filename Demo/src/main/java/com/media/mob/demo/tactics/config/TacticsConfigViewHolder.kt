package com.media.mob.demo.tactics.config

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.media.mob.bean.TacticsConfig
import com.media.mob.bean.TacticsInfo
import com.media.mob.bean.TacticsType
import com.media.mob.demo.databinding.ItemTasticsConfigBinding
import com.media.mob.demo.tactics.show.TacticsInfoShowAdapter

class TacticsConfigViewHolder(private val viewBinding: ItemTasticsConfigBinding) : RecyclerView.ViewHolder(viewBinding.root) {

    private var tacticsInfoList = ArrayList<TacticsInfo>()

    private var tacticsType = TacticsType.TYPE_WEIGHT

    private val tacticsInfoShowAdapter: TacticsInfoShowAdapter by lazy {
        TacticsInfoShowAdapter(tacticsInfoList, tacticsType)
    }

    @SuppressLint("SetTextI18n")
    fun bind(tacticsConfig: TacticsConfig, position: Int) = with(itemView) {
        viewBinding.tvTacticsConfigType.text = when (tacticsConfig.tacticsType) {
            TacticsType.TYPE_WEIGHT -> {
                "策略类型: 权重"
            }
            TacticsType.TYPE_PRIORITY -> {
                "策略类型: 优先级"
            }
            TacticsType.TYPE_PARALLEL -> {
                "策略类型: 并行"
            }
        }

        viewBinding.tvTacticsConfigSequence.text = "序列: ${position + 1} "

        tacticsType = tacticsConfig.tacticsType

        if (tacticsConfig.tacticsInfoList.isNotEmpty()) {
            tacticsInfoList.run {
                clear()
                addAll(tacticsConfig.tacticsInfoList)
            }

            viewBinding.rvTacticsConfigList.itemAnimator?.changeDuration = 0

            viewBinding.rvTacticsConfigList.adapter = tacticsInfoShowAdapter
            viewBinding.rvTacticsConfigList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            tacticsInfoShowAdapter.notifyDataSetChanged()
        }
    }
}