package com.example.empik_recruitment_task.service

import com.example.empik_recruitment_task.dto.ComplaintRequest
import com.example.empik_recruitment_task.dto.ComplaintResponse
import com.example.empik_recruitment_task.model.Complaint
import com.example.empik_recruitment_task.model.toComplaint
import com.example.empik_recruitment_task.model.toComplaintResponse
import com.example.empik_recruitment_task.repository.ComplaintRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ComplaintService(
    private val complaintRepository: ComplaintRepository,
    private val ipLocationService: IpLocationService,
    private val ipAddressExtractor: IpAddressExtractor
) {

    private val logger: Logger = LoggerFactory.getLogger(ComplaintService::class.java)

    @Transactional
    fun addComplaint(request: ComplaintRequest, forwardedHeader: String?): ComplaintResponse {
        logger.info("New complaint creation STARTED for productId [${request.productId}] and reporter [${request.reporter}]")

        val existingComplaint = complaintRepository.findByProductIdAndReporter(request.productId, request.reporter)
        val complaint = existingComplaint?.apply {
            logger.warn("Complaint already exists for productId [${request.productId}] and reporter [${request.reporter}]. Incrementing counter...")
            incrementCounter()
        } ?: createNewComplaint(request, forwardedHeader)

        val savedComplaint = complaintRepository.save(complaint).toComplaintResponse()

        logger.info("New complaint creation FINISHED for productId [${request.productId}] and reporter [${request.reporter}]")

        return savedComplaint
    }

    @Transactional
    fun updateContent(productId: String, reporter: String, newContent: String): ComplaintResponse? {
        val complaint = complaintRepository.findByProductIdAndReporter(productId, reporter)
        return complaint?.let {
            it.updateContent(newContent)
            complaintRepository.save(it).toComplaintResponse()
        }?.also {
            logger.info("Complaint content successfully UPDATED for productId [$productId] and reporter [$reporter]")
        }
    }

    fun getComplaint(productId: String, reporter: String): ComplaintResponse? =
        complaintRepository.findByProductIdAndReporter(productId, reporter)?.toComplaintResponse()

    fun getAllComplaints(pageable: Pageable): Page<ComplaintResponse> =
        complaintRepository.findAll(pageable)
            .map { it.toComplaintResponse() }

    private fun createNewComplaint(request: ComplaintRequest, forwardedHeader: String?): Complaint {
        val reporterIp = ipAddressExtractor.extractIp(forwardedHeader)
        val country = ipLocationService.getCountryByIp(reporterIp)
        return request.toComplaint(country)
    }

}
