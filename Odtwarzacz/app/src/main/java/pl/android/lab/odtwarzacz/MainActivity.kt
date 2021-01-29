package pl.android.lab.odtwarzacz

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import pl.android.lab.odtwarzacz.song.readers.SongScanner


class MainActivity : AppCompatActivity() {
    private val seekBarRefreshMillis = 300L
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var musicPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)

        if (!playerViewModel.playerIsSet()) {
            playerViewModel.setSongs(this, SongScanner().getSongReferences())
        }

        musicPlayer = playerViewModel.getPlayerLiveData().value!!
        observeViewModelData()
        adjustToNewSong()
        setSeekBar()
        setPlayButtonListener()
        setSkipSongButtonsListeners()
        setSkipTenSecondsButtonsListeners()
        refreshPlayButton()
    }

    private fun observeViewModelData() {
        playerViewModel.getSongLiveData().observe(this, {
            val icon: Bitmap = BitmapFactory.decodeResource(
                this.resources,
                it.songCoverReference
            )
            background_cover.setImageBitmap(background_cover.blurRenderScript(icon, 10))

            background_cover.refreshDrawableState()

            song_cover.setImageResource(it.songCoverReference)
            song_title_TV.text = it.title
        })
        playerViewModel.getPlayerLiveData().observe(this, {
            musicPlayer = it
        })
    }

    private fun setSeekBar() {
        seek_bar.progressDrawable.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN);
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        runnable = Runnable {
            seek_bar.progress = musicPlayer.currentPosition
            handler.postDelayed(runnable, seekBarRefreshMillis)
        }
        handler.postDelayed(runnable, seekBarRefreshMillis)
    }

    private fun setSkipSongButtonsListeners() {
        prev_song_button.setOnClickListener {
            musicPlayer.stop()
            playerViewModel.previousSong(this)
            adjustToNewSong()
        }
        next_song_button.setOnClickListener {
            musicPlayer.stop()
            playerViewModel.nextSong(this)
            adjustToNewSong()
        }
    }

    private fun setSkipTenSecondsButtonsListeners() {
        fast_rewind_button.setOnClickListener {
            skipSongForSeconds(-10)
        }
        fast_forward_button.setOnClickListener {
            skipSongForSeconds(10)
        }
    }

    private fun skipSongForSeconds(seconds: Int) {
        val millis = seconds * 1000
        val newSongPosition = musicPlayer.currentPosition + millis
        if (newSongPosition > 0 && newSongPosition < musicPlayer.duration) {
            musicPlayer.seekTo(newSongPosition)
        } else if (newSongPosition <= 0) {
            musicPlayer.seekTo(0)
        } else {
            musicPlayer.seekTo(musicPlayer.duration - 1)
        }
    }

    private fun adjustToNewSong() {
        if (playerViewModel.isPlaying) {
            musicPlayer.start()
        }
        seek_bar.max = musicPlayer.duration

        musicPlayer.setOnCompletionListener {
            playerViewModel.nextSong(this)
            if (playerViewModel.isPlaying) {
                musicPlayer.start()
            }
            seek_bar.max = musicPlayer.duration
            seek_bar.progress = 0
        }
    }

    private fun setPlayButtonListener() {
        play_button.setOnClickListener {
            if (!musicPlayer.isPlaying) {
                musicPlayer.start()
                playerViewModel.isPlaying = true
                refreshPlayButton()
            } else {
                musicPlayer.pause()
                playerViewModel.isPlaying = false
                refreshPlayButton()
            }
        }
    }

    private fun refreshPlayButton() {
        if (playerViewModel.isPlaying) {
            play_button.setImageResource(R.drawable.ic_baseline_pause_60)
        } else {
            play_button.setImageResource(R.drawable.ic_baseline_play_arrow_60)
        }
    }
}