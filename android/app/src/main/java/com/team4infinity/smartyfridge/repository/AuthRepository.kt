package com.team4infinity.smartyfridge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository(var auth: FirebaseAuth) : IAuthRepository {

    override fun login(email: String, password: String): Task<AuthResult> {
      return  auth.signInWithEmailAndPassword(email, password)
    }
    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }


}