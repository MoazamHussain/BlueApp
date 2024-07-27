package com.example.neoapplication.mainScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoapplication.R
import com.example.neoapplication.databinding.RecyclerLayoutBinding
import com.example.neoapplication.databinding.RecyclerLayoutHorizontalBinding
import com.example.neoapplication.mainScreen.model.PilatesItems
import com.example.neoapplication.mainScreen.model.YogaItems

class YogaHorizontalAdapter(private var yogaList: List<YogaItems>) : RecyclerView.Adapter<YogaHorizontalAdapter.YogaViewHolder>() {

    private var filteredItems: List<YogaItems> = yogaList
    private val originalList: List<YogaItems> = yogaList // Store the original list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YogaViewHolder {
        val binding = RecyclerLayoutHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YogaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YogaViewHolder, position: Int) {
        val item = yogaList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = yogaList.size

    inner class YogaViewHolder(private val binding: RecyclerLayoutHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: YogaItems) {
            // Load image using Glide or any other image loading library
            Glide.with(binding.imgDisp.context)
                .load(item.YImg)
                .placeholder(R.color.grey) // Placeholder image while loading
                .into(binding.imgDisp)

            binding.txttitle.text = item.YId.toString()
            binding.txtsubtitle.text = item.YName // Customize as needed
        }
    }

    // Method to filter the data
    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.YName?.contains(query, ignoreCase = true) == true
            }
        }
        notifyDataSetChanged()
    }

    // Method to get the current filtered items
    fun getFilteredItems(): List<YogaItems> {
        return filteredItems
    }

    // Method to update the data
    fun updateData(newData: List<YogaItems>) {
        yogaList = newData
        filteredItems = newData
        notifyDataSetChanged()
    }
}