package com.example.empik_recruitment_task.model

import com.example.empik_recruitment_task.dto.ComplaintRequest
import com.example.empik_recruitment_task.dto.ComplaintResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "complaints",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["product_id", "reporter"])
    ]
)
class Complaint(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "product_id", nullable = false)
    val productId: String,

    @Column(nullable = false)
    var content: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val reporter: String,

    @Column(nullable = false)
    val country: String,

    @Column(name = "complaint_count", nullable = false)
    var complaintCount: Int = 1
) {
    fun incrementCounter() {
        this.complaintCount++
    }

    fun updateContent(newContent: String) {
        this.content = newContent
    }
}

fun Complaint.toComplaintResponse(): ComplaintResponse =
    ComplaintResponse(
        this.productId,
        this.content,
        this.createdAt,
        this.reporter,
        this.country,
        this.complaintCount
    )

fun ComplaintRequest.toComplaint(country: String): Complaint =
    Complaint(
        productId = this.productId,
        content = this.content,
        reporter = this.reporter,
        country = country
    )
