package com.impaladigital.photo_datasource.network

import com.impaladigital.photo_domain.PhotoData
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.net.SocketTimeoutException

interface PhotoService {

    suspend fun getPhotoList(pageNumber: Int, pageLimit: Int): List<PhotoData>

    suspend fun getPhotoData(photoId: String): PhotoData

    companion object Factory {
        fun build(): PhotoService {
            return PhotoServiceImpl(
                httpClient = HttpClient(Android) {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                ignoreUnknownKeys = true
                                isLenient = true
                                encodeDefaults = false
                            }
                        )
                    }
                    install(HttpTimeout) {
                        socketTimeoutMillis = 15000L
                        requestTimeoutMillis = 15000L
                        connectTimeoutMillis = 15000L
                    }
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.HEADERS
                    }
                    install(HttpRequestRetry) {
                        maxRetries = 3
                        retryIf { _, response -> !response.status.isSuccess() }
                        retryOnExceptionIf { _, cause -> cause is HttpRequestTimeoutException || cause is SocketTimeoutException }
                        delayMillis { retry -> retry * 3000L }
                    }
                }
            )
        }

    }

}