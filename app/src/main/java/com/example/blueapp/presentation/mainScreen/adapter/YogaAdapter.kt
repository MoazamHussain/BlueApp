package com.example.blueapp.presentation.mainScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blueapp.R
import com.example.blueapp.data.remote.dto.YogaItems
import com.example.blueapp.databinding.RecyclerLayoutBinding


class YogaAdapter(private var yogaList: List<YogaItems>) : RecyclerView.Adapter<YogaAdapter.YogaViewHolder>() {

    private var filteredItems: List<YogaItems> = yogaList
    private val originalList: List<YogaItems> = yogaList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YogaViewHolder {
        val binding = RecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YogaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YogaViewHolder, position: Int) {
        val item = yogaList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = yogaList.size

    inner class YogaViewHolder(private val binding: RecyclerLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: YogaItems) {

            binding.imgDisp.setImageResource(R.drawable.yoga)
            binding.txttitle.text = item.YId.toString()
            binding.txtsubtitle.text = item.YName
        }
    }

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


    fun getFilteredItems(): List<YogaItems> {
        return filteredItems
    }


    fun updateData(newData: List<YogaItems>) {
        yogaList = newData
        filteredItems = newData
        notifyDataSetChanged()
    }
}