package com.jcode.srtplayer

import android.app.Application
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    val player = ExoPlayer.Builder(getApplication()).build()

    @OptIn(UnstableApi::class)
    fun setMediaItem(url: String, passphrase: String) {
        player.setMediaSource(createMediaSource(url, passphrase))
        player.prepare()
        player.playWhenReady = true
    }

    @OptIn(UnstableApi::class)
    private fun createMediaItem(url: String, passphrase: String): MediaItem {
        return MediaItem.Builder()
            .setUri(url)
            /**
             * From SRT socket option: "The password must be minimum 10 and maximum
             * 79 characters long."
             */
            .setCustomCacheKey(passphrase)
            .build()
    }

    @OptIn(UnstableApi::class)
    private fun createMediaSource(mediaItem: MediaItem): MediaSource {
        return ProgressiveMediaSource.Factory(SrtDataSourceFactory(), TsOnlyExtractorFactory())
            .createMediaSource(mediaItem)
    }

    private fun createMediaSource(url: String, passphrase: String): MediaSource {
        return createMediaSource(createMediaItem(url, passphrase))
    }

    override fun onCleared() {
        player.stop()
        player.release()
        super.onCleared()
    }
}