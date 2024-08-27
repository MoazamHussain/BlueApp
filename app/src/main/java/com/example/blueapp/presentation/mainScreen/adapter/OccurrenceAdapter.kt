package com.example.blueapp.presentation.mainScreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OccurrenceAdapter(private val occurrences: List<String>) :
    RecyclerView.Adapter<OccurrenceAdapter.OccurrenceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccurrenceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return OccurrenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: OccurrenceViewHolder, position: Int) {
        holder.bind(occurrences[position])
    }

    override fun getItemCount(): Int = occurrences.size

    inner class OccurrenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(occurrence: String) {
            textView.text = occurrence
        }
    }
}

