package com.example.neoapplication.mainScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoapplication.R
import com.example.neoapplication.databinding.RecyclerLayoutBinding
import com.example.neoapplication.databinding.RecyclerLayoutHorizontalBinding
import com.example.neoapplication.mainScreen.model.PilatesItems

class PilatesHorizontalAdapter(private var pilatesList: List<PilatesItems>) : RecyclerView.Adapter<PilatesHorizontalAdapter.PilatesViewHolder>() {

    private var filteredItems: List<PilatesItems> = pilatesList
    private val originalList: List<PilatesItems> = pilatesList // Store the original list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PilatesViewHolder {
        val binding = RecyclerLayoutHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PilatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PilatesViewHolder, position: Int) {
        val item = pilatesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = pilatesList.size

    inner class PilatesViewHolder(private val binding: RecyclerLayoutHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PilatesItems) {
            // Load image using Glide or any other image loading library
            Glide.with(binding.imgDisp.context)
                .load(item.PImg)
                .placeholder(R.color.grey) // Placeholder image while loading
                .into(binding.imgDisp)

            binding.txttitle.text = item.PId.toString()
            binding.txtsubtitle.text = item.PName // Customize as needed
        }
    }

    // Method to filter the data
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

    // Method to get the current filtered items
    fun getFilteredItems(): List<PilatesItems> {
        return filteredItems
    }

    // Method to update the data
    fun updateData(newData: List<PilatesItems>) {
        pilatesList = newData
        filteredItems = newData
        notifyDataSetChanged()
    }
}