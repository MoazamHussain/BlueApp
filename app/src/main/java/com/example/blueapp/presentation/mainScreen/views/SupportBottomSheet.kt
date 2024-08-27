package com.example.blueapp.presentation.mainScreen.views

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blueapp.R
import com.example.blueapp.data.remote.dto.PilatesItems
import com.example.blueapp.data.remote.dto.YogaItems
import com.example.blueapp.databinding.SupportBottomSheetBinding
import com.example.blueapp.presentation.mainScreen.adapter.OccurrenceAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SupportBottomSheet(val selection : Int, val yogaItemsOriginal : List<YogaItems>,
                         val pilatesItemsOriginal : List<PilatesItems>,
                         val ySize : Int, val pSize : Int) : BottomSheetDialogFragment() {



    companion object {
        const val TAG = "MyBottomSheet"
        fun getInstance(selection : Int, yogaItemsOriginal : List<YogaItems>,
                        pilatesItemsOriginal : List<PilatesItems>, ySize : Int, pSize : Int): SupportBottomSheet {
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
            val totalItems = pSize
            val topItems = pilatesItemsOriginal

            val occurrences = topItems.flatMap { it.PName!!.toLowerCase().toList() }
                .groupBy { it }
                .mapValues { it.value.size }
                .toList()
                .sortedByDescending { it.second }
                .take(3)

            binding.txtTotal.text = "Total number of items: $totalItems"


            val occurrenceStrings = occurrences.map { "'${it.first}' -> ${it.second}" }
            binding.recyclerList.layoutManager = LinearLayoutManager(requireContext())
            val occurrenceAdapter = OccurrenceAdapter(occurrenceStrings)
            binding.recyclerList.adapter = occurrenceAdapter

        }
        else
        {
            val totalItems =ySize
            val topItems = yogaItemsOriginal

            val occurrences = topItems.flatMap { it.YName!!.toLowerCase().toList() }
                .groupBy { it }
                .mapValues { it.value.size }
                .toList()
                .sortedByDescending { it.second }
                .take(3)

            binding.txtTotal.text = "Total number of items: $totalItems"

            val occurrenceStrings = occurrences.map { "'${it.first}' -> ${it.second}" }
            binding.recyclerList.layoutManager = LinearLayoutManager(requireContext())
            val occurrenceAdapter = OccurrenceAdapter(occurrenceStrings)
            binding.recyclerList.adapter = occurrenceAdapter
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