package com.fastcampus.issueservice.model

import com.fastcampus.issueservice.domain.Comment
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * 코멘트 생성 모델
 *
 * @property body 본문
 */
data class CommentRequest(
    val body: String,
)

/**
 * 코멘트 응답 모델
 *
 * @property id 코멘트 아이디
 * @property issueId 이슈 아이디
 * @property userId 사용자 아이디
 * @property username 사용자명
 * @property body 본문
 * @property createdAt 생성일시
 * @property updatedAt 수정일시
 */
data class CommentResponse(
    var id: Long,
    val issueId: Long,
    val userId: Long,
    val username: String,
    val body: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
)

/**
 * 응답 모델 변환 함수
 */
fun Comment.toResponse() = CommentResponse(
    id = id!!,
    issueId = issue.id!!,
    userId = userId,
    username = username,
    body = body,
    createdAt = createdAt,
    updatedAt = updatedAt,
)