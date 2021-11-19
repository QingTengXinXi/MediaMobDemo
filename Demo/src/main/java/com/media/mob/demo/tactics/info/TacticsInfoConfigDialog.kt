package com.media.mob.demo.tactics.info

import android.app.Service
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.media.mob.bean.TacticsConfig
import com.media.mob.bean.TacticsInfo
import com.media.mob.bean.TacticsType
import com.media.mob.demo.R
import com.media.mob.demo.R.style
import com.media.mob.demo.databinding.DialogTacticsInfoConfigBinding
import com.media.mob.demo.tactics.check.TacticsInfoCheckDialog
import com.media.mob.demo.widget.SimpleItemTouchHelper

class TacticsInfoConfigDialog : DialogFragment() {

    private lateinit var viewBinding: DialogTacticsInfoConfigBinding

    private val tacticsInfoList: ArrayList<TacticsInfo> by lazy {
        ArrayList()
    }

    private var tacticsConfig: TacticsConfig? = null

    private val tacticsInfoAdapter: TacticsInfoAdapter by lazy {
        TacticsInfoAdapter(tacticsInfoList, TacticsType.TYPE_WEIGHT)
    }

    private val simpleItemTouchHelper: SimpleItemTouchHelper by lazy {
        SimpleItemTouchHelper(tacticsInfoAdapter)
    }

    private val tacticsInfoCheckDialog: TacticsInfoCheckDialog by lazy {
        TacticsInfoCheckDialog().apply {
            confirmListener = {
                tacticsInfoList.clear()
                tacticsInfoList.addAll(it)

                tacticsConfig?.tacticsInfoList?.clear()
                tacticsConfig?.tacticsInfoList?.addAll(tacticsInfoList)

                checkEmptyViewState()

                tacticsInfoAdapter.notifyDataSetChanged()
            }
        }
    }

    var confirmListener: ((TacticsConfig) -> Unit)? = null

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

        viewBinding = DialogTacticsInfoConfigBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tacticsInfoList.clear()

        tacticsConfig = TacticsConfig(
            TacticsType.TYPE_WEIGHT,
            1,
            arrayListOf()
        )
        tacticsConfig?.tacticsPriorityReturn = false

        viewBinding.clTacticsConfigResult.setOnClickListener {
            hideSoftInputFromWindow()
        }

        viewBinding.ivTacticsConfigClose.setOnClickListener {
            dismissAllowingStateLoss()
        }

        viewBinding.tvTacticsConfigConfirm.setOnClickListener {
            if (tacticsConfig != null && tacticsConfig?.tacticsInfoList?.isNotEmpty() == true) {
                tacticsConfig?.let {
                    if (it.tacticsInfoList.isNotEmpty()) {
                        if (it.tacticsInfoList.size > 1) {
                            if (it.tacticsType == TacticsType.TYPE_WEIGHT) {
                                it.tacticsInfoList.sortWith { first, second ->
                                    ((first?.tacticsInfoWeight ?: 10) - (second?.tacticsInfoWeight ?: 10))
                                }
                            } else if (it.tacticsType == TacticsType.TYPE_PARALLEL) {
                                it.tacticsInfoList.sortWith { first, second ->
                                    ((first?.tacticsInfoSequence ?: 1) - (second?.tacticsInfoSequence ?: 1))
                                }
                            }
                        }

                        confirmListener?.invoke(it)

                        dismissAllowingStateLoss()
                    }
                }
            } else {
                Toast.makeText(context, "广告位策略配置没有策略信息，请先添加策略信息", Toast.LENGTH_LONG).show()
            }
        }

        viewBinding.rbTacticsConfigWeight.isChecked = true
        simpleItemTouchHelper.insertLongPressDragEnable(false)

        tacticsInfoAdapter.insertTacticsType(TacticsType.TYPE_WEIGHT)

        viewBinding.rgTacticsConfigType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_tactics_config_weight -> {
                    tacticsConfig?.tacticsType = TacticsType.TYPE_WEIGHT
                    tacticsConfig?.tacticsPriorityReturn = false

                    tacticsInfoAdapter.insertTacticsType(TacticsType.TYPE_WEIGHT)
                    simpleItemTouchHelper.insertLongPressDragEnable(false)

                    viewBinding.clTacticsConfigOther.visibility = View.GONE
                }
                R.id.rb_tactics_config_priority -> {
                    tacticsConfig?.tacticsType = TacticsType.TYPE_PRIORITY

                    tacticsInfoAdapter.insertTacticsType(TacticsType.TYPE_PRIORITY)
                    simpleItemTouchHelper.insertLongPressDragEnable(true)

                    viewBinding.clTacticsConfigOther.visibility = View.VISIBLE
                }
                R.id.rb_tactics_config_parallel -> {
                    tacticsConfig?.tacticsType = TacticsType.TYPE_PARALLEL
                    tacticsConfig?.tacticsPriorityReturn = false

                    tacticsInfoAdapter.insertTacticsType(TacticsType.TYPE_PARALLEL)
                    simpleItemTouchHelper.insertLongPressDragEnable(false)

                    viewBinding.clTacticsConfigOther.visibility = View.GONE
                }
            }
        }

        viewBinding.scTacticsConfigPriorityReturn.isChecked = tacticsConfig?.tacticsPriorityReturn ?: false
        viewBinding.scTacticsConfigPriorityReturn.setOnCheckedChangeListener { _, isChecked ->
            tacticsConfig?.tacticsPriorityReturn = isChecked
        }

        viewBinding.tvTacticsConfigInsert.setOnClickListener {

            tacticsInfoCheckDialog.insertSlotType("Splash")
            tacticsInfoCheckDialog.insertCheckedTacticsInfoList(tacticsInfoList)

            if (tacticsInfoCheckDialog.isAdded) {
                tacticsInfoCheckDialog.showsDialog = true
            } else {
                tacticsInfoCheckDialog.show(this.childFragmentManager, "SlotInfoDialog")
            }
        }

        viewBinding.rvTacticsConfigList.adapter = tacticsInfoAdapter
        viewBinding.rvTacticsConfigList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchHelper)

        itemTouchHelper.attachToRecyclerView(viewBinding.rvTacticsConfigList)

        checkEmptyViewState()
    }

    private fun hideSoftInputFromWindow() {
        val inputMethodManager = this.context?.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(viewBinding.clTacticsConfigResult.windowToken, 0)
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

    override fun onSaveInstanceState(outState: Bundle) {

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        viewBinding.rgTacticsConfigType.setOnCheckedChangeListener(null)
        viewBinding.rbTacticsConfigWeight.isChecked = true
    }

    private fun checkEmptyViewState() {
        if (tacticsInfoList.isEmpty()) {
            viewBinding.clTacticsConfigEmpty.visibility = View.VISIBLE
            viewBinding.rvTacticsConfigList.visibility = View.GONE
        } else {
            viewBinding.clTacticsConfigEmpty.visibility = View.GONE
            viewBinding.rvTacticsConfigList.visibility = View.VISIBLE
        }
    }
}
