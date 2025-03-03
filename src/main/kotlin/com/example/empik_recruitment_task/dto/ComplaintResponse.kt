package com.example.empik_recruitment_task.dto

import java.time.LocalDateTime

data class ComplaintResponse(
    val productId: String,
    val content: String,
    val createdAt: LocalDateTime,
    val reporter: String,
    val country: String,
    val complaintCount: Int
)
