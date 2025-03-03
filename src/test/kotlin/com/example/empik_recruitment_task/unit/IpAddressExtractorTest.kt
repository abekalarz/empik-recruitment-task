package com.example.empik_recruitment_task.unit

import com.example.empik_recruitment_task.service.IpAddressExtractor
import org.junit.jupiter.api.Tag
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Tag("unit")
internal class IpAddressExtractorTest {

    private val ipAddressExtractor: IpAddressExtractor = IpAddressExtractor()

    @AfterEach
    fun tearDown() {
        unmockkStatic(RequestContextHolder::class)
    }

    @Nested
    @DisplayName("Scenarios with a populated X-Forwarded-For header")
    inner class ForwardedHeaderTests {

        @Test
        fun `should return the first IP address from X-Forwarded-For header`() {
            // given
            val forwardedHeader = "192.168.1.2, 127.0.0.1"

            // when
            val result = ipAddressExtractor.extractIp(forwardedHeader)

            // then
            assertEquals("192.168.1.1", result)
        }

        @Test
        fun `should return the IP address when the header contains only one address`() {
            // given
            val forwardedHeader = "10.0.0.5"

            // when
            val result = ipAddressExtractor.extractIp(forwardedHeader)

            // then
            assertEquals("10.0.0.5", result)
        }

        @Test
        fun `should return the IP address when address is surrounded by spaces`() {
            // given
            val forwardedHeader = "  192.168.1.50  ,  192.168.1.51 "

            // when
            val result = ipAddressExtractor.extractIp(forwardedHeader)

            // then
            assertEquals("192.168.1.50", result)
        }
    }

    @Nested
    @DisplayName("Scenarios where the X-Forwarded-For header is null or empty.")
    inner class NullOrEmptyHeaderTests {

        @Test
        fun `should return the address from remoteAddr when the X-Forwarded-For header is null`() {
            // given
            mockkStatic(RequestContextHolder::class)

            val mockRequest = mockk<HttpServletRequest>()
            every { mockRequest.remoteAddr } returns "127.0.0.1"

            val mockAttributes = mockk<ServletRequestAttributes>()
            every { mockAttributes.request } returns mockRequest

            every { RequestContextHolder.getRequestAttributes() } returns mockAttributes

            // when
            val result = ipAddressExtractor.extractIp(null)

            // then
            assertEquals("127.0.0.1", result)
        }

        @Test
        fun `should retrieve the address from remoteAddr when the X-Forwarded-For header is empty`() {
            // given
            mockkStatic(RequestContextHolder::class)

            val mockRequest = mockk<HttpServletRequest>()
            every { mockRequest.remoteAddr } returns "192.168.100.1"

            val mockAttributes = mockk<ServletRequestAttributes>()
            every { mockAttributes.request } returns mockRequest

            every { RequestContextHolder.getRequestAttributes() } returns mockAttributes

            // when
            val result = ipAddressExtractor.extractIp("")

            // then
            assertEquals("192.168.100.1", result)
        }
    }

}
