package com.example.blueapp.presentation.mainScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blueapp.R
import com.example.blueapp.data.remote.dto.PilatesItems
import com.example.blueapp.databinding.RecyclerLayoutBinding


class PilatesAdapter(private var pilatesList: List<PilatesItems>) : RecyclerView.Adapter<PilatesAdapter.PilatesViewHolder>() {

    private var filteredItems: List<PilatesItems> = pilatesList
    private val originalList: List<PilatesItems> = pilatesList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PilatesViewHolder {
        val binding = RecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PilatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PilatesViewHolder, position: Int) {
        val item = pilatesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = pilatesList.size

    inner class PilatesViewHolder(private val binding: RecyclerLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PilatesItems) {
            binding.imgDisp.setImageResource(R.drawable.pilates)
            binding.txttitle.text = item.PId.toString()
            binding.txtsubtitle.text = item.PName // Customize as needed
        }
    }

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.PName?.contains(query, ignoreCase = true) == true
            }
        }
        notifyDataSetChanged()
    }


    fun getFilteredItems(): List<PilatesItems> {
        return filteredItems
    }


    fun updateData(newData: List<PilatesItems>) {
        pilatesList = newData
        filteredItems = newData
        notifyDataSetChanged()
    }
}