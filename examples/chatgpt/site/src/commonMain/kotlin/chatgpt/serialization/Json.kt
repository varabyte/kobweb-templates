package chatgpt.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

val JsonSerializer = Json {
    // OpenAI uses snake_case, while our Kotlin objects use pascalCase
    @ExperimentalSerializationApi
    @OptIn(ExperimentalSerializationApi::class)
    namingStrategy = JsonNamingStrategy.SnakeCase

    // We don't want to fail if we get a response with extra fields
    ignoreUnknownKeys = true
}
