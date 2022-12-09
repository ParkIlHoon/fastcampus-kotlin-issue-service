package com.fastcampus.issueservice.service

import com.fastcampus.issueservice.domain.Issue
import com.fastcampus.issueservice.domain.IssueRepository
import com.fastcampus.issueservice.domain.enums.IssueStatus
import com.fastcampus.issueservice.exception.IssueNotFoundException
import com.fastcampus.issueservice.exception.NotFoundException
import com.fastcampus.issueservice.model.IssueRequest
import com.fastcampus.issueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
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
     * @return 생성된 이슈 응답 객체
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

    /**
     * 이슈 목록 조회
     *
     * @param userId 사용자 아이디
     * @param status 이슈 상태
     * @return 사용자 아이디와 이슈 상태에 해당하는 이슈 응답 객체 목록
     */
    @Transactional(readOnly = true)
    fun getAll(userId: Long, status: IssueStatus): List<IssueResponse> {
        return issueRepository.findAllByUserIdAndStatusOrderByCreatedAtDesc(userId, status)
            .map { IssueResponse(it) }
    }

    /**
     * 이슈 단건 조회
     *
     * @param id 조회할 이슈 아이디
     * @throws NotFoundException 아이디에 해당하는 이슈가 존재하지 않는 경우
     * @return 아이디에 해당하는 이슈 응답 객체
     */
    @Transactional(readOnly = true)
    fun get(id: Long): Any {
        val issue = issueRepository.findByIdOrNull(id) ?: throw IssueNotFoundException()
        return IssueResponse(issue)
    }

    /**
     * 이슈 수정
     *
     * @param id 수정할 이슈 아이디
     * @param userId 수정할 사용자 아이디
     * @param request 수정할 내용
     * @throws NotFoundException 아이디에 해당하는 이슈가 존재하지 않는 경우
     * @return 수정된 이슈 응답 객체
     */
    @Transactional
    fun update(id: Long, userId: Long, request: IssueRequest): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id) ?: throw IssueNotFoundException()
        return with(issue) {
            summary = request.summary
            description = request.description
            type = request.type
            priority = request.priority
            status = request.status
            this.userId = userId

            IssueResponse(issueRepository.save(this))
        }
    }

    /**
     * 이슈 삭제
     *
     * @param id 삭제할 이슈 아이디
     * @throws NotFoundException 아이디에 해당하는 이슈가 존재하지 않는 경우
     */
    @Transactional
    fun delete(id: Long) {
        val issue = issueRepository.findByIdOrNull(id) ?: throw IssueNotFoundException()
        issueRepository.delete(issue)
    }

}