package com.wizeline.academy.hangman.feature.common.data.user.datasource.impl

import com.google.common.truth.Truth.assertThat
import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.feature.common.data.user.room.UserDao
import com.wizeline.academy.hangman.feature.common.data.user.room.UserEntity
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import com.wizeline.academy.hangman.testutil.TestUsersData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class UserLocalDataSourceTest {

    @Mock
    private lateinit var mockUserDao: UserDao

    @Mock
    private lateinit var mockUserModelToEntityMapper: Mapper<UserModel, UserEntity>

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var subjectUnderTest: UserLocalDataSource

    @Before
    fun setUp() {
        whenever(mockUserModelToEntityMapper.map(any())).thenReturn(
            TestUsersData.user_entity_1
        )

        subjectUnderTest = UserLocalDataSource(
            userDao = mockUserDao,
            userModelToEntityMapper = mockUserModelToEntityMapper,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `assert that saveUser is success`() = runTest(testDispatcher) {
        val testUser = TestUsersData.user_model_1

        val result = subjectUnderTest.saveUser(testUser)

        assertThat(result.isSuccess).isTrue()
        result.getOrThrow().let { user ->
            assertThat(user).isEqualTo(testUser)
        }
    }

    @Test
    fun `assert that saveUser generated id is name hashcode`() = runTest(testDispatcher) {
        val testUser = TestUsersData.user_model_no_id
        val expectedId = testUser.name.hashCode()

        val result = subjectUnderTest.saveUser(testUser)

        assertThat(result.isSuccess).isTrue()
        result.getOrThrow().let { user ->
            assertThat(user.id).isEqualTo(expectedId)
            assertThat(user.name).isEqualTo(testUser.name)
        }
    }

    @Test
    fun `assert that saveUser calls userDao insert`() = runTest(testDispatcher) {
        val testUser = TestUsersData.user_model_1
        val expectedEntity = TestUsersData.user_entity_1

        val result = subjectUnderTest.saveUser(testUser)

        if (!result.isSuccess) throw result.exceptionOrNull()!!

        assertThat(result.isSuccess).isTrue()
        val captor = argumentCaptor<UserEntity>()
        verify(mockUserDao).insert(captor.capture())
        assertThat(captor.firstValue).isEqualTo(expectedEntity)
    }

}