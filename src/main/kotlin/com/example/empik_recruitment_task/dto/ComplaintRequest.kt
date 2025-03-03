package com.example.empik_recruitment_task.dto

import jakarta.validation.constraints.NotBlank

data class ComplaintRequest(
    @field:NotBlank(message = "Product id is mandatory")
    val productId: String,
    @field:NotBlank(message = "Complaint content is mandatory")
    val content: String,
    @field:NotBlank(message = "Reporter is mandatory")
    val reporter: String
)
