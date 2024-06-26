package com.devdroid.habitsapp.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Settings")
            }, navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xFFEEDFD9)
                        ),
                        onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(it).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Support",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.tertiary
            )
            SettingsItem(text = "Contact", Icons.Default.Phone, {})
            SettingsItem(text = "Log out", Icons.Default.ExitToApp, onLogout)
        }
    }
}
