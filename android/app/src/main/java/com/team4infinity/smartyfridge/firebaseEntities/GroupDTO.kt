package com.team4infinity.smartyfridge.firebaseEntities

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable
@IgnoreExtraProperties
data class GroupDTO(
        val Id :String = "",
        val Name :String? = "",
        val CartId :String? = "",
        val FridgeId :String = "",
        val RecipeListId :String = "",
        val Members :List<String> = listOf()

) {

    override fun toString(): String {
        return "GroupDTO(Id='$Id', Name=$Name, CartId=$CartId, FridgeId='$FridgeId', RecipeListId='$RecipeListId', Members=$Members)"
    }
}
