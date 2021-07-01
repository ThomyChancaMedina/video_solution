package com.example.video_solution.playlist

import com.example.video_solution.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists: List<Playlist> = mock()


    @Test
    fun fetchPlaylistFromAPI() = runBlockingTest {

        service = PlaylistService(api)

        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()

    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())

    }



    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message
        )

    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Thomy backend developers"))

        service = PlaylistService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)
    }
}