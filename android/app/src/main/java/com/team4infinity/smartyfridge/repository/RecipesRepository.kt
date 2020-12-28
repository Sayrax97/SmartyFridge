package com.team4infinity.smartyfridge.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.team4infinity.smartyfridge.firebaseEntities.RecipeDTO
import com.team4infinity.smartyfridge.utils.FIREBASE_DB_RECIPES

class RecipesRepository (var databaseReference: DatabaseReference): IRecipesRepository {
    val recipes: MutableLiveData<MutableList<RecipeDTO>> = MutableLiveData(mutableListOf())
    val indexer: MutableMap<String,Int> = mutableMapOf()
    override fun getRecipes(size: Int, last_recipe_id: String?): MutableLiveData<MutableList<RecipeDTO>> {

        if (last_recipe_id.isNullOrEmpty())
            databaseReference.child(FIREBASE_DB_RECIPES).orderByKey().limitToFirst(size).addChildEventListener(object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val recipe: RecipeDTO? = snapshot.getValue(RecipeDTO::class.java)
                    if (recipe != null) {
                        indexer[recipe.Id] = recipes.value!!.size
                        recipes.value?.add(recipe)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val recipe: RecipeDTO? = snapshot.getValue(RecipeDTO::class.java)
                    if (recipe != null) {
                        val id = indexer[recipe.Id]
                        recipes.value!![id!!] = recipe
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val recipe: RecipeDTO? = snapshot.getValue(RecipeDTO::class.java)
                    if (recipe != null) {
                        recipes.value?.remove(recipe)
                        resetIndexer()
                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        else
            databaseReference.child(FIREBASE_DB_RECIPES).orderByKey().startAt(last_recipe_id).limitToFirst(size).addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.key == last_recipe_id){
                        return
                    }
                    val recipe: RecipeDTO? = snapshot.getValue(RecipeDTO::class.java)
                    if (recipe != null) {
                        indexer[recipe.Id] = recipes.value!!.size
                        recipes.value?.add(recipe)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.key == last_recipe_id){
                        return
                    }
                    val recipe: RecipeDTO? = snapshot.getValue(RecipeDTO::class.java)
                    if (recipe != null) {
                        val index = indexer[recipe.Id]
                        recipes.value!![index!!] = recipe
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    if (snapshot.key == last_recipe_id){
                        return
                    }
                    val recipe: RecipeDTO? = snapshot.getValue(RecipeDTO::class.java)
                    if (recipe != null) {
                        recipes.value?.remove(recipe)
                        resetIndexer()
                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        return recipes
    }

    override fun getRecipe(recipe_id: String): MutableLiveData<RecipeDTO> {
        val recipe: MutableLiveData<RecipeDTO> = MutableLiveData()
        databaseReference.child(FIREBASE_DB_RECIPES).child(recipe_id).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                recipe.value = snapshot.getValue(RecipeDTO::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return recipe
    }

    override fun createRecipe(recipe: RecipeDTO) {
        val key = databaseReference.child(FIREBASE_DB_RECIPES).push().key
        recipe.Id = key!!
        databaseReference.child(FIREBASE_DB_RECIPES).child(recipe.Id).setValue(recipe)
    }

    override fun canMakeRecipe(recipe_id: String, fridge_id: String): MutableLiveData<List<String>> {
        TODO("Not yet implemented")
    }

    fun resetIndexer(){
        indexer.clear()
        recipes.value?.forEachIndexed{
            index, recipeDTO ->
            indexer[recipeDTO.Id] = index
        }
    }
}