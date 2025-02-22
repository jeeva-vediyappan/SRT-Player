package com.jcode.srtplayer

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager

import com.jcode.srtplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private val url: String?
        get() = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(
                getString(R.string.srt_endpoint_key),
                "srt://srt://ip.of.my.computer:9998?streamid=@streamid" // @URL set Here
            )

    private val passphrase: String?
        get() = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(
                getString(R.string.srt_passphrase_key),
                getString(R.string.srt_passphrase_default)
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideSystemUI()

        binding = ActivityMainBinding.inflate(layoutInflater)
        // Force landscape mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(binding.root)

        binding.playerView.player = viewModel.player
        viewModel.setMediaItem(url!!, passphrase!!)

    }
    private fun hideSystemUI() {
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()  // Hide Action Bar

        // Full-screen mode (Optional)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }
}