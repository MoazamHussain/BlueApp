package com.example.blueapp.presentation.mainScreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.blueapp.R

class DashboardPagerAdapter(private val data: List<Int>) : PagerAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context
        val inflater = LayoutInflater.from(context)
        val layout =
            inflater.inflate(R.layout.dashboard_pager_layout, container, false) as LinearLayout

        val img_display_img: ImageView = layout.findViewById(R.id.img_display_img)

        val item = data[position]

        img_display_img.setImageResource(item)

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
