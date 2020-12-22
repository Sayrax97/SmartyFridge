package com.team4infinity.smartyfridge.firebaseEntities

data class GroceryDTO(
        val Id :String = "",
        val Name :String = "",
        val Quantity :Int = 0,
        val Unit :String = "",
        val Category :String = "",
        val Icon :String = ""
){
    override fun toString(): String {
        return "GroceryDTO(Id='$Id', Name='$Name', Quantity=$Quantity, Unit='$Unit', Category='$Category', Icon='$Icon')"
    }
}
