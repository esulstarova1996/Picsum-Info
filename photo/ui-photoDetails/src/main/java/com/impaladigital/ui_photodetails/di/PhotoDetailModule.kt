package com.impaladigital.ui_photodetails.di

import com.impaladigital.photo_interactors.GetFilteredImageUrl
import com.impaladigital.photo_interactors.GetPhotoDetail
import com.impaladigital.photo_interactors.PhotoInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoDetailModule {

    @Provides
    @Singleton
    fun provideGetPhotos(
        interactors: PhotoInteractors,
    ): GetFilteredImageUrl {
        return interactors.getFilteredImageUrl
    }

    @Provides
    @Singleton
    fun providePhotoDetail(
        interactors: PhotoInteractors,
    ): GetPhotoDetail {
        return interactors.getPhotoDetail
    }


}