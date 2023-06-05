@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.gittrends


import com.example.gittrends.data.RetrofitHelper
import com.example.gittrends.model.UserRepositories
import com.example.gittrends.model.UserRepositoryImpl
import com.example.gittrends.network.TestApis
import com.example.gittrends.network.User
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class TestRepositoryImplTest {

    private lateinit var repository: UserRepositories
    private lateinit var testApis: TestApis
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        testApis = RetrofitHelper.testApiInstance(mockWebServer.url("/").toString())
        repository = UserRepositoryImpl(testApis)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `for no users, api must return empty with http code 200`() = runBlockingTest {
        val users = emptyList<User>()
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(users))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.body).hasSize(0)
    }

    @Test
    fun `for multiple users, api must return all users with http code 200`() = runBlockingTest {
        val users = listOf(
            User(1,"SadaPay","Isb","Pakistan"),
            User(2,"PaySada","London","UK"),
        )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(users))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.body).hasSize(2)
        assertThat(actualResponse.body).isEqualTo(users)
    }

    @Test
    fun `for server error, api must return with http code 5xx`() = runBlockingTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @Test
    fun `for server error, api must return with http code 5xx and error message`() = runBlockingTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
        assertThat(actualResponse.errorMessage).isEqualTo("server error")
    }

    @Test
    fun `for user id, api must return with http code 200 and user object`() = runBlockingTest {
        val mockUser =   User(1,"SadaPay","Isb","Pakistan")
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(mockUser))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getUserById(1)
        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_OK)
        assertThat(actualResponse.errorMessage).isNull()
        assertThat(actualResponse.body).isEqualTo(mockUser)
    }

    @Test
    fun `for user id not available, api must return with http code 404 and null user object`() = runBlockingTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getUserById(1)
        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)
        assertThat(actualResponse.body).isNull()
    }

}