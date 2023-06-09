package com.gameandwebview.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gameandwebview.R
import com.gameandwebview.databinding.ActivitySplashBinding
import com.gameandwebview.presentation.tetris.TetrisActivity
import com.gameandwebview.presentation.webview.WebViewActivity
import com.gameandwebview.presentation.webview.WebViewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.gameDisabled.collect {
                if (it) {
                    startActivity(Intent(this@SplashActivity, WebViewActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, TetrisActivity::class.java))
                }
            }
        }
    }
}