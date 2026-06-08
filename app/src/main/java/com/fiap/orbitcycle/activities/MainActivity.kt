package com.fiap.orbitcycle.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fiap.orbitcycle.R
import com.fiap.orbitcycle.api.SpaceObject
import com.fiap.orbitcycle.viewmodel.CaptureAnalysisViewModel
import com.fiap.orbitcycle.viewmodel.SpaceViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OrbitCycleApp()
        }
    }
}

@Composable
fun SpaceObjectsScreen(
    navController: NavController
) {

    val any = ""

    val spaceViewModel: SpaceViewModel = viewModel()
    val objects by spaceViewModel.objects.collectAsState()

    val BackgroundColor = Color(0xFF050B16)

    LaunchedEffect(any) {
        spaceViewModel.loadObjects()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {

        SpaceCaptureHeader()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 170.dp, 16.dp, 16.dp)
                .background(Color(0xFF050B16))
            ) {

            items(objects.size) { index ->

                val objectItem = objects[index]

                SpaceObjectCard(
                    spaceObject = objectItem,
                    onClick = {
                        navController.navigate("captureAnalysis/${objectItem.noradId}")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceCaptureHeader() {

    val BackgroundColor = Color(0xFF050B16)
    val TitleColor = Color(0xFF00D4FF)

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(BackgroundColor)
            .clip(
                RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            ),
        title = {
            Text(
                "Lista de Detritos Espaciais",
                color = TitleColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor,
            titleContentColor = TitleColor,
            navigationIconContentColor = TitleColor
        )
    )
}

@Composable
fun SpaceObjectCard(
    spaceObject: SpaceObject,
    onClick: () -> Unit
) {

    val CardColor = Color(0xFF0D1B2A)
    val TitleColor = Color(0xFF00D4FF)
    val TextColor = Color.White

            Card(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardColor
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = spaceObject.objectName,
                        style = MaterialTheme.typography.titleLarge,
                        color = TitleColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "NORAD: ${spaceObject.noradId}",
                        color = TextColor
                    )

                    Text(
                        text = "Nome do Objeto: ${spaceObject.objectName}",
                        color = TextColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("X: ${spaceObject.positionX}", color = TextColor)
                    Text("Y: ${spaceObject.positionY}", color = TextColor)
                    Text("Z: ${spaceObject.positionZ}", color = TextColor)
                }
            }

}

@Composable
fun OrbitCycleApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "debris"
    ) {

        composable("debris") {

            SpaceObjectsScreen(
                navController = navController
            )
        }

        composable(
            route = "captureAnalysis/{noradId}"
        ) { backStack ->

            val noradId =
                backStack.arguments
                    ?.getString("noradId")
                    ?: ""

            CaptureAnalysisScreen(noradId, navController)
        }
    }
}

@Composable
fun CaptureAnalysisScreen(
    noradId: String,
    navController: NavController
) {

    val BackgroundColor = Color(0xFF050B16)
    val TitleColor = Color(0xFF00D4FF)

    val viewModel: CaptureAnalysisViewModel = viewModel()
    val estimate by viewModel.captureEstimate.collectAsState()

    LaunchedEffect(noradId) {
        viewModel.loadCaptureEstimate(noradId)
    }

    if (estimate == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    val data = estimate!!

    Scaffold(
        topBar = {
            CaptureAnalysisHeader(navController)
        },
        containerColor = Color(0xFF050B16)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(16.dp, 170.dp, 16.dp, 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = data.objectName,
                style = MaterialTheme.typography.headlineMedium,
                color = TitleColor
            )

            Text(
                text = "NORAD ${data.noradId}",
                style = MaterialTheme.typography.bodyLarge,
                color = TitleColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Informações do Objeto",
                style = MaterialTheme.typography.titleLarge,
                color = TitleColor
            )

            InfoCard("Tipo do Objeto", data.objectType)
            InfoCard("Tamanho RCS", data.rcsSize)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Dados Orbitais",
                style = MaterialTheme.typography.titleLarge,
                color = TitleColor
            )

            InfoCard(
                "Altitude Atual",
                "${data.currentAltitudeKm} km"
            )

            InfoCard(
                "Inclinação",
                "${data.inclinationDeg}°"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Missão Captura",
                style = MaterialTheme.typography.titleLarge,
                color = TitleColor
            )

            InfoCard(
                "ΔV Requerido",
                "${data.deltaVms} m/s"
            )

            InfoCard(
                "Quantidade de Combustível Necessária",
                "${data.fuelKg} kg"
            )

            InfoCard(
                "Tempo de Transferência",
                "${data.transferTimeHours} h"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Análise de Custo",
                style = MaterialTheme.typography.titleLarge,
                color = TitleColor
            )

            InfoCard(
                "Custo do Combustível",
                "$${String.format("%.2f", data.fuelCostUSD)}"
            )

            InfoCard(
                "Custos Operacionais",
                "$${String.format("%.2f", data.operationsCostUSD)}"
            )

            InfoCard(
                "Custos Totais",
                "$${String.format("%.2f", data.totalCostUSD)}"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Janela de Tempo da Missão",
                style = MaterialTheme.typography.titleLarge,
                color = TitleColor
            )

            InfoCard(
                "Tempo de Chegada Estimado",
                data.estimatedArrival
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String
) {

    val CardColor = Color(0xFF0D1B2A)
    val TitleColor = Color(0xFF00D4FF)
    val TextColor = Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TitleColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = TextColor
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureAnalysisHeader(
    navController: NavController
) {
    TopAppBar(
        title = {
            Text("Análise de Captura")
        },
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF050B16),
            titleContentColor = Color(0xFF00D4FF),
            navigationIconContentColor = Color(0xFF00D4FF)
        )
    )
}