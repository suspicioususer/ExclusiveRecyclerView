package com.example.exclusiverecyclerview

interface ExclusiveRecyclerViewScrollCallback {

    fun loadData()

    fun checkBuffer(): Boolean

    fun checkLoad(): Boolean

}