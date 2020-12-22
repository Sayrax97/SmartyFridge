package com.team4infinity.smartyfridge.firebaseEntities

data class CartDTO(
    val id :String = "",
    val list :List<GroceryDTO> = arrayListOf<GroceryDTO>()
){
    override fun toString(): String {
        return "CartDTO(id='$id', list=$list)"
    }
}
