package com.media.mob.demo.tactics.config

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.media.mob.bean.TacticsConfig
import com.media.mob.demo.databinding.ItemTasticsConfigBinding
import com.media.mob.demo.widget.ItemTouchHelperInterface
import java.util.Collections

class TacticsConfigAdapter(private val tacticsConfigList: ArrayList<TacticsConfig>) : RecyclerView.Adapter<ViewHolder>(),
    ItemTouchHelperInterface {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TacticsConfigViewHolder(
            ItemTasticsConfigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tacticsConfig = tacticsConfigList[position]

        if (holder is TacticsConfigViewHolder) {
            holder.bind(tacticsConfig, position)
        }
    }

    override fun getItemCount(): Int {
        return tacticsConfigList.size
    }

    override fun itemMove(fromPosition: Int, position: Int) {
        Collections.swap(tacticsConfigList, fromPosition, position)
        notifyItemMoved(fromPosition, position)
    }

    override fun itemDelete(position: Int) {
        tacticsConfigList.removeAt(position)
        notifyItemRemoved(position)
    }
}