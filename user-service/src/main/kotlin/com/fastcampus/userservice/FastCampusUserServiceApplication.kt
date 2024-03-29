package com.fastcampus.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class FastCampusUserServiceApplication

fun main(args: Array<String>) {
    runApplication<FastCampusUserServiceApplication>(*args)
}