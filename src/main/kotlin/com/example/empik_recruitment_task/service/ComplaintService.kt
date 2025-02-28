package com.example.empik_recruitment_task.service

import com.example.empik_recruitment_task.dto.ComplaintRequest
import com.example.empik_recruitment_task.dto.ComplaintResponse
import com.example.empik_recruitment_task.model.Complaint
import com.example.empik_recruitment_task.model.toComplaint
import com.example.empik_recruitment_task.model.toComplaintResponse
import com.example.empik_recruitment_task.repository.ComplaintRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
class ComplaintService(
    private val complaintRepository: ComplaintRepository,
    private val ipLocationService: IpLocationService,
    private val ipAddressExtractor: IpAddressExtractor
) {

    private val logger: Logger = LoggerFactory.getLogger(ComplaintService::class.java)

    @Transactional
    fun addComplaint(request: ComplaintRequest, forwardedHeader: String?): ComplaintResponse {
        val existingComplaint = complaintRepository.findByProductIdAndReporter(request.productId, request.reporter)

        val complaint = existingComplaint?.apply { incrementCounter() }
            ?: createNewComplaint(request, forwardedHeader)

        return complaintRepository.save(complaint).toComplaintResponse()
    }

    private fun createNewComplaint(request: ComplaintRequest, forwardedHeader: String?): Complaint {
        val reporterIp = ipAddressExtractor.extractIp(forwardedHeader)

        logger.info("Reporter ip = $reporterIp")

        val country = ipLocationService.getCountryByIp(reporterIp)

        logger.info("Reporter country = $country")

        return request.toComplaint(country)
    }


}