package com.wizeline.academy.hangman.feature.game.data.repository.impl

import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.feature.game.ChallengeModel
import com.wizeline.academy.hangman.feature.game.data.datasource.MoviesRemoteDataSourceContract
import com.wizeline.academy.hangman.feature.game.data.datasource.retrofit.MovieDto
import com.wizeline.academy.hangman.testutil.TestChallengeData
import com.wizeline.academy.hangman.testutil.TestMoviesData
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ChallengeRepositoryTest {

    @Mock
    private lateinit var moviesRemoteDataSourceMock: MoviesRemoteDataSourceContract

    @Mock
    private lateinit var movieDtoToChallengeModelMapperMock: Mapper<MovieDto, ChallengeModel>

    private lateinit var subjectUnderTest: ChallengeRepository

    @Before
    fun setUp() {
        whenever(moviesRemoteDataSourceMock.getRandomPopularMovies()).thenReturn(
            Single.just(TestMoviesData.movie_results_1)
        )
        whenever(movieDtoToChallengeModelMapperMock.map(any())).thenReturn(
            TestChallengeData.challenge_1
        )

        subjectUnderTest = ChallengeRepository(
            moviesRemoteDataSource = moviesRemoteDataSourceMock,
            movieDtoToChallengeModelMapper = movieDtoToChallengeModelMapperMock
        )
    }

    @Test
    fun `assert that getRandomChallenge runs successfully`() {
        val expectedChallenge = TestChallengeData.challenge_1

        subjectUnderTest.getRandomChallenge().test().run {
            assertComplete()
            assertNoErrors()
            assertValue { challenge ->
                challenge == expectedChallenge
            }
        }

    }

}
