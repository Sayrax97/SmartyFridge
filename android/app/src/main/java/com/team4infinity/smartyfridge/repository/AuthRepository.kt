package com.team4infinity.smartyfridge.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository(var auth: FirebaseAuth) : IAuthRepository {

    override fun login(email: String, password: String): MutableLiveData<Boolean> {
        val data:MutableLiveData<Boolean> = MutableLiveData()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            data.value = task.isSuccessful
        }
        return data
    }
    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }


}