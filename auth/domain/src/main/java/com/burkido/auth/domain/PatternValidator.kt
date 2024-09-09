package com.burkido.auth.domain

interface PatternValidator {
    fun matches(input: String): Boolean
}