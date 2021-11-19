package com.media.mob.demo.tactics.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.media.mob.bean.TacticsInfo
import com.media.mob.bean.TacticsType
import com.media.mob.demo.databinding.ItemTacticsInfoBinding
import com.media.mob.demo.widget.ItemTouchHelperInterface
import java.util.Collections

class TacticsInfoAdapter(private val tacticsInfoList: ArrayList<TacticsInfo>, private var tacticsType: TacticsType) :
    RecyclerView.Adapter<ViewHolder>(),
    ItemTouchHelperInterface {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TacticsInfoViewHolder(
            ItemTacticsInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tacticsInfo = tacticsInfoList[position]

        if (holder is TacticsInfoViewHolder) {
            holder.bind(tacticsInfo, tacticsType)
        }
    }

    override fun getItemCount(): Int {
        return tacticsInfoList.size
    }

    fun insertTacticsType(tacticsType: TacticsType) {
        if (this.tacticsType != tacticsType) {
            this.tacticsType = tacticsType

            notifyDataSetChanged()
        }
    }

    override fun itemMove(fromPosition: Int, position: Int) {
        Collections.swap(tacticsInfoList, fromPosition, position)
        notifyItemMoved(fromPosition, position)
    }

    override fun itemDelete(position: Int) {
        tacticsInfoList.removeAt(position)
        notifyItemRemoved(position)
    }
}