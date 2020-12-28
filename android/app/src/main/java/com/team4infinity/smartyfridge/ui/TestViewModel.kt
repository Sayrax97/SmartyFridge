package com.team4infinity.smartyfridge.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.team4infinity.smartyfridge.firebaseEntities.RecipeDTO
import com.team4infinity.smartyfridge.repository.IRecipesRepository
import com.team4infinity.smartyfridge.utils.PAGE_SIZE

class TestViewModel

@ViewModelInject
constructor(private val repository: IRecipesRepository): ViewModel() {
    //lateinit var recipes: LiveData<MutableList<RecipeDTO>>
    lateinit var recipe: LiveData<RecipeDTO>

    fun getRecipes(){
//        recipes = this.repository.getRecipes(PAGE_SIZE,"-MPZcVuSLa5hhJsfEYDM")
        recipe = repository.getRecipe("-MPZcVuHrr-0jWZFJtgq")
    }

}