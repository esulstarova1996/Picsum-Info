package com.impaladigital.picsum.di

import android.app.Application
import coil.ImageLoader
import com.impaladigital.picsum.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoilModule {

    @Provides
    @Singleton
    fun provideImageLoader(app: Application, @Named("io") coroutineDispatcher: CoroutineDispatcher): ImageLoader {
        return ImageLoader.Builder(app)
            .dispatcher(coroutineDispatcher)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(0.25) // Don't know what is recommended?
            .crossfade(true)
            .build()
    }
}