package com.devdroid.habitsapp.home.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devdroid.habitsapp.core.presentation.HabitButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeAskPermission(
    permission: String,
    modifier: Modifier = Modifier
) {
    val permissionState = rememberPermissionState(permission = permission)
    LaunchedEffect(key1 = Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.shouldShowRationale) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = { /*TODO*/ },
            buttons = {
                HabitButton(
                    text = "Accept",
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    permissionState.launchPermissionRequest()
                }
            },
            title = {
                Text(text = "Permission Required")
            },
            text = {
                Text(text = "We need this permission for the app to work correctly")
            }
        )
    }
}