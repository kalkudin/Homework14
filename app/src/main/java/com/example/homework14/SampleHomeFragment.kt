package com.example.homework14

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework14.databinding.FragmentSampleHomeBinding
import kotlinx.coroutines.launch

class SampleHomeFragment :
    BaseFragment<FragmentSampleHomeBinding>(FragmentSampleHomeBinding::inflate) {

    private lateinit var itemAdapter: ItemsRecycleAdapter

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun setUp() {
        setUpItemListAdapter()
        setUpSwipeRefreshLayout()
    }

    override fun setUpOnClickListeners() {
        addNewItemType1()
        addNewItemType2()
        refreshPage()
    }

    private fun setUpItemListAdapter() {
        itemAdapter = ItemsRecycleAdapter { item ->
            viewModel.deleteItem(item)
        }
        binding.itemsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.itemsRecyclerView.adapter = itemAdapter

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemsStateFlow.collect { newList ->
                    itemAdapter.submitList(newList)
                }
            }
        }
    }

    private fun addNewItemType1() {
        binding.btnAddNewItem1.setOnClickListener() {
            viewModel.addItemType1()
            itemAdapter.submitList(viewModel.itemsStateFlow.value)
        }
    }

    private fun addNewItemType2() {
        binding.btnAddNewItem2.setOnClickListener() {
            viewModel.addItemType2()
            itemAdapter.submitList(viewModel.itemsStateFlow.value)
        }
    }

    private fun refreshPage() {
        binding.btnRefreshPage.setOnClickListener() {
            viewModel.resetToInitialItems()
            itemAdapter.submitList(viewModel.initialItems)
        }
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.resetToInitialItems()
            itemAdapter.submitList(viewModel.initialItems)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}