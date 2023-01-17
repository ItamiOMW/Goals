package com.example.goals.domain.utils.order

sealed class OrderType {

    object Ascending: OrderType()

    object Descending: OrderType()

}
