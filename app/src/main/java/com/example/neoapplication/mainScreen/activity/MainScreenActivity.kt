package com.example.neoapplication.mainScreen.activity

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.neoapplication.databinding.ActivityMainScreenBinding
import com.example.neoapplication.mainScreen.adapter.DashboardPagerAdapter
import com.example.neoapplication.mainScreen.adapter.PilatesAdapter
import com.example.neoapplication.mainScreen.adapter.YogaAdapter
import com.example.neoapplication.mainScreen.model.PilatesData
import com.example.neoapplication.mainScreen.model.PilatesItems
import com.example.neoapplication.mainScreen.model.YogaItems
import com.example.neoapplication.mainScreen.viewmodel.MainScreenViewModel
import com.example.neoapplication.mainScreen.views.SupportBottomSheet
import com.example.neoapplication.ui.base.BaseActivity
import com.example.neoapplication.ui.base.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenActivity : BaseActivity<MainScreenViewModel, ActivityMainScreenBinding>() {


    private var adapterPilates: PilatesAdapter? = null
    private var adapterYoga: YogaAdapter? = null
    var selection = 0
    var yogaItemsOriginal : List<YogaItems>? = emptyList()
    var pilatesItemsOriginal : List<PilatesItems>? = emptyList()
    var supportBottomSheet : SupportBottomSheet? = null

    override fun setUpViewModel(): MainScreenViewModel {
        viewModel = ViewModelProvider(this)[MainScreenViewModel::class.java]
        return viewModel
    }

    override fun getViewBinding(): ActivityMainScreenBinding {
        return ActivityMainScreenBinding.inflate(layoutInflater)
    }

    override fun setupUI() {

        renderViewPager()
        viewModel.getYogaData()

        // Add PageChangeListener
        binding.dashboardPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Handle page scrolling if needed
            }

            override fun onPageSelected(position: Int) {
                selection = position
                // Handle page selection, e.g., update UI or perform actions
                // Example: Show a Toast message
                if(position > 0)
                {
                    viewModel.getPilatesData()
                }
                else
                {
                    viewModel.getYogaData()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Handle page scroll state changes if needed
            }
        })

        // Set up EditText for search
        binding.seacrhToolbar.edtxtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filter the adapter's data
                if(selection > 0)
                {
                    adapterPilates?.filter(s.toString())
                }
                else
                {
                    adapterYoga?.filter(s.toString())
                }
                getFilteredAndUpdateData()

            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //bottom sheet
        binding.btnBottomSheet.setOnClickListener {

            supportBottomSheet = SupportBottomSheet.getInstance(
                selection,
                yogaItemsOriginal!!.take(3),
                pilatesItemsOriginal!!.take(3),
                yogaItemsOriginal!!.size,
                pilatesItemsOriginal!!.size
            )

            supportBottomSheet!!.show(supportFragmentManager, "BottomSheet.TAG")
        }

        setupViewPagerAnimation()

    }

    override fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiStateYogaData.collect {
                    when (it) {
                        is UiState.Success -> {
                            renderYogaList(it.data.YOGA)
                        }

                        is UiState.Error -> {
                            Log.e("DATAAPI", "Error collecting yoga: ${it.message}")
                        }

                        is UiState.Loading -> {
                            Log.e("DATAAPI", "Loading collecting yoga: ")
                        }
                    }

                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiStatePilatesData.collect {
                    when (it) {
                        is UiState.Success -> {
                            renderPilatesList(it.data.PILATES)
                        }

                        is UiState.Error -> {
                            Log.e("DATAAPI", "Error collecting pilates: ${it.message}")
                        }

                        is UiState.Loading -> {
                            Log.e("DATAAPI", "Loading collecting pilates: ")
                        }
                    }

                }
            }
        }

    }

    override fun getIntentAndFetchData() {

    }

    fun renderPilatesList(pilatesItems : List<PilatesItems>)
    {
        pilatesItemsOriginal = pilatesItems
        // Set up RecyclerView
        adapterPilates = PilatesAdapter(pilatesItems)
        binding.recyclerList.layoutManager = LinearLayoutManager(this)
        binding.recyclerList.adapter = adapterPilates
    }

    fun renderYogaList(yogaItems : List<YogaItems>)
    {
        yogaItemsOriginal = yogaItems
        // Set up RecyclerView
        adapterYoga = YogaAdapter(yogaItems)
        binding.recyclerList.layoutManager = LinearLayoutManager(this)
        binding.recyclerList.adapter = adapterYoga
    }

    // Example of getting filtered items and updating data
    private fun getFilteredAndUpdateData() {
        if(selection > 0)
        {
            val filteredItems = adapterPilates!!.getFilteredItems()
            // Do something with filteredItems, e.g., update the data source
            adapterPilates!!.updateData(filteredItems)
        }
        else
        {
            val filteredItems = adapterYoga!!.getFilteredItems()
            // Do something with filteredItems, e.g., update the data source
            adapterYoga!!.updateData(filteredItems)
        }

    }


    fun renderViewPager()
    {
        val dataList = ArrayList<String>()
        dataList.add("https://moazamhussain.github.io/fitnessapp/yoga.jpg")
        dataList.add("https://moazamhussain.github.io/fitnessapp/pilates.jpg")


        val adapter = DashboardPagerAdapter(dataList)


        binding.dashboardPager.adapter = adapter
        if (dataList.size == 1) {
            binding.pagerDots.setupWithViewPager(binding.dashboardPager)
            binding.pagerDots.visibility = View.GONE
        } else {
            binding.pagerDots.setupWithViewPager(binding.dashboardPager)
            binding.pagerDots.visibility = View.VISIBLE
        }
    }

    private fun setupViewPagerAnimation() {
        // Add a scroll listener to the RecyclerView
        binding.recyclerList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var isViewPagerVisible = true

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Check if the RecyclerView is scrolled up
                if (dy > 0 && isViewPagerVisible) { // Scrolled up
                    // Animate the ViewPager to become invisible
                    binding.linearViewpager.animate()
                        .translationY(-binding.linearViewpager.height.toFloat())
                        .alpha(0f)
                        .setDuration(300)
                        .withEndAction {
                            binding.linearViewpager.visibility = View.GONE
                        }
                        .start()
                    isViewPagerVisible = false
                } else if (dy < 0 && !isViewPagerVisible) { // Scrolled down
                    // Animate the ViewPager to become visible
                    binding.linearViewpager.visibility = View.VISIBLE
                    binding.linearViewpager.animate()
                        .translationY(0f)
                        .alpha(1f)
                        .setDuration(300)
                        .start()
                    isViewPagerVisible = true
                }
            }
        })
    }
}