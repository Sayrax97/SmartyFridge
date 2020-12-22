package com.team4infinity.smartyfridge.firebaseEntities

data class RecipeDTO(
        val Id :String = "",
        val Name :String = "",
        val Description :String = "",
        val NumOfPeople :Int = 0,
        val TimeRequired :Int = 0,
        val Rating :Double = 0.2,
        val Category :String = ""
){
    override fun toString(): String {
        return "RecipeDTO(Id='$Id', Name='$Name', Description='$Description', NumOfPeople=$NumOfPeople, TimeRequired=$TimeRequired, Rating=$Rating, Category='$Category')"
    }
}
