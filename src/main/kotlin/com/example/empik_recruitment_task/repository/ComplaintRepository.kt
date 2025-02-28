package com.example.empik_recruitment_task.repository

import com.example.empik_recruitment_task.model.Complaint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ComplaintRepository : JpaRepository<Complaint, Long> {

    fun findByProductIdAndReporter(productId: String, reporter: String): Complaint?

}
