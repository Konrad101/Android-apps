package pl.android.lab.odtwarzacz.song.readers

import pl.android.lab.odtwarzacz.R
import pl.android.lab.odtwarzacz.song.Song
import pl.android.lab.odtwarzacz.song.SongReader

class SongScanner : SongReader {
    override fun getSongReferences(): List<Song> {
        val songs = mutableListOf<Song>()
        songs.add(Song("Paint it, black", R.raw.paint_it_black, R.drawable.album_cover))
        songs.add(Song("Boogie wonderland", R.raw.boogie_wonderland))
        songs.add(Song("Y.M.C.A.", R.raw.ymca, R.drawable.ymca_cover))
        songs.add(Song("Billie Jean", R.raw.billie_jean, R.drawable.billie_jean_cover))
        return songs
    }
}