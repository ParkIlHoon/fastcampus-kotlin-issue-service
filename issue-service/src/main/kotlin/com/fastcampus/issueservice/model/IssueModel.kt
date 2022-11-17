package com.fastcampus.issueservice.model

import com.fastcampus.issueservice.domain.Issue
import com.fastcampus.issueservice.domain.enums.IssuePriority
import com.fastcampus.issueservice.domain.enums.IssueStatus
import com.fastcampus.issueservice.domain.enums.IssueType
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * 이슈 요청 모델
 *
 * @property summary 요약
 * @property description 설명
 * @property type 이슈 타입
 * @property priority 우선 순위
 * @property status 이슈 상태
 */
data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
)

/**
 * 이슈 응답 모델
 *
 * @property id 아이디
 * @property userId 사용자 아이디
 * @property summary 요약
 * @property description 설명
 * @property type 이슈 타입
 * @property priority 우선 순위
 * @property status 이슈 상태
 * @property createdAt 생성일시
 * @property updatedAt 수정일시
 */
data class IssueResponse(
    val id: Long,
    val userId: Long,
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
) {
    companion object {
        operator fun invoke(issue: Issue) =
            with(issue) {
                IssueResponse(
                    id = id!!,
                    userId = userId,
                    summary = summary,
                    description = description,
                    type = type,
                    priority = priority,
                    status = status,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
    }
}
