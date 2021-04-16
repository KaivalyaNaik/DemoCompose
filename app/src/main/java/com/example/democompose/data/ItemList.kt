package com.example.democompose.data

import com.example.democompose.R

object ItemList {
    val list = listOf<Item>(
        Item(name = "Laptop", price = "Rs.50,000", image = R.drawable.laptop),
        Item(name = "Boots", price = "Rs.10,000", image = R.drawable.boots),
        Item(name = "Backpack", price = "Rs.1000", image = R.drawable.backpack),
        Item(name = "Shirt", price = "Rs.1500", image = R.drawable.shirt),
        Item(name = "WaterBottle", price = "Rs.500", image = R.drawable.waterbottle),
    )
}