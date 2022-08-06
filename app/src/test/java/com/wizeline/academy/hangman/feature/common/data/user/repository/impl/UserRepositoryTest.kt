package com.wizeline.academy.hangman.feature.common.data.user.repository.impl

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.feature.common.data.user.datasource.UserLocalDataSourceContract
import com.wizeline.academy.hangman.testutil.TestUsersData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @Mock
    private lateinit var mockUserLocalDataSource: UserLocalDataSourceContract

    private lateinit var subjectUnderTest: UserRepository

    @Before
    fun setUp() {
        mockUserLocalDataSource.stub {
            onBlocking { saveUser(any()) } doReturn Result.success(TestUsersData.user_model_1)
        }
        subjectUnderTest = UserRepository(
            userLocalDataSource = mockUserLocalDataSource
        )
    }

    @Test
    fun `assert that loginUser is success`() = runTest {
        val userName = TestUsersData.user_entity_1.name

        val result = subjectUnderTest.loginUser(userName)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `assert that loginUser calls UserLocalDataSourceContract saveUser`() = runTest {
        val userName = TestUsersData.user_entity_1.name

        val result = subjectUnderTest.loginUser(userName)

        assertThat(result.isSuccess).isTrue()
        result.getOrThrow().let { user ->
            assertThat(user.name).isEqualTo(userName)
        }
    }
}