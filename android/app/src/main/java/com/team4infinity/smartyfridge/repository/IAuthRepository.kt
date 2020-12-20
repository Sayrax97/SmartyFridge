package com.team4infinity.smartyfridge.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface IAuthRepository {
      fun login(email: String, password: String): Task<AuthResult>
      fun getCurrentUser() : FirebaseUser?
}