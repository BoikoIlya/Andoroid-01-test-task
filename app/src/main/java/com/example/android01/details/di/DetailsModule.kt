package com.example.android01.details.di

import com.example.android01.details.presentation.DetailsCommunication
import com.example.android01.details.presentation.PlayerCommunication
import com.example.android01.details.presentation.PlayerTimeCommunication
import com.example.android01.places.presentation.PlaceUi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

/**
 * Created by HP on 05.02.2023.
 **/
@Module
@InstallIn(ViewModelComponent::class)
interface DetailsModule {

    @Binds
    @ViewModelScoped
    fun providePlayerTimeCommunication(obj: PlayerTimeCommunication.Base): PlayerTimeCommunication

    @Binds
    @ViewModelScoped
    fun providePlayerCommunication(obj: PlayerCommunication.Base): PlayerCommunication

    @Binds
    @ViewModelScoped
    fun provideSetMediaPlayer(obj: PlaceUi.SetMediaPlayer.Base): PlaceUi.SetMediaPlayer

    @Binds
    @ViewModelScoped
    fun provideDetailsCommunication(obj: DetailsCommunication.Base): DetailsCommunication



}