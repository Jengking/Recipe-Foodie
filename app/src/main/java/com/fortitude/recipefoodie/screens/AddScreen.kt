package com.fortitude.recipefoodie.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fortitude.recipefoodie.components.AppBar
import com.fortitude.recipefoodie.components.InputWithHint
import com.fortitude.recipefoodie.events.AddEvents
import com.fortitude.recipefoodie.models.UiEvent
import com.fortitude.recipefoodie.vm.AddViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddScreen(controller: NavController, viewModel: AddViewModel = hiltViewModel()) {

    val snackbarHostState = remember { SnackbarHostState() }
    val nameContent = viewModel.nameContent.value
    val ingredientsContent = viewModel.ingredientsContent.value
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = viewModel.eventFlow) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.NavigateBack -> {
                    controller.navigateUp()
                }
                is UiEvent.ShouldShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(title = "Add Recipe", showBackArrow = true,
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            if(nameContent.text.isEmpty() || ingredientsContent.text.isEmpty()) {
                                snackbarHostState.showSnackbar("Please fill out all fields")
                            } else {
                                viewModel.onEvents(AddEvents.OnSave)
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Create, contentDescription = "Save", tint = Color.Black)
                    }
                }
                ){
                controller.popBackStack()
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputWithHint(
                        modifier = Modifier.fillMaxWidth()
                            .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp)),
                        text = nameContent.text,
                        hint = nameContent.hint,
                        isHintVisible = nameContent.isHintVisible,
                        onValueChange = {
                            viewModel.onEvents(AddEvents.OnNameChanged(it))
                        })

                    InputWithHint(
                        modifier = Modifier.fillMaxSize()
                            .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp)),
                        text = ingredientsContent.text,
                        hint = ingredientsContent.hint,
                        isHintVisible = ingredientsContent.isHintVisible,
                        onValueChange = {
                            viewModel.onEvents(AddEvents.OnIngredientsChanged(it))
                        })
                }
            }
        }
    }
}



