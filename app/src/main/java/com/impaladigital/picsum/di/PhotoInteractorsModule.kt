package com.impaladigital.picsum.di

import com.impaladigital.photo_interactors.PhotoInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoInteractorsModule {

    @Provides
    @Singleton
    fun providerPhotoInteractors(): PhotoInteractors {
        return PhotoInteractors.build()
    }

}