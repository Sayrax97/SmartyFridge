package com.team4infinity.smartyfridge.firebaseEntities

data class FridgeDTO(
    val id :String = "",
    val list :List<GroceryDTO> = arrayListOf<GroceryDTO>()
){
    override fun toString(): String {
        return "FridgeDTO(id='$id', list=$list)"
    }
}
