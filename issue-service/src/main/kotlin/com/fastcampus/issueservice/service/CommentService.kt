package com.fastcampus.issueservice.service

import com.fastcampus.issueservice.domain.Comment
import com.fastcampus.issueservice.domain.CommentRepository
import com.fastcampus.issueservice.domain.IssueRepository
import com.fastcampus.issueservice.exception.IssueNotFoundException
import com.fastcampus.issueservice.model.CommentRequest
import com.fastcampus.issueservice.model.CommentResponse
import com.fastcampus.issueservice.model.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 이슈 코멘트 서비스
 */
@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository
) {

    /**
     * 코멘트 생성
     *
     *  @param issueId 이슈 아이디
     *  @param userId 사용자 아이디
     *  @param username 사용자 이름
     *  @param request 코멘트 생성 정보
     *  @return 생성된 코멘트 응답 객체
     */
    @Transactional
    fun create(issueId: Long, userId: Long, username: String, request: CommentRequest): CommentResponse {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw IssueNotFoundException()
        val comment = Comment(
            issue = issue,
            userId = userId,
            username = username,
            body = request.body,
        )
        issue.comments.add(comment)
        return commentRepository.save(comment).toResponse()
    }

    /**
     * 코멘트 목록 조회
     *
     * @param issueId 이슈 아이디
     * @return 이슈 아이디에 해당하는 코멘트 목록(생성일시 순 정렬)
     */
    @Transactional(readOnly = true)
    fun get(issueId: Long): List<CommentResponse> {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw IssueNotFoundException()
        return commentRepository.findAllByIssueOrderByCreatedAt(issue)
            .map { it.toResponse() }
    }

    @Transactional
    fun update(commentId: Long, userId: Long, request: CommentRequest): CommentResponse? {
        return commentRepository.findByIdAndUserId(commentId, userId)?.run {
            body = request.body
            commentRepository.save(this).toResponse()
        }
    }

    @Transactional
    fun delete(issueId: Long, commentId: Long, userId: Long) {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw IssueNotFoundException()
        commentRepository.findByIdAndUserId(commentId, userId)?.let { comment ->
            issue.comments.remove(comment)
        }
    }

}