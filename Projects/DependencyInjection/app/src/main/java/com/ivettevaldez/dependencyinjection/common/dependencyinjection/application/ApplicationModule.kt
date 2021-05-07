package com.ivettevaldez.dependencyinjection.common.dependencyinjection.application

import android.app.Application
import com.ivettevaldez.dependencyinjection.common.dependencyinjection.Retrofit1
import com.ivettevaldez.dependencyinjection.common.dependencyinjection.Retrofit2
import com.ivettevaldez.dependencyinjection.networking.StackOverflowApi
import com.ivettevaldez.dependencyinjection.networking.UrlProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun application(): Application = application

    @Provides
    @ApplicationScope
    fun urlProvider(): UrlProvider = UrlProvider()

    @Provides
    @ApplicationScope
    @Retrofit1
    fun retrofit1(urlProvider: UrlProvider): Retrofit =
        Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl1())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @ApplicationScope
    @Retrofit2
    fun retrofit2(urlProvider: UrlProvider): Retrofit =
        Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl2())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @ApplicationScope
    fun stackOverflowApi(@Retrofit1 retrofit: Retrofit): StackOverflowApi =
        retrofit.create(StackOverflowApi::class.java)
}