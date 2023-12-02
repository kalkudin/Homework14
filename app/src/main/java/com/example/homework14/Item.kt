package com.example.homework14

data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val type: ItemType
){
    enum class ItemType {
        CAT,
        DOG
    }
}