package com.media.mob.demo.tactics.check

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.media.mob.bean.TacticsInfo
import com.media.mob.demo.databinding.ItemTacticsInfoCheckBinding

class TacticsInfoCheckAdapter(
    private val tacticsInfoList: ArrayList<TacticsInfo>,
    private val checkedTacticsInfoSet: ArrayList<TacticsInfo>,
) : RecyclerView.Adapter<ViewHolder>() {

    var checkCallback: ((TacticsInfo, Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TacticsInfoCheckViewHolder(
            ItemTacticsInfoCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tacticsInfo = tacticsInfoList[position]

        if (holder is TacticsInfoCheckViewHolder) {
            holder.bind(tacticsInfo, checkedTacticsInfoSet.contains(tacticsInfo), checkCallback)
        }
    }

    override fun getItemCount(): Int {
        return tacticsInfoList.size
    }
}