package com.team4infinity.smartyfridge.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.team4infinity.smartyfridge.firebaseEntities.CartDTO
import com.team4infinity.smartyfridge.firebaseEntities.FridgeDTO
import com.team4infinity.smartyfridge.firebaseEntities.GroupDTO

interface IGroupRepository {
    fun getGroup(groupId: String): MutableLiveData<GroupDTO>
    fun getAllGroupIds (): MutableLiveData<List<String>>
    fun getShoppingCart(groupId: String, cartId: String): MutableLiveData<CartDTO>
    fun getFridge(groupId: String, fridgeId: String): MutableLiveData<FridgeDTO>
    fun changeGroupName(groupId: String, name: String)
    fun addMemberToGroup(groupId: String, memberId: String)
    fun removeMemberFromGroup(groupId: String, memberId: String)

}