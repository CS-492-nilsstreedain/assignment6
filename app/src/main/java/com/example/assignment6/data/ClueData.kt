/* Assignment 6
   ClueData.kt
   Nils Streedain | streedan@oregonstate.com
   CS 492 | Oregon State University
*/
package com.example.assignment6.data

import com.example.assignment6.model.Clue

object ClueData {
    val clues = listOf(
        Clue(
            id = 1,
            clueText = "Find the fountain in the heart of the city where locals gather.",
            hint = "It's often called Portland's living room.",
            description = "Pioneer Courthouse Square officially opened in 1984 and quickly became a central gathering spot for concerts and public events. Once the site of a hotel and even a parking lot, it now hosts thousands of visitors each year in downtown Portland.",
            latitude = 45.518457,
            longitude = -122.678239
        ),
        Clue(
            id = 2,
            clueText = "Seek the neon sign that guides travelers by the river.",
            hint = "Look for the famous 'White Stag' near the Burnside Bridge.",
            description = "The White Stag Sign, installed in 1940, has changed designs over the years but remains an iconic city symbol. Known for its neon stag and outline of Oregon, it greets visitors crossing the Burnside Bridge into downtown Portland.",
            latitude = 45.523302,
            longitude = -122.670269
        )
    )
}
