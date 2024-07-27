package com.example.neoapplication.mainScreen.views

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neoapplication.R
import com.example.neoapplication.databinding.SupportBottomSheetBinding
import com.example.neoapplication.mainScreen.adapter.PilatesAdapter
import com.example.neoapplication.mainScreen.adapter.PilatesHorizontalAdapter
import com.example.neoapplication.mainScreen.adapter.YogaAdapter
import com.example.neoapplication.mainScreen.adapter.YogaHorizontalAdapter
import com.example.neoapplication.mainScreen.model.PilatesItems
import com.example.neoapplication.mainScreen.model.YogaItems
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SupportBottomSheet(val selection : Int,val yogaItemsOriginal : List<YogaItems>,
                         val pilatesItemsOriginal : List<PilatesItems>,
                         val ySize : Int,val pSize : Int) : BottomSheetDialogFragment() {

    private var adapterPilates: PilatesHorizontalAdapter? = null
    private var adapterYoga: YogaHorizontalAdapter? = null



    companion object {
        const val TAG = "MyBottomSheet"
        fun getInstance(selection : Int,yogaItemsOriginal : List<YogaItems>,
                        pilatesItemsOriginal : List<PilatesItems>,ySize : Int,pSize : Int): SupportBottomSheet {
            return SupportBottomSheet(selection ,yogaItemsOriginal,
            pilatesItemsOriginal,ySize,pSize)
        }
    }

    private lateinit var binding: SupportBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SupportBottomSheetBinding.inflate(inflater, container, false)

        if(selection > 0)
        {
            adapterPilates = PilatesHorizontalAdapter(pilatesItemsOriginal)
            binding.recyclerHorizontal.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerHorizontal.adapter = adapterPilates
            binding.txtmore.text = "+${pSize.toString()} more activities"
        }
        else
        {
            adapterYoga = YogaHorizontalAdapter(yogaItemsOriginal)
            binding.recyclerHorizontal.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerHorizontal.adapter = adapterYoga
            binding.txtmore.text = "+ ${ySize.toString()} more activities"
        }

        binding.imgClose.setOnClickListener {
            dismiss()
        }

        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { it ->
            val d = it as BottomSheetDialog

            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                behavior.peekHeight = 900
                behavior.isHideable = false

            }
        }



        return dialog
    }



    override fun getTheme(): Int {
        return R.style.NoBackgroundDialogTheme
    }
}