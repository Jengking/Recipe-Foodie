package com.fortitude.recipefoodie.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fortitude.recipefoodie.components.AppBar
import com.fortitude.recipefoodie.components.ItemContainer
import com.fortitude.recipefoodie.components.NoImgItemContainer
import com.fortitude.recipefoodie.core.Constants
import com.fortitude.recipefoodie.events.HomeEvents
import com.fortitude.recipefoodie.nav.Screens
import com.fortitude.recipefoodie.vm.HomeViewModel

@Composable
fun HomeScreen(controller: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val selectedIndex = remember {
        viewModel.selectedIndex
    }

    val mealsItems by viewModel.mealsItems.observeAsState(emptyList())
    val category = viewModel.menuList[selectedIndex.intValue]

    Scaffold(
        topBar = {
            AppBar(actions = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Button", tint = Color.Black, modifier = Modifier.padding(end = 8.dp).clickable {
                    controller.navigate(Screens.AddScreen.name)
                })
            })
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    ScrollableTabRow(
                        selectedTabIndex = selectedIndex.intValue,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ) {
                        viewModel.menuList.forEachIndexed { index, name ->
                            Tab(
                                text = {
                                    Text(
                                        text = name,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black
                                    )
                                },
                                selected = selectedIndex.intValue == index,
                                onClick = {
                                    viewModel.onEvent(HomeEvents.TabSelected(index))
                                }
                            )
                        }
                    }
                }

                if (mealsItems.isNotEmpty()) {
                    items(mealsItems) {item ->
                        if (category != Constants.OWN) {
                            ItemContainer(item = item) {
                                controller.navigate(Screens.DetailsScreen.name + "/${item.strMeal}/${item.idMeal}/${item.id}")
                            }
                        } else {
                            NoImgItemContainer(item) {
                                controller.navigate(Screens.DetailsScreen.name + "/${item.strMeal}/${item.idMeal}/${item.id}")
                            }
                        }

                    }
                } else {
                    item {
                        NotFoundBox(text = if (category != Constants.OWN) "No meals found" else "No meals found. Add own recipe.")
                    }
                }
            }
        }
    }
}

@Composable
fun NotFoundBox(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(1f),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        Text(text = text, color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}













