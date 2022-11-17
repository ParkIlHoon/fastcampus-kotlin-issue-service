package com.fastcampus.issueservice.service

import com.fastcampus.issueservice.domain.Issue
import com.fastcampus.issueservice.domain.IssueRepository
import com.fastcampus.issueservice.model.IssueRequest
import com.fastcampus.issueservice.model.IssueResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 이슈 서비스
 */
@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {

    /**
     * 이슈 생성
     *
     * @param userId 사용자 아이디
     * @param request 생성할 이슈 정보
     */
    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {
        val issue = Issue(
            userId = userId,
            summary = request.summary,
            description = request.description,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )
        return IssueResponse(issueRepository.save(issue))
    }

}