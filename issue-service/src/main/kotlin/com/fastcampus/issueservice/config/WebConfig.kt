package com.fastcampus.issueservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

/**
 * MVC 설정
 */
@EnableWebMvc
@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
): WebMvcConfigurationSupport() {

    /**
     * 파라미터 Resolver 추가
     *
     * 1. 사용자 인증 정보 주입 Resolver
     */
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply {
            add(authUserHandlerArgumentResolver)
        }
    }
}

/**
 * 사용자 인증 정보 주입 Resolver
 *
 * 메서드 파라미터 타입이 [AuthUser] 와 일치할 경우 사용자 인증정보를 주입합니다.
 *
 * @see AuthUser 주입되는 사용자 인증정보 클래스
 */
@Component
class AuthUserHandlerArgumentResolver :HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        // TODO: 사용자 인증
        return AuthUser(
            userId = 1,
            userName = "테스트 계정",
        )
    }
}

/**
 * 사용자 인증정보
 *
 * @property userId 사용자 아이디
 * @property userName 사용자 이름
 * @property profileUrl 프로필 주소
 */
data class AuthUser(
    val userId: Long,
    val userName: String,
    val profileUrl: String? = null
)