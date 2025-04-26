package com.max77.tmdbsample.network

import com.max77.tmdbsample.BuildConfig
import com.max77.tmdbsample.network.tmdb.datasource.TmdbDataSource
import com.max77.tmdbsample.network.tmdb.datasource.TmdbDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }
        }
    }

    single<TmdbDataSource> {
        TmdbDataSourceImpl(
            ktorClient = get(),
            apiKey = BuildConfig.TMDB_API_KEY,
            baseUrl = BuildConfig.TMDB_BASE_URL
        )
    }
}