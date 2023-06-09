package com.gameandwebview.presentation.webview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class WebViewViewModel @Inject constructor() : ViewModel() {

    private val _receivedUrl = MutableSharedFlow<String>()
    val receivedUrl: SharedFlow<String> = _receivedUrl

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val link = fetchLinkFromFirebase()
            _receivedUrl.emit(link)
        }
    }

    private suspend fun fetchLinkFromFirebase(): String = suspendCoroutine { continuation ->
        val rootRef =
            FirebaseDatabase.getInstance("https://gameandwebview-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val urlRef = rootRef.child("url")
        urlRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val link = snapshot.value as String
                continuation.resume(link)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FIREBASE DATABASE", "Failed to read value.", error.toException())
            }
        })
    }
}