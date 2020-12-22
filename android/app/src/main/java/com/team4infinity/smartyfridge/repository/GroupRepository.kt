package com.team4infinity.smartyfridge.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.team4infinity.smartyfridge.firebaseEntities.CartDTO
import com.team4infinity.smartyfridge.firebaseEntities.FridgeDTO
import com.team4infinity.smartyfridge.firebaseEntities.GroceryDTO
import com.team4infinity.smartyfridge.firebaseEntities.GroupDTO
import com.team4infinity.smartyfridge.utils.FIREBASE_DB_GROUP
import com.team4infinity.smartyfridge.utils.FIREBASE_DB_GROUP_IDs
import com.team4infinity.smartyfridge.utils.TAG

class GroupRepository (var databaseReference: DatabaseReference): IGroupRepository {
    override fun getGroup(groupId: String): MutableLiveData<GroupDTO> {
        val group: MutableLiveData<GroupDTO>  = MutableLiveData()
        databaseReference.child(FIREBASE_DB_GROUP).child(groupId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<GroupDTO>()
                group.value = value
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value", error.toException())
            }
        })
        return group
    }

    override fun getAllGroupIds(): MutableLiveData<List<String>> {
        val groupIds:MutableLiveData<List<String>> = MutableLiveData()
        databaseReference.child(FIREBASE_DB_GROUP_IDs).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<List<String>>()
                Log.d(TAG, "onDataChange: $value")
                groupIds.value = value
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value", error.toException())
            }


        })
        return groupIds
    }

    override fun getShoppingCart(groupId: String, cartId: String): MutableLiveData<CartDTO> {
        //TODO
        return MutableLiveData(CartDTO(cartId, listOf(GroceryDTO("wnafhwgi","grocery1",1,"Kg","Unknown","groceries.png")) ))
    }

    override fun getFridge(groupId: String, fridgeId: String): MutableLiveData<FridgeDTO> {
        //TODO
        return MutableLiveData(FridgeDTO(fridgeId, listOf(GroceryDTO("wnafhwgi","grocery1",1,"Kg","Unknown","groceries.png"))))
    }

    override fun changeGroupName(groupId: String, name: String) {
        databaseReference.child(FIREBASE_DB_GROUP).child(groupId).child("Name").setValue(name)
    }

    override fun addMemberToGroup(groupId: String, memberId: String) {
        //TODO
        databaseReference.child(FIREBASE_DB_GROUP).child(groupId).child("Members").child("").setValue(memberId)
    }

    override fun removeMemberFromGroup(groupId: String, memberId: String) {
        //TODO
        TODO("Not yet implemented")
    }



}