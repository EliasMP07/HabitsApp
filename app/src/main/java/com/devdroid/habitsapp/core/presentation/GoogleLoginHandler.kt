package com.devdroid.habitsapp.core.presentation

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GoogleLoginHandler {


    private var _callback = mutableStateOf<(@Composable () -> Unit)?>(null)

    suspend fun getContent(
        gsc: GoogleSignInClient,
        maxTry: Int = 10,
        millis: Long = 200,
    ): String? {
        return request(
            maxTry,
            millis,
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.launch(gsc.signInIntent)
        }
    }

    suspend fun request(
        maxTry: Int = 10,
        millis: Long = 200,
        contracts: ActivityResultContracts.StartActivityForResult,
        launcher: (ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit
    ): String? = suspendCancellableCoroutine { coroutine ->
        _callback.value = {
            val a = rememberLauncherForActivityResult(
                contracts
            ) { result: ActivityResult ->
                if (result.resultCode == android.app.Activity.RESULT_OK) {
                    val task =
                        com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(
                            result.data
                        )
                    try {
                        val account =
                            task.getResult(com.google.android.gms.common.api.ApiException::class.java)
                        coroutine.resume(account.idToken!!)
                    } catch (
                        e: com.google.android.gms.common.api.ApiException
                    ) {
                        e.printStackTrace()
                    }
                }

                _callback.value = null
                return@rememberLauncherForActivityResult
            }

            LaunchedEffect(a) {
                var tried = 0
                var tryOn = true
                while (tryOn) {
                    ++tried
                    delay(millis)
                    try {
                        launcher(a)
                        tryOn = false
                    } catch (e: Exception) {
                        if (tried > maxTry) {
                            tryOn = false
                            coroutine.resume(null)
                            _callback.value = null
                        }
                    }
                }
            }
        }

    }
    @Composable
    fun handle() {
        if(_callback.value!=null){
            _callback.value?.invoke()
        }
    }
}