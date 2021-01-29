package pl.android.lab.odtwarzacz

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.android.lab.odtwarzacz.song.Song

class PlayerViewModel : ViewModel() {
    private var songIndex: Int = -1
    private var songs: List<Song> = listOf()
    private val currentSong = MutableLiveData<Song>()
    private val currentPlayer = MutableLiveData<MediaPlayer>()
    var isPlaying: Boolean = false

    fun getSongLiveData(): LiveData<Song> {
        return currentSong
    }

    fun getPlayerLiveData(): LiveData<MediaPlayer> {
        return currentPlayer
    }

    fun setSongs(context: Context, songs: List<Song>) {
        this.songs = songs
        songIndex = if (songs.isNotEmpty()) {
            0
        } else {
            -1
        }
        setSong(context, songIndex)
    }

    fun playerIsSet(): Boolean {
        return songIndex != -1
    }

    fun nextSong(context: Context) {
        if (songs.isNotEmpty()) {
            val nextSongIndex: Int = (songIndex + 1) % songs.size
            setSong(context, nextSongIndex)
        }
    }

    fun previousSong(context: Context) {
        if (songs.isNotEmpty()) {
            val prevSongIndex: Int = if (songIndex - 1 < 0) {
                songs.size - 1
            } else {
                songIndex - 1
            }

            setSong(context, prevSongIndex)
        }
    }

    private fun setSong(context: Context, index: Int) {
        if (0 <= index && index < songs.size) {
            currentSong.value = songs[index]
            currentPlayer.value = MediaPlayer.create(context, songs[index].songReference)
            songIndex = index
        }
    }
}