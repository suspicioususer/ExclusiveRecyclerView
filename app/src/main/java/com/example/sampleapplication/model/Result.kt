package com.example.sampleapplication.model

import com.example.exclusiverecyclerview.model.DataProcessor

class Result(val name: String? = null,
                  val status: String? = null,
                  val image: String? = null,
                  val location: Location? = null): DataProcessor {

    override fun getCharacterName(): String? {
        return this.name
    }

    override fun getCharacterStatus(): String? {
        return this.status
    }

    override fun getCharacterImageURL(): String? {
        return this.image
    }

    override fun getCharacterLocation(): String? {
        return this.location?.name
    }
}