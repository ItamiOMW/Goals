package com.example.goals.domain.utils.order

sealed class TaskOrder(val orderType: OrderType) {

    class Time(orderType: OrderType): TaskOrder(orderType)

    class Title(orderType: OrderType): TaskOrder(orderType)

    fun copy(orderType: OrderType): TaskOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Time -> Time(orderType)
        }
    }

}