package com.team4infinity.smartyfridge.firebaseEntities

data class RecipeDTO(
        val Id :String,
        val Name :String,
        val Description :String,
        val NumOfPeople :Int,
        val TimeRequired :Int,
        val Category :String
)
