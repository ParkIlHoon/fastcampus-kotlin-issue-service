package com.fastcampus.issueservice.domain

import com.fastcampus.issueservice.domain.enums.IssueStatus
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 이슈 리포지토리
 */
interface IssueRepository: JpaRepository<Issue, Long> {

    fun findAllByUserIdAndStatusOrderByCreatedAtDesc(userId: Long, status: IssueStatus): List<Issue>

}