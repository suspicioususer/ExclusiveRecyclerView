package com.example.sampleapplication

enum class DataStatus(val value: Int) {

    FETCHED(0),
    LOADING(11),
    NOT_FOUND(99),
    ERROR(-1)

}