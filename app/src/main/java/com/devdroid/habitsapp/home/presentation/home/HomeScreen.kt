package com.devdroid.habitsapp.home.presentation.home

import android.Manifest
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devdroid.habitsapp.R
import com.devdroid.habitsapp.home.presentation.home.components.HomeAskPermission
import com.devdroid.habitsapp.home.presentation.home.components.HomeDateSelector
import com.devdroid.habitsapp.home.presentation.home.components.HomeHabit
import com.devdroid.habitsapp.home.presentation.home.components.HomeQuote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNewHabit: () -> Unit,
    onSetting: () -> Unit,
    onEditHabit:(String) ->Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewHabit,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.border(BorderStroke(3.dp, Color(0xFFF6C395)), CircleShape).semantics {
                    contentDescription = "Add a new habit"
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Habit",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xFFEEDFD9)
                        ),
                        onClick = {
                        onSetting()
                    }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.background), contentDescription = null
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                HomeAskPermission(permission = Manifest.permission.POST_NOTIFICATIONS)
            }
            LazyColumn(
                modifier = Modifier
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                item {
                    HomeQuote(
                        quote = "We first make our habits, and then our habits make us",
                        author = "Anonymous",
                        imageId = R.drawable.onboarding1,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Habits".uppercase(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        HomeDateSelector(
                            selectedDate = state.selectedDate,
                            mainDate = state.currentDate,
                            onDateClick = {
                                viewModel.onEvent(HomeEvent.ChangeDate(it))
                            })
                    }


                }
                items(
                    state.habits,
                    key = { habit ->
                        habit.id
                    }
                ) { habit ->
                    HomeHabit(
                        habit = habit,
                        selectedDate = state.selectedDate.toLocalDate(),
                        onCheckedChange = {
                            viewModel.onEvent(HomeEvent.CompleteHabit(habit))
                        },
                        onHabitClick = {
                            onEditHabit(habit.id)
                        })
                }


            }


        }
    }
}