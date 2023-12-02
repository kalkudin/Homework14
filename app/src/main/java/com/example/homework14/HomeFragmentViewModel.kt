package com.example.homework14

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

class HomeFragmentViewModel : ViewModel() {

    private val _itemsStateFlow = MutableStateFlow<List<Item>>(emptyList())
    val itemsStateFlow get() = _itemsStateFlow

    val initialItems = listOf(
        Item(1, "Title 1", "Desc 1", Item.ItemType.CAT),
        Item(2, "Title 2", "Desc 2", Item.ItemType.DOG)
    )

    init {
        _itemsStateFlow.value = initialItems
    }

    fun resetToInitialItems(){
        _itemsStateFlow.value = initialItems
    }

    fun addItemType1() {
        viewModelScope.launch {
            val newItem = generateRandomItem(Item.ItemType.CAT)
            _itemsStateFlow.value = _itemsStateFlow.value + listOf(newItem)
        }
    }

    fun addItemType2() {
        viewModelScope.launch {
            val newItem = generateRandomItem(Item.ItemType.DOG)
            _itemsStateFlow.value = _itemsStateFlow.value + listOf(newItem)
        }
    }

    private fun generateRandomItem(type: Item.ItemType): Item {
        return Item(
            id = Random.nextInt(),
            title = type.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()},
            description = "${type.name} Desc",
            type = type
        )
    }
}
