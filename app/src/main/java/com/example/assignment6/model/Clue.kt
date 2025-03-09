/* Assignment 6
   Clue.kt
   Nils Streedain | streedan@oregonstate.com
   CS 492 | Oregon State University
*/
package com.example.assignment6.model

data class Clue(
    val id: Int,
    val clueText: String,
    val hint: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)