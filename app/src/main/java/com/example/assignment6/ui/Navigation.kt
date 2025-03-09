/* Assignment 6
   Navigation.kt
   Nils Streedain | streedan@oregonstate.com
   CS 492 | Oregon State University
*/
package com.example.assignment6.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.assignment6.model.Clue
import com.example.assignment6.viewmodel.Hunt
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@Composable
fun ScreenBody(
    alert: String? = null,
    dismissAlert: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { content() }
    alert?.let {
        AlertDialog(
            onDismissRequest = dismissAlert,
            title = { Text("Note") },
            text = { Text(it) },
            confirmButton = { Button(onClick = dismissAlert) { Text("OK") } }
        )
    }
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val vm: Hunt = viewModel()
    val clue by vm.currentClue.collectAsState()
    val time by vm.elapsedTime.collectAsState()
    NavHost(navController, startDestination = "start", modifier) {
        composable("start") { StartScreen(navController, vm) }
        composable("clue") { ClueScreen(navController, vm, clue, time) }
        composable("clueSolved") { ClueSolvedScreen(navController, vm, clue, time) }
        composable("completed") { CompletedScreen(navController, clue, time) }
    }
}

@Composable
fun StartScreen(nc: NavController, vm: Hunt) {
    val ctx = LocalContext.current
    val perm = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) perm.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    ScreenBody {
        Text("Treasure Hunt", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("Rules:\n" +
                "1. Tap Start to begin your treasure hunt.\n" +
                "2. Read each clue and travel to the location it describes.\n" +
                "3. Press Found It! when you think you’re in the right spot.\n" +
                "4. Solve all clues to finish the hunt and see your total time."
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            vm.resetGame()
            nc.navigate("clue"){ popUpTo("start"){ inclusive=true } }
        }) { Text("Start") }
    }
}

@Composable
fun ClueScreen(nc: NavController, vm: Hunt, clue: Clue, time: Long) {
    val ctx = LocalContext.current
    var alert by remember { mutableStateOf<String?>(null) }
    ScreenBody(alert, { alert = null }) {
        Text("Clue: ${clue.clueText}")
        Text("Time: ${time}s")
        Button({ alert = clue.hint }) { Text("Hint") }
        Button({
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) { alert="Permission not granted";return@Button }
            LocationServices.getFusedLocationProviderClient(ctx)
                .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,null)
                .addOnSuccessListener { loc ->
                    when {
                        loc==null -> alert="No location"
                        !vm.checkLocation(loc.latitude, loc.longitude) ->
                            alert="Location not correct. Try again."
                        clue.id==2 -> {
                            vm.stopTimer()
                            nc.navigate("completed"){ popUpTo("clue"){ inclusive=true } }
                        }
                        else -> {
                            nc.navigate("clueSolved"){ popUpTo("clue"){ inclusive=true } }
                        }
                    }
                }
                .addOnFailureListener { alert="Error retrieving location" }
        }) { Text("Found It!") }
        Button({ nc.navigate("start"){ popUpTo("start"){ inclusive=true } } }) { Text("Quit") }
    }
}

@Composable
fun ClueSolvedScreen(nc: NavController, vm: Hunt, clue: Clue, time: Long) {
    LaunchedEffect(Unit) {
        vm.stopTimer()
    }
    ScreenBody {
        Text("Clue Solved!")
        Text("Time Elapsed: ${time}s")
        Spacer(Modifier.height(16.dp))
        Text(clue.description)
        Spacer(Modifier.height(16.dp))
        Button({
            vm.moveToNextClue()
            vm.startTimer()
            nc.navigate("clue"){ popUpTo("clueSolved"){ inclusive=true } }
        }) { Text("Continue") }
    }
}

@Composable
fun CompletedScreen(nc: NavController, clue: Clue, time: Long) {
    ScreenBody {
        Text("Treasure Hunt Completed!")
        Text("Total Time: ${time}s")
        Spacer(Modifier.height(16.dp))
        Text(clue.description)
        Spacer(Modifier.height(16.dp))
        Button({ nc.navigate("start"){ popUpTo("completed"){ inclusive=true } } }) { Text("Home") }
    }
}