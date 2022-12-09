package com.fastcampus.issueservice.domain

import org.springframework.data.jpa.repository.JpaRepository

/**
 * 코멘트 리포지토리
 */
interface CommentRepository: JpaRepository<Comment, Long> {

    fun findAllByIssueOrderByCreatedAt(issue: Issue): List<Comment>

}