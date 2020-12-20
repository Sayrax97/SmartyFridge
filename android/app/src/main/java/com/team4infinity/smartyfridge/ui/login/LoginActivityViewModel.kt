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

    private var _loggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun login(email:String, password: String){
        repository.login(email,password).addOnSuccessListener {
            _loggedIn.value = true
        }.addOnFailureListener {
            _loggedIn.value = false
        }
    }
    fun isLoggedIn():LiveData<Boolean>{
        return _loggedIn
    }
    fun getCurrentUser(): FirebaseUser? {
        return repository.getCurrentUser()
    }

}