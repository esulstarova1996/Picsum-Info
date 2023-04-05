package com.impaladigital.ui_photolist.di

import com.impaladigital.photo_interactors.GetPhotoPage
import com.impaladigital.photo_interactors.PhotoInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoListModule {

    @Provides
    @Singleton
    fun provideGetPhotos(
        interactors: PhotoInteractors,
    ): GetPhotoPage {
        return interactors.getPhotoPage
    }

}