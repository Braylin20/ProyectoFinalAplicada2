package ucne.edu.proyectofinalaplicada2.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import ucne.edu.proyectofinalaplicada2.presentation.authentication.AuthEvent
import ucne.edu.proyectofinalaplicada2.presentation.authentication.AuthViewModel
import ucne.edu.proyectofinalaplicada2.presentation.authentication.SettingUser
import ucne.edu.proyectofinalaplicada2.presentation.components.NavigationBar
import ucne.edu.proyectofinalaplicada2.presentation.modelo.TipoModeloListListScreen
import ucne.edu.proyectofinalaplicada2.presentation.proveedor.ProveedorRegistroScreen
import ucne.edu.proyectofinalaplicada2.presentation.renta.RentaListSceen
import ucne.edu.proyectofinalaplicada2.presentation.renta.RentaScreen
import ucne.edu.proyectofinalaplicada2.presentation.vehiculo.VehiculoRegistroScreen
import ucne.edu.proyectofinalaplicada2.presentation.view.FiltraVehiculo
import ucne.edu.proyectofinalaplicada2.presentation.view.Home
import ucne.edu.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun MainNavHost(
    navHostController: NavHostController,
    mainViewModel: NavHostViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val roleFlow = authViewModel.roleFlow

    MainBodyNavHost(
        navHostController = navHostController,
        onEvent = { event -> mainViewModel.onEvent(event) },
        uiState = uiState,
        roleFlow = roleFlow,
        onAuthEvent = { event -> authViewModel.onEvent(event) },
    )
}

@SuppressLint("NewApi")
@Composable
fun MainBodyNavHost(
    navHostController: NavHostController,
    onEvent: (MainEvent) -> Unit = {},
    uiState: MainUiState,
    roleFlow: Flow<Boolean>,
    onAuthEvent: (AuthEvent) -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val isRoleVerified by viewModel.isRoleVerified.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    val backStackEntry by navHostController.currentBackStackEntryAsState()

    if (!isRoleVerified) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            bottomBar = {
                NavigationBar(
                    navHostController = navHostController,
                    selectedItemIndex = selectedItemIndex,
                    onSelectItem = { selectedIndex -> selectedItemIndex = selectedIndex },
                    roleFlow = roleFlow
                )
            },
            topBar = {
                TopBar(
                    currentTitle = uiState.currentTitle,
                    showMenu = showMenu,
                    onToggleMenu = { showMenu = !showMenu },
                    onSignOut = {
                        onAuthEvent(AuthEvent.ClearData)
                        FirebaseAuth.getInstance().signOut()
                    },
                    onNavigateSettings = { navHostController.navigate(Screen.Settings) },
                    onBack = { navHostController.popBackStack() },
                    showBackButton = uiState.showBackButton // Pasa el valor aquí
                )
            },
            content = { innerPadding ->
                NavHostContent(
                    navHostController = navHostController,
                    innerPadding = innerPadding,
                    onEvent = onEvent,
                    backStackEntry = backStackEntry
                )
            }
        )
    }
}

@Composable
fun TopBar(
    currentTitle: String,
    showMenu: Boolean,
    onToggleMenu: () -> Unit,
    onSignOut: () -> Unit,
    onNavigateSettings: () -> Unit,
    onBack: () -> Unit = {},
    showBackButton: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF645455),
                        Color(0xFF4A28BA)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset.Infinite
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (showBackButton) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = Color.White,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
                Text(
                    text = currentTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(top = 24.dp)
                )
                Box {
                    IconButton(onClick = onToggleMenu) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Perfil",
                            tint = Color.White,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = onToggleMenu,
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Sign Out") },
                            onClick = onSignOut
                        )
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = onNavigateSettings
                        )
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavHostContent(
    navHostController: NavHostController,
    innerPadding: PaddingValues,
    onEvent: (MainEvent) -> Unit,
    backStackEntry: NavBackStackEntry?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home
        ) {
            composable<Screen.Home> {
                onEvent(MainEvent.UpdateCurrentRoute(backStackEntry ?: return@composable))
                Home(
                    onGoVehiculeList = {
                        navHostController.navigate(Screen.TipoVehiculoListScreen(it))
                    },
                    onGoSearch = {
                        navHostController.navigate(Screen.FiltraVehiculo)
                    },
                    onGoRenta = {
                        navHostController.navigate(Screen.RentaScreen(it,0))
                    }
                )
            }

            composable<Screen.VehiculoRegistroScreen> {
                onEvent(MainEvent.UpdateCurrentRoute(backStackEntry ?: return@composable))
                val id = it.toRoute<Screen.VehiculoRegistroScreen>().id
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ){
                    VehiculoRegistroScreen(
                        vehiculoId = id ?: 0,
                    )
                }
            }
            composable<Screen.TipoVehiculoListScreen> {
                onEvent(MainEvent.UpdateCurrentRoute(backStackEntry ?: return@composable))
                val id = it.toRoute<Screen.TipoVehiculoListScreen>().id
                TipoModeloListListScreen(
                    onGoRenta = { vehiculoId ->
                        navHostController.navigate(Screen.RentaScreen(vehiculoId,0))
                    },
                    marcaId = id,
                    onGoEdit = { vehiculoId ->
                        navHostController.navigate(Screen.VehiculoRegistroScreen(vehiculoId))
                    },
                )
            }
            composable<Screen.RentaScreen> {
                onEvent(MainEvent.UpdateCurrentRoute(backStackEntry ?: return@composable))
                val (vehiculoId, rentaId) = it.toRoute<Screen.RentaScreen>()

                RentaScreen(
                    vehiculoId = vehiculoId,
                    rentaId = rentaId,
                )
            }
            composable<Screen.RentaListScreen> {
                onEvent(MainEvent.UpdateCurrentRoute(backStackEntry ?: return@composable))
                RentaListSceen(
                    onGoEdit = {vehiculoId, rentaId -> navHostController.navigate(Screen.RentaScreen(vehiculoId, rentaId)) },
                )
            }
            composable<Screen.FiltraVehiculo> {
                onEvent(MainEvent.UpdateCurrentRoute(backStackEntry ?: return@composable))
                FiltraVehiculo(
                    onGoRenta = { navHostController.navigate(Screen.RentaScreen(it, 0)) },
                    onGoEdit = { navHostController.navigate(Screen.VehiculoRegistroScreen(it)) },
                )
            }
            composable<Screen.Settings> {
                SettingUser(
                    goToBack = { navHostController.popBackStack() }
                )
            }
            composable<Screen.ProveedorRegistroScreen> {
                ProveedorRegistroScreen(
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun NavHostController() {
    ProyectoFinalAplicada2Theme {
        val navController = rememberNavController()
        MainNavHost(navHostController = navController)
    }
}