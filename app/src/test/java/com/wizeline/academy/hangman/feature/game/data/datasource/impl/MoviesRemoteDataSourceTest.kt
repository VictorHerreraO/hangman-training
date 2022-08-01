package com.wizeline.academy.hangman.feature.game.data.datasource.impl

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MoviesClient
import com.wizeline.academy.hangman.testutil.TestMoviesData
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.AdditionalMatchers.*
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

    private val pageFloor = 1

    private val pageCeil = 500

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
        subjectUnderTest.getRandomPopularMovies().test().run {
            assertComplete()
            assertNoErrors()
            assertValue { list ->
                list == expectedMoviesListDto.results
            }
        }

        verify(mockMoviesClient).getPopularMovies(
            page = and(geq(pageFloor), leq(pageCeil)),
            language = anyOrNull(),
            region = anyOrNull()
        )
    }

    @Test
    fun `assert that getRandomPopularMovies generates page number between pageFloor and pageCeil`() {
        val expectedRange = pageFloor..pageCeil

        for (i in expectedRange) subjectUnderTest.getRandomPopularMovies()

        val captor = argumentCaptor<Int>()
        verify(mockMoviesClient, times(pageCeil)).getPopularMovies(
            page = captor.capture(),
            language = anyOrNull(),
            region = anyOrNull()
        )
        captor.allValues.forEach { value ->
            assertThat(value).isIn(expectedRange)
        }
    }

    @Test
    fun `assert that getPopularMovies returns expected list`() {
        val expectedPage = expectedMoviesListDto.page!!.toInt()
        val expectedList = expectedMoviesListDto.results!!

        subjectUnderTest.getPopularMovies(page = expectedPage).test().run {
            assertComplete()
            assertNoErrors()
            assertValue { list ->
                list == expectedList
            }
        }

        verify(mockMoviesClient).getPopularMovies(eq(expectedPage), anyOrNull(), anyOrNull())
    }

}
