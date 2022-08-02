package com.wizeline.academy.hangman.feature.game.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.BuildConfig
import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import com.wizeline.academy.hangman.feature.game.data.datasource.MoviesRemoteDataSourceContract
import com.wizeline.academy.hangman.feature.game.data.datasource.impl.MoviesRemoteDataSource
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.AuthInterceptor
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.ImdbApi
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieDto
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MoviesClient
import com.wizeline.academy.hangman.feature.game.data.repository.ChallengeRepositoryContract
import com.wizeline.academy.hangman.feature.game.data.repository.impl.ChallengeRepository
import com.wizeline.academy.hangman.feature.game.data.repository.mapper.MovieDtoToChallengeModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class GameModule {

    private companion object {
        const val LOGGING_INTERCEPTOR = "logging_interceptor"
        const val AUTH_INTERCEPTOR = "auth_interceptor"
    }

    @Provides
    fun provideGson(

    ): Gson = GsonBuilder().create()

    @[Provides Named(LOGGING_INTERCEPTOR)]
    fun provideLoggingInterceptor(

    ): Interceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

    @[Provides Named(AUTH_INTERCEPTOR)]
    fun provideAuthInterceptor(

    ): Interceptor {
        return AuthInterceptor(
            apiKey = BuildConfig.API_KEY
        )
    }

    @Provides
    fun provideOkHttpClient(
        @Named(AUTH_INTERCEPTOR) authInterceptor: Interceptor,
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .apply {
            addInterceptor(authInterceptor)
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
        }
        .build()

    @Provides
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ImdbApi.BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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

    @Provides
    fun provideChallengeRepositoryContract(
        challengeRepository: ChallengeRepository
    ): ChallengeRepositoryContract = challengeRepository

}
