package com.wizeline.academy.hangman.feature.game.data.datasource.impl

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MoviesClient
import com.wizeline.academy.hangman.testutil.TestMoviesData
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class MoviesRemoteDataSourceTest {

    @Mock
    private lateinit var mockMoviesClient: MoviesClient

    private lateinit var subjectUnderTest: MoviesRemoteDataSource

    private val expectedMoviesListDto = TestMoviesData.movies_list_dto_1

    @Before
    fun setup() {
        whenever(
            mockMoviesClient.getPopularMovies(
                page = anyInt(),
                language = anyOrNull(),
                region = anyOrNull()
            )
        ).thenReturn(Single.just(expectedMoviesListDto))

        subjectUnderTest = MoviesRemoteDataSource(
            client = mockMoviesClient
        )
    }

    @Test
    fun `assert that getRandomPopularMovies calls client with page number`() {
        val result = subjectUnderTest.getRandomPopularMovies()

        assertThat(result).isNotNull()

        val captor = argumentCaptor<Int>()
        verify(mockMoviesClient).getPopularMovies(
            page = captor.capture(),
            language = anyOrNull(),
            region = anyOrNull()
        )
        captor.firstValue.let { page ->
            assertThat(page).isNotNull()
            assertThat(page).isGreaterThan(0)
        }
    }

    @Test
    fun `assert that getRandomPopularMovies generates page number between 1 and 500`() {
        val expectedRange = 1..500

        for (i in expectedRange) subjectUnderTest.getRandomPopularMovies()

        val captor = argumentCaptor<Int>()
        verify(mockMoviesClient, atLeastOnce()).getPopularMovies(
            page = captor.capture(),
            language = anyOrNull(),
            region = anyOrNull()
        )
        captor.allValues.forEach { value ->
            println("checking if $value is in range")
            assertThat(value).isIn(expectedRange)
        }
    }

    @Test
    fun `assert that getPopularMovies returns expected list`() {
        val expectedPage = expectedMoviesListDto.page!!.toInt()
        val expectedList = expectedMoviesListDto.results!!

        val result = subjectUnderTest.getPopularMovies(page = expectedPage)

        assertThat(result).isNotNull()
        verify(mockMoviesClient).getPopularMovies(eq(expectedPage), anyOrNull(), anyOrNull())
        result.blockingGet().let { list ->
            assertThat(list).isEqualTo(expectedList)
        }
    }

}
