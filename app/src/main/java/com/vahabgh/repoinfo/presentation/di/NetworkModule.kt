package com.vahabgh.repoinfo.presentation.di

import com.apollographql.apollo.ApolloClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.vahabgh.repoinfo.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient : OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BuildConfig.SERVER_URL)
            .okHttpClient(okHttpClient).build()
    }


    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(10,TimeUnit.SECONDS)
            .addInterceptor {chain->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.ACCECC_TOKEN)
                    .build()
                 chain.proceed(request)
            }.build()
    }

}