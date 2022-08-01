package com.wizeline.academy.hangman.feature.login.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.feature.common.data.user.repository.UserRepositoryContract
import com.wizeline.academy.hangman.testutil.TestUsersData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginUserUseCaseTest {

    @Mock
    private lateinit var mockUserRepository: UserRepositoryContract

    private lateinit var subjectUnderTest: LoginUserUseCase

    @Before
    fun setUp() {
        mockUserRepository.stub {
            onBlocking { loginUser(anyString()) } doReturn Result.success(TestUsersData.user_model_1)
        }
        subjectUnderTest = LoginUserUseCase(
            userRepository = mockUserRepository
        )
    }

    @Test
    fun `assert that invoke is success`() = runTest {
        val expectedUser = TestUsersData.user_model_1
        val userName = expectedUser.name

        val result = subjectUnderTest.invoke(userName)

        assertThat(result.isSuccess).isTrue()
        result.getOrThrow().let {
            assertThat(it).isEqualTo(expectedUser)
        }
    }

    @Test
    fun `assert that invoke is failure when empty string`() = runTest {
        val emptyUserName = ""

        val result = subjectUnderTest.invoke(emptyUserName)

        assertThat(result.isFailure).isTrue()
        result.exceptionOrNull().let {
            assertThat(it).isNotNull()
            assertThat(it).isInstanceOf(IllegalArgumentException::class.java)
        }
    }


}