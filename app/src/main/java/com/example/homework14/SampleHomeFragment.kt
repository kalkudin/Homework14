package com.example.homework14

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework14.databinding.FragmentSampleHomeBinding

class SampleHomeFragment :
    BaseFragment<FragmentSampleHomeBinding>(FragmentSampleHomeBinding::inflate) {

    private lateinit var itemAdapter: ItemsRecycleAdapter

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun setUp() {
        setUpItemListAdapter()
    }

    override fun setUpOnClickListeners() {
        addNewItemType1()
        addNewItemType2()
        refreshPage()
    }

    private fun setUpItemListAdapter() {
        itemAdapter = ItemsRecycleAdapter()
        binding.itemsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.itemsRecyclerView.adapter = itemAdapter
        itemAdapter.submitList(viewModel.itemsStateFlow.value)
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
}