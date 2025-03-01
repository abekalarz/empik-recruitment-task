package com.example.empik_recruitment_task.controller

import com.example.empik_recruitment_task.dto.ComplaintRequest
import com.example.empik_recruitment_task.dto.ComplaintResponse
import com.example.empik_recruitment_task.dto.ComplaintUpdateRequest
import com.example.empik_recruitment_task.service.ComplaintService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

// TODO Add valid loggers !
@RestController
@RequestMapping("/api/v1/complaints")
class ComplaintController(
    private val complaintService: ComplaintService
) {

    @PostMapping
    fun add(
        @RequestBody @Valid complaintRequest: ComplaintRequest,
        @RequestHeader(value = "X-Forwarded-For", required = false) forwardedHeader: String?
    ): ComplaintResponse = complaintService.addComplaint(complaintRequest, forwardedHeader)

    @PutMapping("/{productId}/{reporter}")
    fun updateComplaintContent(
        @PathVariable productId: String,
        @PathVariable reporter: String,
        @RequestBody @Valid newContent: ComplaintUpdateRequest
    ): ComplaintResponse? = complaintService.updateContent(productId, reporter, newContent.content)

    @GetMapping("/{productId}/{reporter}")
    fun getComplaint(
        @PathVariable productId: String,
        @PathVariable reporter: String
    ): ComplaintResponse? = complaintService.getComplaint(productId, reporter)

    @GetMapping("/all")
    fun getAllComplaints(): List<ComplaintResponse> = complaintService.getAllComplaints()

}
