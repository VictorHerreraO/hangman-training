package com.wizeline.academy.hangman.feature.game.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.BuildConfig
import com.wizeline.academy.hangman.feature.game.ChallengeModel
import com.wizeline.academy.hangman.feature.game.data.datasource.MoviesRemoteDataSourceContract
import com.wizeline.academy.hangman.feature.game.data.datasource.impl.MoviesRemoteDataSource
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.ImdbApi
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieDto
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MoviesClient
import com.wizeline.academy.hangman.feature.game.data.repository.mapper.MovieDtoToChallengeModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class GameModule {

    private companion object {
        const val LOGGING_INTERCEPTOR = "logging_interceptor"
    }

    @Provides
    fun provideGson(

    ): Gson = GsonBuilder().create()

    @[Provides Named(LOGGING_INTERCEPTOR)]
    fun provideLoggingInterceptor(

    ): Interceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

    @Provides
    fun provideOkHttpClient(
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
        }
        .build()

    @Provides
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ImdbApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    fun provideMoviesClient(
        retrofit: Retrofit
    ): MoviesClient = retrofit.create()

    @Provides
    fun provideMoviesRemoteDataSourceContract(
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRemoteDataSourceContract = moviesRemoteDataSource

    @Provides
    fun provideMovieDtoToChallengeModelMapper(
        movieDtoToChallengeModelMapper: MovieDtoToChallengeModelMapper
    ): Mapper<MovieDto, ChallengeModel> = movieDtoToChallengeModelMapper

}
