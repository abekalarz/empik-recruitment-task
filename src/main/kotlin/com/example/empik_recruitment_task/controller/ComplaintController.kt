package com.example.empik_recruitment_task.controller

import com.example.empik_recruitment_task.dto.ComplaintRequest
import com.example.empik_recruitment_task.dto.ComplaintResponse
import com.example.empik_recruitment_task.service.ComplaintService
import com.example.empik_recruitment_task.service.IpLocationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

// TODO Add valid loggers !
@RestController
@RequestMapping("/api/v1/complaints")
class ComplaintController(
    private val reclamationService: ComplaintService
) {

    @PostMapping
    fun add(
        @RequestBody @Valid complaintRequest: ComplaintRequest,
        @RequestHeader(value = "X-Forwarded-For", required = false) forwardedHeader: String?
    ): ComplaintResponse =
        reclamationService.addComplaint(complaintRequest, forwardedHeader)


}