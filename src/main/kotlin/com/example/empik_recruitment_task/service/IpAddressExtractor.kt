package com.example.empik_recruitment_task.service

import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Service
class IpAddressExtractor {

    fun extractIp(forwardedHeader: String?): String {
        val clientIp = forwardedHeader?.split(",")?.firstOrNull()?.takeIf { it.isNotBlank() }?.trim()
        return clientIp ?: extractIpFromRemoteAddr()
    }

    private fun extractIpFromRemoteAddr(): String {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        return request.remoteAddr
    }

}
