package com.gameandwebview.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private var _gameDisabled = MutableSharedFlow<Boolean>()
    val gameDisabled: SharedFlow<Boolean> = _gameDisabled

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val remoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 15
            }
            remoteConfig.setConfigSettingsAsync(configSettings)

            remoteConfig.fetchAndActivate().await()
            _gameDisabled.emit(remoteConfig.getBoolean("game_disabled"))
        }
    }

}