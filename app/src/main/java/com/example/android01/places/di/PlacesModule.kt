package com.example.android01.places.di


import com.example.android01.core.*
import com.example.android01.places.data.PlacesRepository
import com.example.android01.places.data.cache.PlaceCache
import com.example.android01.places.data.cloud.PlaceCloud
import com.example.android01.places.data.cloud.PlacesService
import com.example.android01.places.domain.PlaceDomain
import com.example.android01.places.domain.PlacesInteractor
import com.example.android01.places.presentation.PlaceUi
import com.example.android01.places.presentation.PlacesCommunication
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

/**
 * Created by HP on 03.02.2023.
 **/
@Module
@InstallIn(ViewModelComponent::class)
interface CitiesModule {

    @Binds
    @ViewModelScoped
    fun providePlaceRepo(obj: PlacesRepository.Base): PlacesRepository

    @Binds
    @ViewModelScoped
    fun providePlacesInteractor(obj: PlacesInteractor.Base): PlacesInteractor

    @Binds
    @ViewModelScoped
    fun provideToCacheMapper(obj: PlaceCloud.ToPlaceCache): PlaceCloud.Mapper<PlaceCache>

    @Binds
    @ViewModelScoped
    fun provideToDomainMapper(obj: PlaceCache.ToPlaceDomain): PlaceCache.Mapper<PlaceDomain>

    @Binds
    @ViewModelScoped
    fun providePlaceResultMapper(obj: PlacesResultMapper): Result.Mapper<PlaceDomain,Unit>

    @Binds
    @ViewModelScoped
    fun provideToPlaceUiMapper(obj: PlaceDomain.ToPlaceUi): PlaceDomain.Mapper<PlaceUi>

    @Binds
    @ViewModelScoped
    fun providePlaceCommunication(obj: PlacesCommunication.Base): PlacesCommunication

    @Binds
    @ViewModelScoped
    fun providePlaceHandleRequest(obj: HandleRequest.Base<PlaceDomain>): HandleRequest<PlaceDomain>

    @Binds
    @ViewModelScoped
    fun providePlaceHandleResult(obj: HandleResult.Base<PlaceDomain>): HandleResult<PlaceDomain>
}

@Module
@InstallIn(ViewModelComponent::class)
class CitiesModuleProvides{

    @Provides
    @ViewModelScoped
    fun provideCitiesService(retrofit: Retrofit):PlacesService{
        return retrofit.create(PlacesService::class.java)
    }
}