package com.example.sampleapplication.model

data class DataResponse(val info: Information? = null,
                        val results: List<Result>? = null) {
}