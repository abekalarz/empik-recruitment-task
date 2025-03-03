package com.example.empik_recruitment_task.dto

import jakarta.validation.constraints.NotBlank

data class ComplaintUpdateRequest(
    @field:NotBlank(message = "Complaint content is mandatory")
    val content: String
)
