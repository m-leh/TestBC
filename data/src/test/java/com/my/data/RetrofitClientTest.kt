package com.my.data

import com.my.data.remote.RetrofitClient
import com.my.domain.Album
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.net.HttpURLConnection

/**
 *
 */
class RetrofitClientTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofitBuilder: RetrofitClient

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        val path = "/mockwebserver/"
        retrofitBuilder = RetrofitClient(mockWebServer.url(path)) {}
    }

    @Test
    fun testReadJson() {
        val reader = MockResponseFileReader("technical-test.json")
        assertNotNull(reader.content)
    }

    @Test
    fun testSuccessJson() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("technical-test.json").content)
        mockWebServer.enqueue(response)

        val actualResponse = retrofitBuilder.apiInterface.getAlbums().execute()
        assertTrue(actualResponse.isSuccessful)
        assertTrue(actualResponse.body() is List<Album>)
        val albums = actualResponse.body()
        assertNotNull(albums)
        assertTrue(albums?.size == 3)
        assertTrue(albums?.get(0)?.title == "accusamus beatae ad facilis cum similique qui sunt")
        assertTrue(albums?.get(1)?.url == "https://via.placeholder.com/600/771796")
        assertTrue(albums?.get(2)?.id == 3L)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
