package com.wizeline.academy.hangman.testutil

import com.wizeline.academy.hangman.feature.game.domain.model.ChallengeModel
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieDto
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieListDto

object TestMoviesData {

    val movie_dto_1 = MovieDto(
        id = 1L,
        title = "Movie 1"
    )

    val challenge_1 = ChallengeModel(
        text = movie_dto_1.title!!
    )

    val movie_dto_2 = MovieDto(
        id = 2L,
        title = "Movie 2"
    )

    val movie_dto_3 = MovieDto(
        id = 2L,
        title = "Movie 2"
    )

    val movie_dto_4 = MovieDto(
        id = 2L,
        title = "Movie 2"
    )

    val movie_results_1 = listOf(
        movie_dto_1,
        movie_dto_2
    )

    val movie_results_2 = listOf(
        movie_dto_3,
        movie_dto_4
    )

    val movies_list_dto_1 = MovieListDto(
        page = 1,
        results = movie_results_1
    )

    val movies_list_dto_2 = MovieListDto(
        page = 1,
        results = movie_results_2
    )

}
