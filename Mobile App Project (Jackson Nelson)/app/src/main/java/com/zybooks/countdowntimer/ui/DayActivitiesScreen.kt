package com.zybooks.countdowntimer.ui
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
}
