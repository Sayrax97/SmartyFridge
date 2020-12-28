package com.team4infinity.smartyfridge.repository

import androidx.lifecycle.MutableLiveData
import com.team4infinity.smartyfridge.firebaseEntities.RecipeDTO

interface IRecipesRepository {
    fun getRecipes(size: Int, last_recipe_id: String?): MutableLiveData<MutableList<RecipeDTO>>
    fun getRecipe(recipe_id: String): MutableLiveData<RecipeDTO>
    fun createRecipe(recipe: RecipeDTO)
    fun canMakeRecipe(recipe_id: String, fridge_id: String): MutableLiveData<List<String>>
}