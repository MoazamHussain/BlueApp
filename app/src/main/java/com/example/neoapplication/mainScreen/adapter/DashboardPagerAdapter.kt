package com.example.neoapplication.mainScreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.neoapplication.R

class DashboardPagerAdapter(private val data: List<String>) : PagerAdapter() {


    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context
        val inflater = LayoutInflater.from(context)
        val layout =
            inflater.inflate(R.layout.dashboard_pager_layout, container, false) as LinearLayout

        val img_display_img: ImageView = layout.findViewById(R.id.img_display_img)

        // Bind data to the layout
        val item = data[position]


        Glide.with(context)
            .load(item)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_display_img)


        container.addView(layout)
        return layout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }
}
