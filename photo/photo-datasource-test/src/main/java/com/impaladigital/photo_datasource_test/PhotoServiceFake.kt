package com.impaladigital.photo_datasource_test

import com.impaladigital.photo_datasource.network.PhotoService
import com.impaladigital.photo_datasource.network.PhotoServiceImpl
import com.impaladigital.photo_datasource_test.data.EmptyPhotoData
import com.impaladigital.photo_datasource_test.data.MalformedPhotoData
import com.impaladigital.photo_datasource_test.data.ValidPhotoData
import com.impaladigital.photo_datasource_test.data.ValidPhotoObject
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class PhotoServiceFake {

    companion object Factory {

        private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
        private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

        fun build(
            type: PhotoServiceResponseType,
        ): PhotoService {
            val client = HttpClient(MockEngine) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                            encodeDefaults = false
                        }
                    )
                }
                engine {
                    addHandler { request ->
                        when (request.url.host) {
                            "picsum.photos" -> {
                                val responseHeaders = headersOf(
                                    "Content-Type" to listOf("application/json", "charset=utf-8")
                                )
                                when (type) {
                                    PhotoServiceResponseType.EmptyList -> {
                                        respond(
                                            EmptyPhotoData.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    PhotoServiceResponseType.GoodData -> {
                                        respond(
                                            ValidPhotoData.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    PhotoServiceResponseType.Http404 -> {
                                        respond(
                                            EmptyPhotoData.data,
                                            status = HttpStatusCode.NotFound,
                                            headers = responseHeaders
                                        )
                                    }
                                    PhotoServiceResponseType.MalformedData -> {
                                        respond(
                                            MalformedPhotoData.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    PhotoServiceResponseType.ValidPhotoData -> {
                                        respond(
                                            ValidPhotoObject.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                }
                            }
                            else -> error("Unhandled ${request.url.fullUrl}")
                        }
                    }
                }
            }
            return PhotoServiceImpl(client)
        }
    }
}