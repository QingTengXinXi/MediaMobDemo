package com.media.mob.demo.tactics.check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.media.mob.bean.TacticsInfo
import com.media.mob.demo.R.style
import com.media.mob.demo.TacticsInfoConfigHelper
import com.media.mob.demo.databinding.DialogTacticsInfoCheckBinding

class TacticsInfoCheckDialog : DialogFragment() {

    private lateinit var viewBinding: DialogTacticsInfoCheckBinding

    private var slotType: String = ""

    private val checkedTacticsInfoSet: ArrayList<TacticsInfo> = arrayListOf()

    private val tacticsInfoList: ArrayList<TacticsInfo> by lazy {
        ArrayList()
    }

    private val tacticsInfoCheckAdapter: TacticsInfoCheckAdapter by lazy {
        TacticsInfoCheckAdapter(tacticsInfoList, checkedTacticsInfoSet).apply {
            checkCallback = { tactics, checked ->
                if (checked) {
                    checkedTacticsInfoSet.add(tactics)
                } else {
                    checkedTacticsInfoSet.remove(tactics)
                }

                if (viewBinding.rvTacticsInfoCheckList.isComputingLayout) {
                    viewBinding.rvTacticsInfoCheckList.post {
                        notifyDataSetChanged()
                    }
                } else {
                    notifyDataSetChanged()
                }
            }
        }
    }

    var confirmListener: ((ArrayList<TacticsInfo>) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, style.CustomDialogFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dialog?.window?.setWindowAnimations(style.CustomDialogFragmentAnimation)

        viewBinding = DialogTacticsInfoCheckBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.ivTacticsInfoCheckClose.setOnClickListener {
            dismissAllowingStateLoss()
        }

        viewBinding.tvTacticsInfoCheckConfirm.setOnClickListener {
            if (checkedTacticsInfoSet.isNotEmpty()) {
                confirmListener?.invoke(checkedTacticsInfoSet)
            } else {
                confirmListener?.invoke(arrayListOf())
            }

            dismissAllowingStateLoss()
        }

        viewBinding.rvTacticsInfoCheckList.adapter = tacticsInfoCheckAdapter
        viewBinding.rvTacticsInfoCheckList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()

        if (dialog != null) {
            dialog?.setCancelable(true)
            dialog?.setCanceledOnTouchOutside(true)

            if (dialog?.window != null) {
                val layoutParams = dialog?.window?.attributes
                layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams?.height = WindowManager.LayoutParams.MATCH_PARENT
                this.dialog?.window?.attributes = layoutParams
            }
        }
    }

    fun insertSlotType(slotType: String) {
        this.slotType = slotType

        changeSlotInfoCheckList()
    }

    private fun changeSlotInfoCheckList() {
        tacticsInfoList.clear()

        when (slotType) {
            "Splash" -> {
                TacticsInfoConfigHelper.splashTacticsInfoList.forEach {
                    tacticsInfoList.add(TacticsInfo(it.thirdAppId, it.thirdSlotId, it.thirdPlatformName))
                }
            }
            "RewardVideo" -> {
                TacticsInfoConfigHelper.rewardVideoTacticsInfoList.forEach {
                    tacticsInfoList.add(TacticsInfo(it.thirdAppId, it.thirdSlotId, it.thirdPlatformName))
                }
            }
            "Interstitial" -> {

            }
        }
        tacticsInfoCheckAdapter.notifyDataSetChanged()
    }

    fun insertCheckedTacticsInfoList(tacticsInfoList: ArrayList<TacticsInfo>) {
        this.checkedTacticsInfoSet.clear()

        if (tacticsInfoList.isNotEmpty()) {
            tacticsInfoList.forEach {
                checkedTacticsInfoSet.add(it)
            }

            tacticsInfoCheckAdapter.notifyDataSetChanged()
        }
    }
}