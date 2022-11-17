package com.fastcampus.issueservice.domain.enums

/**
 * 이슈 우선순위
 */
enum class IssuePriority {
    LOW,
    MEDIUM,
    HIGH;

    companion object {
        operator fun invoke(priority: String) = valueOf(priority.uppercase())
    }
}
