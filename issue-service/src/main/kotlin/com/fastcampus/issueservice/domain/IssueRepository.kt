package com.fastcampus.issueservice.domain

import org.springframework.data.jpa.repository.JpaRepository

/**
 * 이슈 리포지토리
 */
interface IssueRepository: JpaRepository<Issue, Long> {
}