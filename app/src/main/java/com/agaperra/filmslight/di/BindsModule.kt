package com.agaperra.filmslight.di

import com.agaperra.filmslight.data.repository.FilmsRepositoryImpl
import com.agaperra.filmslight.domain.repository.FilmsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface BindsModule {

    @Binds
    fun bindFilmsRepository(filmsRepositoryImpl: FilmsRepositoryImpl): FilmsRepository

}