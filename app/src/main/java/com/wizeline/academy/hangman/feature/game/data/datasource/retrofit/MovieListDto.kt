package com.wizeline.academy.hangman.feature.game.data.datasource.retrofit

data class MovieListDto(
    val page: Long? = null,
    val results: List<MovieDto>? = null
)
