package com.wizeline.academy.hangman.feature.login.framework.presentation

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.core.arch.UseCase
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import com.wizeline.academy.hangman.testutil.MainDispatcherRule
import com.wizeline.academy.hangman.testutil.TestUsersData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.stub
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockLoginUserUseCase: UseCase<String, Result<UserModel>>

    private lateinit var subjectUnderTest: LoginViewModel

    private val expectedUser = TestUsersData.user_model_1

    @Before
    fun setup() {
        mockLoginUserUseCase.stub {
            onBlocking { invoke(any()) } doAnswer { Result.success(expectedUser) }
        }

        subjectUnderTest = LoginViewModel(
            loginUserUseCase = mockLoginUserUseCase
        )
    }

    @Test
    fun `when user names changes then userName is updated`() = runTest {
        val expectedName = expectedUser.name

        subjectUnderTest.onUserNameChanged(userName = expectedName)

        val state = subjectUnderTest.loginState.first()

        assertThat(state).isNotNull()
        assertThat(state.userName).isEqualTo(expectedName)
    }

    @Test
    fun `when user name is empty then loginEnabled is false`() = runTest {
        subjectUnderTest.onUserNameChanged(userName = "")

        val state = subjectUnderTest.loginState.first()
        assertThat(state).isNotNull()
        assertThat(state.loginEnabled).isFalse()
    }

    @Test
    fun `when do login then loginLoading is true`() = runTest {
        val userName = expectedUser.name
        subjectUnderTest.onUserNameChanged(userName = userName)

        subjectUnderTest.login()

        val state = subjectUnderTest.loginState.first()
        assertThat(state.loginLoading).isTrue()
    }

    @Test
    fun `when do login success then navigate to home`() = runTest {
        val userName = expectedUser.name
        subjectUnderTest.onUserNameChanged(userName = userName)

        subjectUnderTest.login()

        val navigationEvent = subjectUnderTest.navigateTo.first()

        assertThat(navigationEvent).isNotNull()
        navigationEvent.getContentIfNotHandled().let { destination ->
            assertThat(destination).isNotNull()
            assertThat(destination).isInstanceOf(LoginNavigation.NavigateHome::class.java)
            assertThat((destination as LoginNavigation.NavigateHome).userId).isEqualTo(expectedUser.id)
        }
    }

    @Test
    fun `when do login failed then loginLoading is false`() = runTest() {
        whenever(mockLoginUserUseCase.invoke(any())).thenAnswer {
            Result.failure<UserModel>(
                RuntimeException()
            )
        }
        val userName = expectedUser.name
        subjectUnderTest.onUserNameChanged(userName = userName)

        subjectUnderTest.login()

        /*
        TODO: For this test i would like to be able to get the second emitted element of the flow
            and run my assertions against it
         */

        val state = subjectUnderTest.loginState.first()
        assertThat(state.loginLoading).isFalse()
    }

}
