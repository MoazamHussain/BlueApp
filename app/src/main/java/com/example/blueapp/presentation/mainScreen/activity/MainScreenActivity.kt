package com.example.blueapp.presentation.mainScreen.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.blueapp.R
import com.example.blueapp.data.remote.dto.PilatesItems
import com.example.blueapp.data.remote.dto.YogaItems
import com.example.blueapp.databinding.ActivityMainScreenBinding
import com.example.blueapp.presentation.mainScreen.adapter.DashboardPagerAdapter
import com.example.blueapp.presentation.mainScreen.adapter.PilatesAdapter
import com.example.blueapp.presentation.mainScreen.adapter.YogaAdapter
import com.example.blueapp.presentation.mainScreen.viewmodel.MainScreenViewModel
import com.example.blueapp.presentation.mainScreen.views.SupportBottomSheet
import com.example.blueapp.presentation.ui.base.BaseActivity
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


        binding.dashboardPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                selection = position

                if(position > 0)
                {
                    viewModel.getPilatesList()
                }
                else
                {

                    viewModel.getYogaList()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        binding.seacrhToolbar.edtxtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateYogaData.collect { uiState ->
                    when {
                        uiState.isLoading -> {
                        }
                        uiState.error.isNotEmpty() -> {
                        }
                        else -> {
                            renderYogaList(uiState.list.YOGA)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStatePilatesData.collect { uiState ->
                    when {
                        uiState.isLoading -> {
                        }
                        uiState.error.isNotEmpty() -> {
                        }
                        else -> {
                            renderPilatesList(uiState.list.PILATES)
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
        adapterPilates = PilatesAdapter(pilatesItems)
        binding.recyclerList.layoutManager = LinearLayoutManager(this)
        binding.recyclerList.adapter = adapterPilates
    }

    fun renderYogaList(yogaItems : ArrayList<YogaItems>)
    {
        yogaItemsOriginal = yogaItems
        adapterYoga = YogaAdapter(yogaItems)
        binding.recyclerList.layoutManager = LinearLayoutManager(this)
        binding.recyclerList.adapter = adapterYoga
    }

    private fun getFilteredAndUpdateData() {
        if(selection > 0)
        {
            val filteredItems = adapterPilates!!.getFilteredItems()
            adapterPilates!!.updateData(filteredItems)
        }
        else
        {
            val filteredItems = adapterYoga!!.getFilteredItems()
            adapterYoga!!.updateData(filteredItems)
        }

    }


    fun renderViewPager()
    {
        val dataList = ArrayList<Int>()
        dataList.add(R.drawable.yoga)
        dataList.add(R.drawable.pilates)


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
        binding.recyclerList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var isViewPagerVisible = true

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                if (dy > 0 && isViewPagerVisible) { // Scrolled up

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