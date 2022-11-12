package com.fastcampus.issueservice.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * API 문서화 설정
 */
@Configuration
class DocConfig {

    /**
     * API 문서 설정
     */
    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .info(Info()
                .title("이슈 관리 서비스 API")
                .description("실무 프로젝트로 배우는 Kotlin & Spring : 리팩토링부터 서비스 구현까지")
                .version("v1.0"))
}