package com.example.goals.domain.utils.order

sealed class GoalOrder(val orderType: OrderType) {

    class Date(orderType: OrderType): GoalOrder(orderType)

    class Title(orderType: OrderType): GoalOrder(orderType)

    fun copy(orderType: OrderType): GoalOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
        }
    }

}