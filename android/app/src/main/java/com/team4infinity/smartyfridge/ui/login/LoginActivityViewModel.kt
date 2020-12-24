package com.team4infinity.smartyfridge.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.team4infinity.smartyfridge.repository.IAuthRepository

class LoginActivityViewModel

    @ViewModelInject
    constructor(private val repository: IAuthRepository): ViewModel() {

    lateinit var loggedIn: LiveData<Boolean>

    fun login(email:String, password: String){
        loggedIn = repository.login(email,password)
    }
    fun getCurrentUser(): FirebaseUser? {
        return repository.getCurrentUser()
    }

}