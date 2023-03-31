package com.fastcampus.userservice.utils

import at.favre.lib.crypto.bcrypt.BCrypt

object BCryptUtils {

    fun hash(plain: String) = BCrypt.withDefaults().hashToString(12, plain.toCharArray())

    fun verify(plain: String, hashed: String) = BCrypt.verifyer().verify(plain.toCharArray(), hashed).verified

}