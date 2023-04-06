object Ktor {
    private const val ktorVersion = "2.2.2"

    const val core = "io.ktor:ktor-client-core:$ktorVersion"

    const val ktorKotlinxSerialization = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
    const val contentNegotiator = "io.ktor:ktor-client-content-negotiation:$ktorVersion"

    const val android = "io.ktor:ktor-client-android:$ktorVersion"

    const val ktorClientLogging = "io.ktor:ktor-client-logging:$ktorVersion"

    const val ktorClientMock = "io.ktor:ktor-client-mock:$ktorVersion"
    const val clientSerialization = "io.ktor:ktor-client-serialization:$ktorVersion"
}