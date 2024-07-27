package com.example.neoapplication.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    lateinit var viewModel: VM

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        setUpViewModel()
        getIntentAndFetchData()
        setupUI()
        setupObserver()
    }

    protected abstract fun setUpViewModel(): VM

    protected abstract fun getViewBinding(): VB

    protected abstract fun setupUI()

    protected abstract fun setupObserver()

    protected abstract fun getIntentAndFetchData()

}