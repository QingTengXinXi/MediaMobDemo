package com.media.mob.demo.tactics.show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.media.mob.bean.TacticsInfo
import com.media.mob.bean.TacticsType
import com.media.mob.demo.databinding.ItemTacticsInfoBinding
import com.media.mob.demo.databinding.ItemTacticsInfoShowBinding
import com.media.mob.demo.widget.ItemTouchHelperInterface
import java.util.Collections

class TacticsInfoShowAdapter(private val tacticsInfoList: ArrayList<TacticsInfo>, private val tacticsType: TacticsType) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TacticsInfoShowViewHolder(
            ItemTacticsInfoShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tacticsInfo = tacticsInfoList[position]

        if (holder is TacticsInfoShowViewHolder) {
            holder.bind(tacticsInfo, tacticsType, position)
        }
    }

    override fun getItemCount(): Int {
        return tacticsInfoList.size
    }
}