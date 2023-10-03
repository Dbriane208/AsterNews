package daniel.brian.asternews.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samkt.asternews.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daniel.brian.asternews.data.asterNewsRepository.AsterNewsApiService
import daniel.brian.asternews.data.asterNewsRepository.AsterNewsRepository
import daniel.brian.asternews.data.asterNewsRepository.AsterNewsRepositoryImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAsterNewsApiService(): AsterNewsApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(AsterNewsApiService::class.java)

    @Provides
    @Singleton
    fun provideAsterNewsRepository(newsApi: AsterNewsApiService): AsterNewsRepository =
        AsterNewsRepositoryImpl(newsApi = newsApi)
}
