package ucne.edu.proyectofinalaplicada2.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import ucne.edu.proyectofinalaplicada2.components.Home
import ucne.edu.proyectofinalaplicada2.components.ModalDrawerSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentCarNavHost(
    navHostController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("BravquezRentcar") },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    scrolledContainerColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )

            )
        },
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unSelectedIcon = Icons.Outlined.Home,
                    ),
                    BottomNavigationItem(
                        title = "Renta",
                        selectedIcon = Icons.Filled.Call,
                        unSelectedIcon = Icons.Outlined.Call,
                    ),
                    BottomNavigationItem(
                        title = "Settings",
                        selectedIcon = Icons.Filled.Settings,
                        unSelectedIcon = Icons.Outlined.Settings,
                    )
                )

                items.forEachIndexed{index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex ==index,
                        onClick = {
                            selectedItemIndex =index
                            navHostController.navigate(Screen.Home)
                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon ={
                            Icon(
                                imageVector = if(index == selectedItemIndex){
                                    item.selectedIcon
                                }else item.unSelectedIcon,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    ) { innerpadding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home,
            modifier = Modifier.padding(innerpadding)
        ) {
            composable<Screen.Home> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Home()
                }

            }
        }
    }


}