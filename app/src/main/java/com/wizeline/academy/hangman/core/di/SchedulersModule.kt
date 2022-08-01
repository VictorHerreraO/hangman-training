package com.wizeline.academy.hangman.core.di

import com.wizeline.academy.hangman.core.arch.IoScheduler
import com.wizeline.academy.hangman.core.arch.MainThreadScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

@Module
@InstallIn(SingletonComponent::class)
object SchedulersModule {

    @[Provides IoScheduler]
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @[Provides MainThreadScheduler]
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

}
