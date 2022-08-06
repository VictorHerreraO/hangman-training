package com.wizeline.academy.hangman.feature.common.data.user.datasource.impl

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.hangman.testutil.TestUsersData
import org.junit.Before
import org.junit.Test


class UserModelToEntityMapperTest {

    private lateinit var subjectUnderTest: UserModelToEntityMapper

    @Before
    fun setup() {
        subjectUnderTest = UserModelToEntityMapper()
    }

    @Test
    fun map() {
        val testUser = TestUsersData.user_model_1
        val expectedResult = TestUsersData.user_entity_1

        val result = subjectUnderTest.map(testUser)

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(expectedResult)
    }

}
