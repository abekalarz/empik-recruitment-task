package com.example.empik_recruitment_task.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class IpLocationService(
    @Value("\${location.service.host}")
    private val host: String
) {

    private val logger: Logger = LoggerFactory.getLogger(IpLocationService::class.java)

    private val httpClient: HttpClient = HttpClient.newHttpClient()

    fun getCountryByIp(ip: String): String {
        val url = "$host/$ip/country_name/"
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build()

        // runCatching wyłapuje wyjątki i opakuje je w Result
        // Jeśli wszystko pójdzie ok, mamy "onSuccess"; w przeciwnym razie "onFailure"
        return runCatching {
            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

            logger.info("Response = $response")

            // Sprawdzamy kod odpowiedzi
            if (response.statusCode() == 200) {
                response.body() ?: "Unknown"
            } else {
                "Unknown"
            }
        }.getOrElse {
            // getOrElse wywoływane jest w przypadku wyjątku
            "Unknown"
        }
    }
}
