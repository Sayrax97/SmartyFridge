package com.team4infinity.smartyfridge.di

import com.google.firebase.auth.FirebaseAuth
import com.team4infinity.smartyfridge.repository.AuthRepository
import com.team4infinity.smartyfridge.repository.IAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
            auth: FirebaseAuth
    ): IAuthRepository {
        return AuthRepository(auth)
    }
}