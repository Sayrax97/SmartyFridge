package com.team4infinity.smartyfridge.firebaseEntities

data class RecipeDTO(
        var Id :String = "",
        var Name :String = "",
        var Description :String = "",
        var NumOfPeople :Int = 0,
        var TimeRequired :Int = 0,
        var Rating :Double = 0.2,
        var Category :String = ""
){
    override fun toString(): String {
        return "RecipeDTO(Id='$Id', Name='$Name', Description='$Description', NumOfPeople=$NumOfPeople, TimeRequired=$TimeRequired, Rating=$Rating, Category='$Category')"
    }
}
