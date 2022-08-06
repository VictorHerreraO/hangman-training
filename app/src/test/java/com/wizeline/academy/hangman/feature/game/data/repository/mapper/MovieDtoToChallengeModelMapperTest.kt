package com.wizeline.academy.hangman.feature.game.data.repository.mapper

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.testutil.TestMoviesData
import org.junit.Before
import org.junit.Test

class MovieDtoToChallengeModelMapperTest {

    private lateinit var subjectUnderTest: MovieDtoToChallengeModelMapper

    @Before
    fun setUp() {
        subjectUnderTest = MovieDtoToChallengeModelMapper()
    }

    @Test
    fun `assert that map maps successfully`() {
        val dto = TestMoviesData.movie_dto_1
        val expectedChallenge = TestMoviesData.challenge_1

        val result = subjectUnderTest.map(dto)

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(expectedChallenge)
    }
}
