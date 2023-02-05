package com.example.android01.app

import android.content.Context
import android.media.MediaPlayer
import android.provider.DocumentsContract.Root
import androidx.room.Room
import com.example.android01.core.*
import com.example.android01.details.presentation.PlayerCommunication
import com.example.android01.details.presentation.PlayerTimeCommunication
import com.example.android01.places.data.cache.PlaceDao
import com.example.android01.places.data.cache.PlacesDB
import com.example.android01.places.presentation.PlaceUi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by HP on 03.02.2023.
 **/
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object{
        private const val baseUrl = "https://krokapp.by/api/"
    }

    @Provides
    @Singleton
    fun provideManagerResource(@ApplicationContext context: Context): ManagerResource{
        return ManagerResource.Base(context)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun bindDetailsKeeper():DetailsKeeper<PlaceUi>{
        return DetailsKeeper.Base(
            PlaceUi(
                id = 0,
                images = listOf(),
                logo = "",
                name = "",
                sound = "",
                text = ""
            )
        )
    }

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): PlacesDB{
        return Room.databaseBuilder(
                context,
                PlacesDB::class.java,
                "places_db"
            ).build()
    }

    @Provides
    @Singleton
    fun provideDao(db: PlacesDB): PlaceDao{
        return db.getDao()
    }

    @Provides
    @Singleton
    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface AppBindModule{

    @Binds
    @Singleton
    fun bindImageLoader(obj: ImageLoader.Base): ImageLoader

    @Binds
    @Singleton
    fun bindDispatcherList(obj: DispatchersList.Base): DispatchersList

    @Binds
    @Singleton
    fun bindHandleError(obj: HandleError.Base): HandleError


}