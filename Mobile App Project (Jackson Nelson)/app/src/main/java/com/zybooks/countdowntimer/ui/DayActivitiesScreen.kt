package com.zybooks.countdowntimer.ui
/*import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
@Composable
fun DayActivitiesScreen(day: String) {
    // TODO: Implement UI for showing and managing activities for the given day
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // center horizontally
    ) {
        // Display the day at the top
        Text(
            text = day,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Spacer to separate the day from the rest of the UI
        Spacer(modifier = Modifier.height(24.dp))

        // TODO: Later, add activities list and input UI here
    }
    //dialouge code for popup use floating action button
}*/


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.countdowntimer.data.db.ScheduledEvent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
/*@Composable
fun DayActivitiesScreen(
    day: String,
    activities: List<ScheduledEvent>,
    onGoBack: () -> Unit,
    onAddActivity: (String) -> Unit,           // <-- Add activity callback
    onRemoveActivity: (ScheduledEvent) -> Unit // <-- Remove activity callback
) {

    Scaffold(
        bottomBar = {
            BottomAppBar(tonalElevation = 4.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = onGoBack) {
                        Text("Back")
                    }
                    Button(onClick = { onAddActivity(day) }) {
                        Text("Add Activity")
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔷 TOP: Day name
            Text(
                text = day,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔷 MIDDLE: Activities list
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(activities) { event ->
                    ActivityItem(
                        event = event,
                        onRemove = { onRemoveActivity(event) }  // <-- Remove clicked
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityItem(
    event: ScheduledEvent,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = event.taskName, fontWeight = FontWeight.Bold)
            Text(text = "Start: ${event.startTimeMillis}")
            Text(text = "End: ${event.endTimeMillis}")

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onRemove,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Remove")
            }
        }
    }
}*/
@Composable
fun DayActivitiesScreen(
    day: String,
    navController: NavController, //added NavController
    onGoBack: () -> Unit,
    onAddActivity: () -> Unit,
    viewModel: DayActivitiesViewModel = viewModel()
) {
    // Track selected activity for removal
    var selectedActivity by remember { mutableStateOf<ScheduledEvent?>(null) }

    //temp state for user input
    // Track whether the Add Activity dialog is open
    var showAddDialog by remember { mutableStateOf(false) }

    // Temporary state for user input
    var activityName by remember { mutableStateOf("") }
    var startHour by remember { mutableStateOf(0) }
    var startMinute by remember { mutableStateOf(0) }
    var endHour by remember { mutableStateOf(0) }
    var endMinute by remember { mutableStateOf(0) }

    // Load activities for the selected day
    LaunchedEffect(day) {
        viewModel.loadDay(day)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top: Display current day
        Text(
            text = day,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        // Middle: Scrollable list of activities
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(viewModel.activityList) { activity ->
                ActivityItem(
                    activity = activity,
                    isSelected = activity == selectedActivity,
                    onClick = { selectedActivity = activity }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Bottom: Bar with three buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("home") }) {

                Text("Go Back")
            }

            /*Button(onClick = onAddActivity) {
                Text("Add Activity")
            }*/

            Button(onClick = { showAddDialog = true }) {
                Text("Add Activity")
            }

            Button(
                onClick = {
                    selectedActivity?.let {
                        viewModel.removeActivity(it)
                        selectedActivity = null // reset selection
                    }
                },
                enabled = selectedActivity != null // only enable if something is selected
            ) {
                Text("Remove Activity")
            }
        }
    }
    //Activity Dialog here
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Activity") },
            text = {
                Column {
                    OutlinedTextField(
                        value = activityName,
                        onValueChange = { activityName = it },
                        label = { Text("Activity Name") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        NumberPickerWrapper(
                            initVal = startHour,
                            maxVal = 23,
                            onNumPick = { startHour = it }
                        )
                        NumberPickerWrapper(
                            initVal = startMinute,
                            maxVal = 59,
                            onNumPick = { startMinute = it }
                        )
                    }
                    Text("Start Time (HH:MM)")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        NumberPickerWrapper(
                            initVal = endHour,
                            maxVal = 23,
                            onNumPick = { endHour = it }
                        )
                        NumberPickerWrapper(
                            initVal = endMinute,
                            maxVal = 59,
                            onNumPick = { endMinute = it }
                        )
                    }
                    Text("End Time (HH:MM)")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        //val startTimeMillis = (startHour * 3600 + startMinute * 60) * 1000L
                        //val endTimeMillis = (endHour * 3600 + endMinute * 60) * 1000L

                        viewModel.addActivity(
                            dayOfWeek = day,
                            taskName = activityName,
                            startHour = startHour,
                            startMin = startMinute,
                            startSec = 0,
                            endHour = endHour,
                            endMin = endMinute,
                            endSec = 0

                        )

                        // Reset and close dialog
                        activityName = ""
                        startHour = 0
                        startMinute = 0
                        endHour = 0
                        endMinute = 0
                        showAddDialog = false
                    }
                ) {
                    Text("Insert Activity")
                }
            },
            dismissButton = {
                Button(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

}

@Composable
fun ActivityItem(
    activity: ScheduledEvent,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = if (isSelected) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        else CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(activity.taskName, fontWeight = FontWeight.Medium)
            Text(
                text = "${formatTime(activity.startTimeMillis)} - ${formatTime(activity.endTimeMillis)}"
            )
        }
    }
}

// Helper: convert milliseconds to HH:MM:SS string
fun formatTime(timeMillis: Long): String {
    val hours = (timeMillis / 3600000)
    val minutes = (timeMillis / 60000) % 60
    val seconds = (timeMillis / 1000) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

