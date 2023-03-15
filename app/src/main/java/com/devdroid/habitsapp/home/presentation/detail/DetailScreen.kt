package com.devdroid.habitsapp.home.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devdroid.habitsapp.R
import com.devdroid.habitsapp.core.presentation.HabitTextfield
import com.devdroid.habitsapp.home.presentation.detail.components.DetailFrequency
import com.devdroid.habitsapp.home.presentation.detail.components.DetailReminder
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    LaunchedEffect(state.isSave) {
        if (state.isSave) {
            onSave()
        }
    }
    val clockState = rememberSheetState()
    ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes {hours, minutes ->
        viewModel.onEvent(DetailEvent.ReminderChange(LocalTime.of(hours, minutes)))
    }, config = ClockConfig(
        defaultTime = state.reminder,
        is24HourFormat = true
    ))
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.border(BorderStroke(3.dp, Color(0xFFF6C395)), CircleShape),
                onClick = { viewModel.onEvent(DetailEvent.HabitSave) },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Create Habit",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "New Habit") },
                navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xFFEEDFD9)
                        ),
                        onClick = {
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.background), contentDescription =null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                HabitTextfield(
                    value = state.habitName,
                    onValueChange = {
                        viewModel.onEvent(DetailEvent.NameChange(it))
                    },
                    placeholder = "New Habit",
                    contentDescription = "Enter Habit name",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.White,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onAny = {
                            viewModel.onEvent(DetailEvent.HabitSave)
                        }
                    )
                )
                DetailFrequency(selectedDays = state.frequency, onFrequencyChange = {
                    viewModel.onEvent(DetailEvent.FrecuencyChange(it))

                })
                DetailReminder(reminder = state.reminder, onTimeClick = { clockState.show() })

            }

        }
    }

}