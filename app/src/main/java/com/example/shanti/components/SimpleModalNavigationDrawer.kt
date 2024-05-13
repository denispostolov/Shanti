package com.example.shanti.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shanti.common.NavigationDrawerItemInfo
import com.example.shanti.navigation.screen.HomeScreen
import kotlinx.coroutines.launch

@Composable
fun SimpleModalNavigationDrawer(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    val itemInfoList = listOf(
        NavigationDrawerItemInfo(
            name = HomeScreen.Home.name,
            icon = Icons.Filled.Home,
            onClick = { navHostController.navigate(HomeScreen.Home.route) }
        ),
        NavigationDrawerItemInfo(
            name = HomeScreen.Profile.name,
            icon = Icons.Filled.AccountCircle,
            onClick = { navHostController.navigate(HomeScreen.Profile.route) }
        )
    )

    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf(itemInfoList[0]) }

    ModalNavigationDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                itemInfoList.forEach { item ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "Screen icon"
                            )
                        },
//                        badge = {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowRight,
//                                contentDescription = "Navigate"
//                            )
//                        },
                        label = {
                            Text(item.name)
                        },
                        selected = item == selectedItem.value,
                        onClick = {
                            selectedItem.value = item
                            scope.launch { drawerState.close() }
                            item.onClick()
                        }
                    )
                }
            }
        },
        content = {
            content()
        }
    )

}