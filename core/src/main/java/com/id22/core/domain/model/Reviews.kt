package com.id22.core.domain.model

data class Reviews(
    val id: String,
    val author: String,
    val content: String,
    val createdAt: String,
    val rating: Double,
    val avatar: String?,
)