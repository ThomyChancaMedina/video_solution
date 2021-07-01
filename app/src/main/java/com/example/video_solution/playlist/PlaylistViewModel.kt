package com.example.video_solution.playlist

import androidx.lifecycle.*
import kotlinx.coroutines.delay


class PlaylistViewModel(
    private val repository: PlaylistRepository
) : ViewModel(){

    val playlists = liveData<Result<List<Playlist>>> {
        emitSource(repository.getPlaylists().asLiveData())
    }

}
