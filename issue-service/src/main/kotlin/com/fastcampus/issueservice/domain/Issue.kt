package com.fastcampus.issueservice.domain

import com.fastcampus.issueservice.domain.enums.IssuePriority
import com.fastcampus.issueservice.domain.enums.IssueType
import com.fastcampus.issueservice.domain.enums.IssueStatus
import javax.persistence.*

/**
 * 이슈 엔티티
 *
 * @property id 아이디
 * @property userId 사용자 아이디
 * @property summary 요약
 * @property description 설명
 * @property type 이슈 타입
 * @property priority 우선 순위
 * @property status 이슈 상태
 */
@Entity
@Table
class Issue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var userId: Long,

    @Column
    var summary: String,

    @Column
    var description: String,

    @Column
    @Enumerated(value = EnumType.STRING)
    var type: IssueType,

    @Column
    @Enumerated(value = EnumType.STRING)
    var priority: IssuePriority,

    @Column
    @Enumerated(value = EnumType.STRING)
    var status: IssueStatus,

    ): BaseEntity() {
}