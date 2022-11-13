package com.fastcampus.issueservice.domain.enums

/**
 * 이슈 상태
 */
enum class IssueStatus {
    // 할 일
    TODO,
    // 진행 중
    IN_PROGRESS,
    // 완료
    RESOLVED;

    companion object {
        operator fun invoke(status: String) = valueOf(status.uppercase())
    }
}
