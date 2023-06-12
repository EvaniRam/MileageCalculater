package com.evani.mileageccalculator.presentation.di

import com.evani.mileageccalculator.data.MileageRepository
import com.evani.mileageccalculator.data.MileageRepositoryImpl
import com.evani.mileageccalculator.presentation.MileageViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VehicleModule {

    @Provides
    fun providesMileageRepository(): MileageRepositoryImpl {
        return MileageRepositoryImpl()
    }

    @Provides
    fun providesMileageViewModel(repository: MileageRepositoryImpl): MileageViewModel {
        return MileageViewModel(repository)
    }

}