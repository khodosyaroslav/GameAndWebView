package com.gameandwebview.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gameandwebview.databinding.ActivitySplashBinding
import com.gameandwebview.presentation.tetris.TetrisActivity
import com.gameandwebview.presentation.webview.WebViewActivity
import com.gameandwebview.utils.NetworkUtils
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

        binding.btnRetry.setOnClickListener {
            checkInternetConnection(binding)
        }

        checkInternetConnection(binding)
    }

    private fun checkInternetConnection(binding: ActivitySplashBinding) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            binding.btnRetry.visibility = View.GONE
            binding.noInternetConnection.visibility = View.GONE
            lifecycleScope.launch {
                viewModel.gameDisabled.collect {
                    if (it) {
                        startActivity(Intent(this@SplashActivity, WebViewActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashActivity, TetrisActivity::class.java))
                    }
                }
            }
        } else {
            binding.btnRetry.visibility = View.VISIBLE
            binding.noInternetConnection.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}