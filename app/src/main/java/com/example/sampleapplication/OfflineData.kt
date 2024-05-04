package com.example.sampleapplication

import com.example.sampleapplication.model.Location
import com.example.sampleapplication.model.Result

object OfflineData {

    const val characterCount = 25
    const val totalPageCount = (characterCount - 1 / 20) + 1

    val list = arrayListOf<Result>().apply {
        for (i in 0 until characterCount) {
            val index = i / 3
            when (i % 3) {
                0 -> add(Result("Bartender Morty ${index + 1}", "Alive", "https://rickandmortyapi.com/api/character/avatar/473.jpeg", Location("Citadel of Ricks")))
                1 -> add(Result("Fascist Teddy Bear Rick’s Clone ${index + 1}", "Dead", "https://rickandmortyapi.com/api/character/avatar/508.jpeg", Location("Earth (Fascist Teddy Bear Dimension)")))
                2-> add(Result("Young Beth ${index + 1}", "unknown", "https://rickandmortyapi.com/api/character/avatar/824.jpeg", Location("Earth (Unknown dimension)")))
            }
        }
        //add(Result("Bartender Morty", "https://rickandmortyapi.com/api/character/avatar/473.jpeg", "Alive", Location("Citadel of Ricks")))
        //add(Result("Fascist Teddy Bear Rick’s Clone", "https://rickandmortyapi.com/api/character/avatar/508.jpeg", "Dead", Location("Earth (Fascist Teddy Bear Dimension)")))
        //add(Result("Young Beth", "https://rickandmortyapi.com/api/character/avatar/824.jpeg", "unknown", Location("Earth (Unknown dimension)")))
    }

}